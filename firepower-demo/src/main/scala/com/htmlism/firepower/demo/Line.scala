package com.htmlism.firepower.demo

object Line:
  def mkString(xs: List[String]): String =
    xs
      .mkString("\n")
