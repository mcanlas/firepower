package com.htmlism.scratchpad

sealed trait Register[A]:
  def self: String

object Register:
  given registerA: Register[A] with
    def self: String = "A"

  given registerX: Register[X] with
    def self: String = "X"

  given registerY: Register[Y] with
    def self: String = "Y"

  object A:
    given loadA: Load[A] with
      def instruction: String =
        "LDA"

    given storeA: Store[A] with
      def instruction: String =
        "STA"

  class A

  object X:
    given loadX: Load[X] with
      def instruction: String =
        "LDX"

    given storeX: Store[X] with
      def instruction: String =
        "STX"

  class X

  object Y:
    given loadY: Load[Y] with
      def instruction: String =
        "LDY"

    given storeY: Store[Y] with
      def instruction: String =
        "STY"

  class Y
