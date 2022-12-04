package com.htmlism.firepower.core

object Load:
  def constA[B: Encoded.Byte](x: B): Asm1[Reg.A] =
    const(x)

  def constX[B: Encoded.Byte](x: B): Asm1[Reg.X] =
    const(x)

  def constY[B: Encoded.Byte](x: B): Asm1[Reg.Y] =
    const(x)

  private def const[R, A](x: A)(using R: Register[R], A: Encoded.Byte[A]) =
    Asm1Instructions[R](List(s"${R.load} ${A.int(x)}"))
