package com.htmlism.firepower.demo.str

/**
  * An abstraction for groups of lines that are eventually separated by newlines, but between paragraphs there's an
  * extra newline
  */
case class Paragraph(xs: List[String])

object Paragraph:
  def apply(xs: String*): Paragraph =
    Paragraph(xs.toList)

  def mkLines(ps: List[Paragraph]): List[String] =
    interFlatMap(ps)(List(""), _.xs)

  def interFlatMap[A, B](xs: List[A])(x: List[B], f: A => List[B]): List[B] =
    xs match
      case head :: tail =>
        f(head) ::: tail.flatMap(a => x ::: f(a))

      case Nil =>
        Nil
