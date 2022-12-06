package com.htmlism.firepower.demo

import scala.util.chaining._

import cats.syntax.all._

import com.htmlism.firepower.core.AsmBlock._
import com.htmlism.firepower.core._

object PrintThree:
  val program: String =
    List(
      AnonymousCodeBlock(
        List(
          AsmBlock.Intent(
            None,
            List(
              AsmBlock.Intent.Instruction("LDA #$01", None),
              AsmBlock.Intent.Instruction("STA $0200", None)
            )
          ),
          AsmBlock.Intent(
            None,
            List(
              AsmBlock.Intent.Instruction("LDA #$05", None),
              AsmBlock.Intent.Instruction("STA $0201", None)
            )
          ),
          AsmBlock.Intent(
            None,
            List(
              AsmBlock.Intent.Instruction("LDA #$08", None),
              AsmBlock.Intent.Instruction("STA $0202", None)
            )
          )
        )
      )
    )
      .map(AsmBlock.toLines)
      .pipe(xs => AsmBlock.interFlatMap(xs)(List("", ""), identity))
      .pipe(str.Line.mkString)
