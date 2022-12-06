package com.htmlism.firepower.demo

import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object AnnotatedSnake:
  val program: String =
    List(
      CommentBlock.fromMultiline(asciiArt),
      CommentBlock(List("Change direction: W A S D")),
      AnonymousCodeBlock(
        List(
          AsmBlock.Intent(
            None,
            List(
              AsmBlock.Intent.Instruction.One("lda", "$00", None),
              AsmBlock.Intent.Instruction.One("lda", "$01", "instruction comment".some)
            )
          ),
          AsmBlock.Intent(
            "this block has some preamble".some,
            List(
              AsmBlock.Intent.Instruction.One("lda", "$00", None),
              AsmBlock.Intent.Instruction.One("lda", "$01", "instruction comment".some)
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
              AsmBlock.Intent.Instruction.One("lda", "$00", None),
              AsmBlock.Intent.Instruction.One("lda", "$01", "instruction comment".some)
            )
          ),
          AsmBlock.Intent(
            "this block has some preamble".some,
            List(
              AsmBlock.Intent.Instruction.One("lda", "$00", None),
              AsmBlock.Intent.Instruction.One("lda", "$01", "instruction comment".some)
            )
          )
        )
      )
    )
      .map(AsmBlock.toLines)
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
      .pipe(str.Line.mkString)

  private lazy val asciiArt =
    """ ___           _        __ ___  __ ___
      |/ __|_ _  __ _| |_____ / /| __|/  \_  )
      |\__ \ ' \/ _` | / / -_) _ \__ \ () / /
      ||___/_||_\__,_|_\_\___\___/___/\__/___|""".stripMargin
