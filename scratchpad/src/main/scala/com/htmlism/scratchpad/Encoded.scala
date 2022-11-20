package com.htmlism.scratchpad

object Encoded:
  trait Byte[A]

  object Byte:
    given intByte: Encoded.Byte[Int] with {}

  trait Word[A]
