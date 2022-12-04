package com.htmlism.firepower.demo.asm

import com.htmlism.firepower.demo.str.Paragraph

case class CommentBlock(xs: List[String])

object CommentBlock:
  def toParagraph(cb: CommentBlock): Paragraph =
    Paragraph(cb.xs.map("; " + _))

  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
