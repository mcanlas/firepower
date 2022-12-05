package com.htmlism.firepower.demo

import java.io.IOException

import scala.util.chaining._

import cats.syntax.all._
import zio.*

import com.htmlism.firepower.demo.asm._
import com.htmlism.firepower.demo.str._
import com.htmlism.rufio.withzio.*

object PrintPrograms extends ZIOAppDefault:
  private def writeLine(file: String)(s: String) =
    File(s"data/$file.txt")
      .writeLine(s)

  private val programs =
    List[(String, String)](
      "one-line.txt"        -> "one line",
      "two-lines.txt"       -> List("foo", "bar")
        .pipe(Line.mkString),
      "two-paragraphs.txt"  -> List(
        List("foo", "bar"),
        List("alpha", "bravo")
      )
        .pipe(xxs => AsmBlock.interFlatMap(xxs)(List("", ""), identity))
        .pipe(Line.mkString),
      "annotated-snake.asm" -> List(
        CommentBlock.fromMultiline(asciiArt),
        CommentBlock(List("Change direction: W A S D")),
        AnonymousCodeBlock(
          List(
            AsmBlock.Intent(
              None,
              List(
                AsmBlock.Intent.Instruction("lda $00", None),
                AsmBlock.Intent.Instruction("lda $01", "instruction comment".some)
              )
            ),
            AsmBlock.Intent(
              "this block has some preamble".some,
              List(
                AsmBlock.Intent.Instruction("lda $00", None),
                AsmBlock.Intent.Instruction("lda $01", "instruction comment".some)
              )
            )
          )
        ),
        NamedCodeBlock(
          "labeled",
          "This is a subroutine description".some,
          List(
            AsmBlock.Intent(
              None,
              List(
                AsmBlock.Intent.Instruction("lda $00", None),
                AsmBlock.Intent.Instruction("lda $01", "instruction comment".some)
              )
            ),
            AsmBlock.Intent(
              "this block has some preamble".some,
              List(
                AsmBlock.Intent.Instruction("lda $00", None),
                AsmBlock.Intent.Instruction("lda $01", "instruction comment".some)
              )
            )
          )
        )
      )
        .map(AsmBlock.toLines)
        .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
        .pipe(Line.mkString)
    )

  private lazy val asciiArt =
    """ ___           _        __ ___  __ ___
      |/ __|_ _  __ _| |_____ / /| __|/  \_  )
      |\__ \ ' \/ _` | / / -_) _ \__ \ () / /
      ||___/_||_\__,_|_\_\___\___/___/\__/___|""".stripMargin

  def run: Task[Unit] =
    for {
      // just a traverse in slow motion...
      _ <- programs
             .map { case (f, s) => File(s"data/$f").writeLine(s) }
             .foldLeft[Task[Unit]](ZIO.unit)((acc, z) => acc *> z)
    } yield ()
