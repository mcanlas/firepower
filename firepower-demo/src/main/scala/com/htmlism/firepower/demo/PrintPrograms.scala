package com.htmlism.firepower.demo

import java.io.IOException

import scala.util.chaining._

import cats.syntax.all._
import zio.*

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core.AssemblerOptions._
import com.htmlism.firepower.core._
import com.htmlism.firepower.demo.str._
import com.htmlism.rufio.withzio.*

object PrintPrograms extends ZIOAppDefault:
  private def writeLine(file: String)(s: String) =
    File(s"data/$file.txt")
      .writeLine(s)

  private val programs =
    List[(String, String)](
      // FEATURE: writing a string to a file is easy (thanks, rufio)
      "one-line.txt" -> "one line",

      // FEATURE: writing lines to a file is easy
      "two-lines.txt" -> List("foo", "bar")
        .pipe(Line.mkString),

      // FEATURE: writing paragraphs separated by newlines is easy
      "two-paragraphs.txt"         -> List(
        List("foo", "bar"),
        List("alpha", "bravo")
      )
        .pipe(xxs => AsmBlock.interFlatMap(xxs)(List("", ""), identity))
        .pipe(Line.mkString),
      "feature-demo.asm"           -> FeatureDemo.program,
      "print-three-upper-math.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitionsWithMath)
      ),
      "print-three-upper.asm"      -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitions)
      ),
      "print-three-lower.asm"      -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Lowercase, DefinitionsMode.UseLiterals)
      ),
      "snake-easy-6502.asm"        -> SnakeEasy6502.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitions)
      )
    )

  def run: Task[Unit] =
    for {
      // just a traverse in slow motion...
      _ <- programs
             .map { case (f, s) => File(s"data/$f").writeLine(s) }
             .foldLeft[Task[Unit]](ZIO.unit)((acc, z) => acc *> z)
    } yield ()
