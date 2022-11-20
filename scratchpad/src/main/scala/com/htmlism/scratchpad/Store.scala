package com.htmlism.scratchpad

object Store:
  def from[R: Register, A: WriteLease.Byte]: Asm2[R, A] =
    Byte[R, A]()

  case class Byte[R, A]()(using R: Register[R], A: WriteLease.Byte[A]) extends Asm2[R, A]:
    def xs: List[String] =
      List("ST" + R.name)
