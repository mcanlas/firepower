package com.htmlism.nescant

trait Operand[A] {
  def encode(x: A): String
}

object Operand {
  implicit val operandForInt: Operand[Int] =
    _.toString
}
