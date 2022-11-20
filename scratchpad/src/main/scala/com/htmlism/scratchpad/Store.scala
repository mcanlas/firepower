package com.htmlism.scratchpad

object Store:
  def fromA[B](dest: WriteLease.ByteAddress[B]): Asm2[Reg.A, B] =
    Byte[Reg.A, B](dest)

  def fromX[B](dest: WriteLease.ByteAddress[B]): Asm2[Reg.X, B] =
    Byte[Reg.X, B](dest)

  def fromY[B](dest: WriteLease.ByteAddress[B]): Asm2[Reg.Y, B] =
    Byte[Reg.Y, B](dest)

  case class Byte[R, A](dest: WriteLease.ByteAddress[A])(using R: Register[R]) extends Asm2[R, A]:
    def xs: List[String] =
      List(R.store + " " + dest.address)
