package com.htmlism.mos6502.dsl

object ZeroAddress {
  implicit val operandZero: Operand[ZeroAddress] =
    (x: ZeroAddress) => String.format("$%02x", x.n)
}

case class ZeroAddress(n: Int)
