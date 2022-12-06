package com.htmlism.firepower.demo

import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  case class Move[A: Definable](src: A, dest: String)

  val program: List[Move[Easy6502.Color]] =
    List(
      Move(Easy6502.Color.White, "$0200"),
      Move(Easy6502.Color.Green, "$0201"),
      Move(Easy6502.Color.Orange, "$0202")
    )

  def assemble(opt: AssemblerOptions): String =
    program
      .map { mv =>
        val hex =
          f"#$$${mv.src.toValue}%02X"

        AsmBlock.Intent(
          s"${mv.dest} = ${mv.src.toComment}".some,
          List(
            AsmBlock.Intent.Instruction(instruction("LDA", opt.instructionCase) + " " + hex, None),
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
