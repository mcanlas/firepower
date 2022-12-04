package com.htmlism.firepower.core

object Store:
  def fromA[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.A, A] =
    from[Reg.A, A](dest)

  def fromX[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.X, A] =
    from[Reg.X, A](dest)

  def fromY[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.Y, A] =
    from[Reg.Y, A](dest)

  private def from[R, A](dest: WriteLease.ByteAddress[A])(using R: Register[R]) =
    Asm2Instructions[R, A](List(R.store + " " + dest.address))
