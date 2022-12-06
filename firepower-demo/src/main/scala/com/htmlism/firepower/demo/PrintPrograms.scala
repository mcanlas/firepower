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
      "one-line.txt"          -> "one line",
      "two-lines.txt"         -> List("foo", "bar")
        .pipe(Line.mkString),
      "two-paragraphs.txt"    -> List(
        List("foo", "bar"),
        List("alpha", "bravo")
      )
        .pipe(xxs => AsmBlock.interFlatMap(xxs)(List("", ""), identity))
        .pipe(Line.mkString),
      "annotated-snake.asm"   -> AnnotatedSnake.program,
      "print-three-upper.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Uppercase, DefinitionsMode.UseLiterals)
      ),
      "print-three-lower.asm" -> PrintThree.assemble(
        AssemblerOptions(InstructionCase.Lowercase, DefinitionsMode.UseLiterals)
      )
    )

  def run: Task[Unit] =
    for {
      // just a traverse in slow motion...
      _ <- programs
             .map { case (f, s) => File(s"data/$f").writeLine(s) }
             .foldLeft[Task[Unit]](ZIO.unit)((acc, z) => acc *> z)
    } yield ()
