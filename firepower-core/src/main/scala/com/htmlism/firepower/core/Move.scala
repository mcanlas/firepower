package com.htmlism.firepower.core

// https://mads.atari8.info/mads_eng.html#_mv
object Move:
  def constA[A, X: Encoded.Byte](x: X, dest: WriteLease.ByteAddress[A]): Asm2[Reg.A, A] =
    Load
      .constA(x)
      .widenWith[A]
      .andThen(Store.fromA(dest))

  def from[R: Register, A: ReadLease.ByteAddress, B: WriteLease.ByteAddress]: Asm3[R, A, B] =
    Asm3Instructions(List(""))

  object Word:
    def const[R: Register, A, X: Encoded.Word](x: X, dest: WriteLease.ByteAddress[A]): Asm2[R, A] =
      Asm2Instructions(List(""))
