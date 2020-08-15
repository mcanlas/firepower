package com.htmlism.mos6502.dsl

object ZeroAddress {
  implicit val operandZero: Operand[ZeroAddress] =
    new Operand[ZeroAddress] {
      val operandType: OperandType =
        MemoryLocation

      def toShow(x: ZeroAddress): String =
        String.format("global address 0x%02x", x.n)

      def toAddressLiteral(x: ZeroAddress): String =
        String.format("$%02x", x.n)
    }
}

case class ZeroAddress(n: Int)
