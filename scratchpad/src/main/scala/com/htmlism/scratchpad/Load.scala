package com.htmlism.scratchpad

object Load:
  def constA[B: Encoded.Byte](x: B): Asm1[Reg.A] =
    Const(x)

  def constX[B: Encoded.Byte](x: B): Asm1[Reg.X] =
    Const(x)

  def constY[B: Encoded.Byte](x: B): Asm1[Reg.Y] =
    Const(x)

  case class Const[R, A](x: A)(using R: Register[R], A: Encoded.Byte[A]) extends Asm1[R]:
    def xs: List[String] =
      List(s"${R.load} ${A.int(x)}")
