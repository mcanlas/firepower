package com.htmlism.firepower.demo

case class Line(s: String)

object Line:
  def mkString(xs: List[Line]): String =
    xs
      .iterator
      .map(_.s)
      .mkString("\n")
