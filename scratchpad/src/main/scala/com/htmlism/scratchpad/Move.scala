package com.htmlism.scratchpad

// https://mads.atari8.info/mads_eng.html#_mv
object Move:
  def const[R: Register, A: WriteLease, X: ByteLike](x: X): Asm2[R, A] =
    // TODO freeze this into a data structure or a composition of data structures of load and store
    Asm2Instructions(List(""))

  def from[R: Register, A: ReadLease, B: WriteLease]: Asm3[R, A, B] =
    Asm3Instructions(List(""))

  object Word:
    def const[R: Register, A: WriteLease, X: WordLike](x: X): Asm2[R, A] =
      Asm2Instructions(List(""))
