package com.htmlism.firepower.demo

import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  case class Move(src: String, dest: String)

  val program: List[Move] =
    List(
      Move("#$01", "$0200"),
      Move("#$03", "$0201"),
      Move("#$05", "$0202")
    )

  def assemble(opt: AssemblerOptions): String =
    program
      .map { mv =>
        AsmBlock.Intent(
          s"${mv.dest} = ${mv.src}".some,
          List(
            AsmBlock.Intent.Instruction(instruction("LDA", opt.instructionCase) + " " + mv.src, None),
            AsmBlock.Intent.Instruction(instruction("STA", opt.instructionCase) + " " + mv.dest, None)
          )
        )
      }
      .pipe(AnonymousCodeBlock(_))
      .pipe(List(_))
      .map(AsmBlock.toLines)
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
      .pipe(str.Line.mkString)

  def instruction(s: String, instructionCase: AssemblerOptions.InstructionCase): String =
    instructionCase match
      case AssemblerOptions.InstructionCase.Uppercase =>
        s.toUpperCase

      case AssemblerOptions.InstructionCase.Lowercase =>
        s.toLowerCase
