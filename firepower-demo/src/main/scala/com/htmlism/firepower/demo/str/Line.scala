package com.htmlism.firepower.demo.str

object Line:
  def mkString(xs: List[String]): String =
    xs
      .mkString("\n")
