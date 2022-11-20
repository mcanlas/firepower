package com.htmlism.scratchpad

object Encoded:
  trait Byte[A]

  object Byte:
    given Encoded.Byte[Int] with {}

  trait Word[A]
