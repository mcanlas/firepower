package com.htmlism.scratchpad

// on the 6502, every register of this kind can store and load; so that capability was not parceled out into traits
// (unlike read/write leases)
sealed trait Register[A]:
  def name: String

object Register:
  object A:
    given registerA: Register[A] with
      def name: String = "A"

  class A

  object X:
    given registerX: Register[X] with
      def name: String = "X"

  class X

  object Y:
    given registerY: Register[Y] with
      def name: String = "Y"

  class Y
