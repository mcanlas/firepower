package com.htmlism.firepower.demo.asm

import com.htmlism.firepower.demo.str.Paragraph

sealed trait AsmBlock

case class CommentBlock(xs: List[String]) extends AsmBlock

case class NamedCodeBlock(name: String, comment: Option[String], intents: List[AsmBlock.Intent]) extends AsmBlock

case class AnonymousCodeBlock(intents: List[AsmBlock.Intent]) extends AsmBlock

object AsmBlock:
  def toComment(s: String): String =
    "; " + s

  def withIndent(s: String): String =
    "  " + s

  def toParagraphs(xs: AsmBlock): List[Paragraph] =
    xs match
      case CommentBlock(ys) =>
        List(
          Paragraph(ys.map(toComment))
        )

      case NamedCodeBlock(label, oComment, _) =>
        val headerParagraph =
          Paragraph(
            List(label + ":") ++ oComment.map(toComment).map(withIndent).toList
          )

        val intentParagraphs =
          Nil

        headerParagraph :: intentParagraphs

      case AnonymousCodeBlock(_) =>
        Nil

  case class Intent(label: Option[String], instructions: List[Intent.Instruction])

  object Intent:
    case class Instruction(code: String, comment: Option[String])

object CommentBlock:
  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
