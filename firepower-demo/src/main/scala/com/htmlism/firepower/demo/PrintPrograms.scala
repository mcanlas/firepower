package com.htmlism.firepower.demo

import scala.util.chaining.*

import zio.*

import com.htmlism.firepower.core.AsmBlock.*
import com.htmlism.firepower.core.AssemblerOptions.*
import com.htmlism.firepower.core.*
import com.htmlism.rufio.withzio.*

object PrintPrograms extends ZIOAppDefault:
  private val programs =
    List[(String, List[String])](
      // FEATURE: writing lines to a file is easy (thanks, rufio)
      "two-lines.txt" -> List("foo", "bar"),

      // FEATURE: writing paragraphs separated by newlines is easy
      "two-paragraphs.txt" -> List(
        List("foo", "bar"),
        List("alpha", "bravo")
      )
        .pipe(xxs => AsmBlock.interFlatMap(xxs)(List("", ""), identity)),
      "feature-demo.asm" -> FeatureDemo.program,
      "print-three-upper-math.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitionsWithMath)
      ),
      "print-three-upper.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitions)
      ),
      "print-three-lower.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Lowercase, DefinitionsMode.UseLiterals)
      ),
      "snake-easy-6502.asm" -> SnakeEasy6502.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseDefinitions)
      )
    )

  def run: Task[Unit] =
    for {
      // just a traverse in slow motion...
      _ <- programs
        .map { case (f, xs) => File(s"data/$f").writeLines(xs) }
        .foldLeft[Task[Unit]](ZIO.unit)((acc, z) => acc *> z)
    } yield ()
