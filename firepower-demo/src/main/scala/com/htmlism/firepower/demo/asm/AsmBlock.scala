package com.htmlism.firepower.demo.asm

import com.htmlism.firepower.demo.str.Paragraph

sealed trait AsmBlock

case class CommentBlock(xs: List[String]) extends AsmBlock

case class NamedCodeBlock(name: String, intents: List[AsmBlock.Intent]) extends AsmBlock

case class AnonymousCodeBlock(intents: List[AsmBlock.Intent]) extends AsmBlock

object AsmBlock:
  def toParagraph(xs: AsmBlock): Paragraph =
    xs match
      case CommentBlock(ys) =>
        Paragraph(ys.map("; " + _))

      case NamedCodeBlock(label, _) =>
        Paragraph(
          List(label + ":")
        )

      case AnonymousCodeBlock(_) =>
        Paragraph("")

  case class Intent(label: Option[String], instructions: List[Intent.Instruction])

  object Intent:
    case class Instruction(code: String, comment: Option[String])

object CommentBlock:
  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
