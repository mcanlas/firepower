package com.htmlism.nescant

trait Operand[A]:
  def encode(x: A): String

object Operand:
  given operandForInt: Operand[Int] =
    _.toString
