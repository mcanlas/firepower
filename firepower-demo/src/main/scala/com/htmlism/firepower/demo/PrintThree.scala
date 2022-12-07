package com.htmlism.firepower.demo

import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  def build(screen: Easy6502.Screen): List[MetaIntent.Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    List(
      MetaIntent.Move(Easy6502.Color.White, screen(0)),
      MetaIntent.Move(Easy6502.Color.Green, screen(1)),
      MetaIntent.Move(Easy6502.Color.Orange, screen(2))
    )

  val program: List[MetaIntent.Move[Easy6502.Color, Easy6502.Screen.Pixel]] =
    build(Easy6502.Screen(0x200))

  def assemble(opts: AssemblerOptions): List[String] =
    (defines(opts.definitionsMode) ++ codes(opts.definitionsMode))
      .map(AsmBlock.toLines(opts.instructionCase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))

  private def defines(opts: AssemblerOptions.DefinitionsMode) =
    opts match
      case AssemblerOptions.DefinitionsMode.UseLiterals =>
        Nil

      case AssemblerOptions.DefinitionsMode.UseDefinitions | AssemblerOptions.DefinitionsMode.UseDefinitionsWithMath =>
        program
          .flatMap(_.defines)
          .distinct
          .map { dt =>
            AsmBlock.DefinesBlock(dt.description.some, dt.xs)
          }

  private def codes(opts: AssemblerOptions.DefinitionsMode) =
    program
      .map(MetaIntent.Move.toIntent(_, opts))
      .pipe(AnonymousCodeBlock(_))
      .pipe(List(_))
