package com.htmlism.scratchpad

object Load:
  def const[R: Register, X: Encoded.Byte](x: X): Asm1[R] =
    Const(x)

  case class Const[R, X: Encoded.Byte](x: X)(using R: Register[R]) extends Asm1[R]:
    def xs: List[String] =
      List("LD" + R.name)
