package com.htmlism.firepower.demo

import scala.collection.immutable._
import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  case class Move[A: Definable](src: A, dest: String):
    def defines: Option[ListMap[String, String]] =
      implicitly[Definable[A]]
        .table
        .some

  val program: List[Move[Easy6502.Color]] =
    List(
      Move(Easy6502.Color.White, "$0200"),
      Move(Easy6502.Color.Green, "$0201"),
      Move(Easy6502.Color.Orange, "$0202")
    )

  def assemble(opts: AssemblerOptions): String =
    (defines(opts) ++ codes(opts))
      .map(AsmBlock.toLines)
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
      .pipe(str.Line.mkString)

  private def defines(opts: AssemblerOptions) =
    opts.definitionsMode match
      case AssemblerOptions.DefinitionsMode.UseLiterals =>
        Nil

      case AssemblerOptions.DefinitionsMode.UseDefinitions =>
        program
          .flatMap(_.defines.toList)
          .distinct
          .map { xs =>
            xs
              .toList
              .map { case (k, v) =>
                s"define $k $v"
              }
              .pipe(AsmBlock.CommentBlock(_))
          }

      case AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
        program
          .flatMap(_.defines.toList)
          .distinct
          .map { xs =>
            xs
              .toList
              .map { case (k, v) =>
                s"define $k $v"
              }
              .pipe(AsmBlock.CommentBlock(_))
          }

  private def codes(opts: AssemblerOptions) =
    program
      .map { mv =>
        val argument =
          opts.definitionsMode match
            case AssemblerOptions.DefinitionsMode.UseLiterals =>
              f"#$$${mv.src.toValue}%02X"

            case AssemblerOptions.DefinitionsMode.UseDefinitions |
                AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
              mv.src.toDefine

        AsmBlock.Intent(
          s"${mv.dest} = ${mv.src.toComment}".some,
          List(
            AsmBlock.Intent.Instruction(instruction("LDA", opts.instructionCase) + " " + argument, None),
            AsmBlock.Intent.Instruction(instruction("STA", opts.instructionCase) + " " + mv.dest, None)
          )
        )
      }
      .pipe(AnonymousCodeBlock(_))
      .pipe(List(_))

  def instruction(s: String, instructionCase: AssemblerOptions.InstructionCase): String =
    instructionCase match
      case AssemblerOptions.InstructionCase.Uppercase =>
        s.toUpperCase

      case AssemblerOptions.InstructionCase.Lowercase =>
        s.toLowerCase
