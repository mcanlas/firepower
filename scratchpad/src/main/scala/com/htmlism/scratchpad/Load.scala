package com.htmlism.scratchpad

object Load:
  def const[R: Register, A: Encoded.Byte](x: A): Asm1[R] =
    Const(x)

  case class Const[R, A: Encoded.Byte](x: A)(using R: Register[R]) extends Asm1[R]:
    def xs: List[String] =
      List("LD" + R.name)
