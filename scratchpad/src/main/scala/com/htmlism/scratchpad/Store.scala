package com.htmlism.scratchpad

object Store:
  def fromA[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.A, A] =
    Byte[Reg.A, A](dest)

  def fromX[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.X, A] =
    Byte[Reg.X, A](dest)

  def fromY[A](dest: WriteLease.ByteAddress[A]): Asm2[Reg.Y, A] =
    Byte[Reg.Y, A](dest)

  case class Byte[R, A](dest: WriteLease.ByteAddress[A])(using R: Register[R]) extends Asm2[R, A]:
    def xs: List[String] =
      List(R.store + " " + dest.address)
