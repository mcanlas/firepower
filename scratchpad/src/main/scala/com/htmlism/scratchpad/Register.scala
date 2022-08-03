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

  class A
  class X
  class Y
