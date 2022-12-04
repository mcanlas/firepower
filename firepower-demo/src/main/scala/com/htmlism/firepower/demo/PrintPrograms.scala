package com.htmlism.firepower.demo

import cats.syntax.all._

import com.htmlism.firepower.demo.asm._
import com.htmlism.firepower.demo.str._
import zio.*

import java.io.IOException
import com.htmlism.rufio.withzio.*

object PrintPrograms extends ZIOAppDefault:
  private def writeLine(file: String)(s: String) =
    File(s"data/$file.txt")
      .writeLine(s)

  private val programs =
    List[(String, String)](
      "one-line.txt"        -> "one line",
      "two-lines.txt"       -> (Line.mkString _)
        .apply(List("foo", "bar")),
      "two-paragraphs.txt"  -> (Line.mkString _)
        .compose(Paragraph.mkLines)
        .apply(
          List(
            Paragraph(List("foo", "bar")),
            Paragraph(List("alpha", "bravo"))
          )
        ),
      "annotated-snake.asm" -> (Line.mkString _)
        .compose(Paragraph.mkLines)
        .compose((xs: List[AsmBlock]) => xs.flatMap(AsmBlock.toParagraphs))
        .apply(
          List(
            CommentBlock.fromMultiline(asciiArt),
            CommentBlock(List("Change direction: W A S D")),
            AnonymousCodeBlock(
              List(
                AsmBlock.Intent(
                  None,
                  List(
                    AsmBlock.Intent.Instruction("hello", None),
                    AsmBlock.Intent.Instruction("world", "instruction comment".some)
                  )
                ),
                AsmBlock.Intent(
                  "this block has some preamble".some,
                  List(
                    AsmBlock.Intent.Instruction("hello", None),
                    AsmBlock.Intent.Instruction("world", "instruction comment".some)
                  )
                )
              )
            ),
            NamedCodeBlock(
              "labeled",
              List(
                AsmBlock.Intent(
                  None,
                  List(
                    AsmBlock.Intent.Instruction("hello", None),
                    AsmBlock.Intent.Instruction("world", "instruction comment".some)
                  )
                ),
                AsmBlock.Intent(
                  "this block has some preamble".some,
                  List(
                    AsmBlock.Intent.Instruction("hello", None),
                    AsmBlock.Intent.Instruction("world", "instruction comment".some)
                  )
                )
              )
            )
          )
        )
    )

  lazy val asciiArt =
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
