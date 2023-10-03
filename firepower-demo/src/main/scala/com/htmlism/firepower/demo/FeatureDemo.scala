package com.htmlism.firepower.demo

import scala.util.chaining.*

import cats.syntax.all.*

import com.htmlism.firepower.core.AsmBlock.*
import com.htmlism.firepower.core.*

object FeatureDemo:
  val program: List[String] =
    List(
      CommentBlock.fromMultiline(asciiArt),
      CommentBlock(List("Change direction: W A S D")),
      AnonymousCodeBlock(
        List(
          AsmBlock.Intent(
            None,
            List(
              AsmBlock.Intent.Instruction.one("lda", "$00", None),
              AsmBlock.Intent.Instruction.one("lda", "$01", "instruction comment".some)
            )
          ),
          AsmBlock.Intent(
            "this block has some preamble".some,
            List(
              AsmBlock.Intent.Instruction.one("lda", "$00", None),
              AsmBlock.Intent.Instruction.one("lda", "$01", "instruction comment".some)
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
              AsmBlock.Intent.Instruction.one("lda", "$00", None),
              AsmBlock.Intent.Instruction.one("lda", "$01", "instruction comment".some)
            )
          ),
          AsmBlock.Intent(
            "this block has some preamble".some,
            List(
              AsmBlock.Intent.Instruction.one("lda", "$00", None),
              AsmBlock.Intent.Instruction.one("lda", "$01", "instruction comment".some)
            )
          )
        )
      )
    )
      .map(AsmBlock.toLines(AssemblerOptions.InstructionCase.Lowercase))
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))

  private lazy val asciiArt =
    """ ___           _        __ ___  __ ___
      |/ __|_ _  __ _| |_____ / /| __|/  \_  )
      |\__ \ ' \/ _` | / / -_) _ \__ \ () / /
      ||___/_||_\__,_|_\_\___\___/___/\__/___|""".stripMargin
