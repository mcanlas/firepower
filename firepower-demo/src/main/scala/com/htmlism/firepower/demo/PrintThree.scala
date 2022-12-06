package com.htmlism.firepower.demo

import scala.collection.immutable._
import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  case class Move[A: Definable, B: Definable](src: A, dest: B):
    def defines: List[ListMap[String, String]] =
      List(implicitly[Definable[A]].table, implicitly[Definable[B]].table)

  def build(screen: Easy6502.Screen): List[Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    List(
      Move(Easy6502.Color.White, screen(0)),
      Move(Easy6502.Color.Green, screen(1)),
      Move(Easy6502.Color.Orange, screen(2))
    )

  val program: List[Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    build(Easy6502.Screen(200))

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
          .flatMap(_.defines)
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
              "#" + mv.src.toDefine

        AsmBlock.Intent(
          s"${mv.dest.toComment} = ${mv.src.toComment}".some,
          List(
            AsmBlock.Intent.Instruction(instruction("LDA", opts.instructionCase) + " " + argument, None),
            AsmBlock.Intent.Instruction(instruction("STA", opts.instructionCase) + " $" + mv.dest.toValue, None)
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
