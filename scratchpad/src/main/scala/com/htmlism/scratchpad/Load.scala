package com.htmlism.scratchpad

object Load:
  def constA[B: Encoded.Byte](x: B): Asm1[Reg.A] =
    Const(x)

  def constX[B: Encoded.Byte](x: B): Asm1[Reg.X] =
    Const(x)

  def constY[B: Encoded.Byte](x: B): Asm1[Reg.Y] =
    Const(x)

  case class Const[R, A: Encoded.Byte](x: A)(using R: Register[R]) extends Asm1[R]:
    def xs: List[String] =
      List("LD" + R.name)
