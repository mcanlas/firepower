package com.htmlism.scratchpad

// https://mads.atari8.info/mads_eng.html#_mv
object Move:
  def const[R: Register, A: WriteLease.Byte, X: Encoded.Byte](x: X): Asm2[R, A] =
    Load
      .const[R, X](x)
      .widenWith[A] andThen Store.from[R, A]

  def from[R: Register, A: ReadLease.Byte, B: WriteLease.Byte]: Asm3[R, A, B] =
    Asm3Instructions(List(""))

  object Word:
    def const[R: Register, A: WriteLease.Word, X: Encoded.Word](x: X): Asm2[R, A] =
      Asm2Instructions(List(""))
