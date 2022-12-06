package com.htmlism.firepower.demo

import scala.collection.immutable._
import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  case class Move[A: Definable, B: Definable](src: A, dest: B):
    def defines: List[ListMap[String, Int]] =
      List(
        Definable[A]
          .table(src),
        Definable[B]
          .table(dest)
      )

  def build(screen: Easy6502.Screen): List[Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    List(
      Move(Easy6502.Color.White, screen(0)),
      Move(Easy6502.Color.Green, screen(1)),
      Move(Easy6502.Color.Orange, screen(2))
    )

  val program: List[Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    build(Easy6502.Screen(0x200))

  def assemble(opts: AssemblerOptions): String =
    (defines(opts.definitionsMode) ++ codes(opts.definitionsMode))
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
      .pipe(str.Line.mkString)

  private def defines(opts: AssemblerOptions.DefinitionsMode) =
    opts match
      case AssemblerOptions.DefinitionsMode.UseLiterals =>
        Nil

      case AssemblerOptions.DefinitionsMode.UseDefinitions | AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
        program
          .flatMap(_.defines)
          .distinct
          .map { xs =>
            xs
              .toList
              .pipe(AsmBlock.DefinesBlock(_))
          }

  private def codes(opts: AssemblerOptions.DefinitionsMode) =
    program
      .map { mv =>
        val argument =
          opts match
            case AssemblerOptions.DefinitionsMode.UseLiterals =>
              f"#$$${mv.src.toValue}%02X"

            case AssemblerOptions.DefinitionsMode.UseDefinitions |
                AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
              "#" + mv.src.toDefine

        val argumentTwo =
          opts match
            case AssemblerOptions.DefinitionsMode.UseLiterals =>
              AsmBlock.toHex(mv.dest.toValue)

            case AssemblerOptions.DefinitionsMode.UseDefinitions =>
              mv.dest.toDefine

            case AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
              mv.dest.toDefineWithMath

        AsmBlock.Intent(
          s"${mv.dest.toComment} = ${mv.src.toComment}".some,
          List(
            AsmBlock
              .Intent
              .Instruction
              .One("LDA", argument, s"a = ${mv.src.toComment}".some),
            AsmBlock
              .Intent
              .Instruction
              .One(
                "STA",
                argumentTwo,
                s"${mv.dest.toComment} = a".some
              )
          )
        )
      }
      .pipe(AnonymousCodeBlock(_))
      .pipe(List(_))
