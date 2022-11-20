package com.htmlism.scratchpad

// on the 6502, every register of this kind can store and load; so that capability was not parceled out into traits
// (unlike read/write leases)
sealed trait Register[A]:
  def name: String

  def load: String

  def store: String

object Register:
  object A:
    given Register[A] with
      def name: String =
        "A"

      def load: String =
        "LDA"

      def store: String =
        "STA"

  class A

  object X:
    given Register[X] with
      def name: String =
        "X"

      def load: String =
        "LDX"

      def store: String =
        "STX"

  class X

  object Y:
    given Register[Y] with
      def name: String =
        "Y"

      def load: String =
        "LDY"

      def store: String =
        "STY"

  class Y
