package com.htmlism.firepower.demo

case class CommentBlock(xs: List[String])

object CommentBlock:
  def toParagraph(cb: CommentBlock): Paragraph =
    Paragraph(cb.xs.map("; " + _))

  def fromMultiline(s: String): CommentBlock =
    CommentBlock(s.split("\\n").toList)
