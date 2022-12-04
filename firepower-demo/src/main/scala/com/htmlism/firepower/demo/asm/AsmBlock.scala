package com.htmlism.firepower.demo.asm

import com.htmlism.firepower.demo.str.Paragraph

sealed trait AsmBlock

case class CommentBlock(xs: List[String]) extends AsmBlock

object CommentBlock:
  def toParagraph(xs: AsmBlock): Paragraph =
    xs match
      case CommentBlock(ys) =>
        Paragraph(ys.map("; " + _))

  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
