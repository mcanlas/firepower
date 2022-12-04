package com.htmlism.firepower.demo.str

/**
  * An abstraction for groups of lines that are eventually separated by newlines, but between paragraphs there's an
  * extra newline
  */
case class Paragraph(xs: List[String])

object Paragraph:
  val blankLine: Paragraph =
    Paragraph(List(""))

  def apply(xs: String*): Paragraph =
    Paragraph(xs.toList)

  def mkLines(ps: List[Paragraph]): List[String] =
    interlace(blankLine)(ps)
      .flatMap(_.xs)

  private def interlace[A](x: A)(xs: List[A]): List[A] =
    xs match
      case head :: tail =>
        head :: tail.flatMap(a => List(x, a))

      case Nil =>
        Nil
