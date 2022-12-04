package com.htmlism.firepower.core

object Encoded:
  trait Byte[A]:
    def int(x: A): Int

  object Byte:
    given Encoded.Byte[Int] with
      def int(x: Int): Int =
        x

  trait Word[A]
