package com.htmlism.firepower.demo.asm

import com.htmlism.firepower.demo.str.Paragraph

sealed trait AsmBlock

case class CommentBlock(xs: List[String]) extends AsmBlock

case class CodeBlock(name: Option[String], chunks: List[CodeBlock.Chunk]) extends AsmBlock

object CodeBlock:
  case class Chunk(label: Option[String], instructions: List[Chunk.Instruction])

  object Chunk:
    case class Instruction(code: String, comment: Option[String])

object AsmBlock:
  def toParagraph(xs: AsmBlock): Paragraph =
    xs match
      case CommentBlock(ys) =>
        Paragraph(ys.map("; " + _))

      case CodeBlock(oLabel, _) =>
        Paragraph(
          oLabel
            .map(_ + ":")
            .toList
        )

object CommentBlock:
  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
