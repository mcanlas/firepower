package com.htmlism.mos6502.dsl

object GlobalAddress {
  implicit val operandGlobal: Operand[GlobalAddress] =
    new Operand[GlobalAddress] {
      val operandType: OperandType =
        MemoryLocation

      def toShow(x: GlobalAddress): String =
        String.format("global address 0x%04x", x.n)

      def toAddressLiteral(x: GlobalAddress): String =
        String.format("$%04x", x.n)
    }
}

case class GlobalAddress(n: Int)
