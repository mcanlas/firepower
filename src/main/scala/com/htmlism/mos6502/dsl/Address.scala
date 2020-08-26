package com.htmlism.mos6502.dsl

sealed trait Address {
  def n: Int
}

object ZeroAddress {
  implicit val operandZero: Operand[ZeroAddress] =
    new Operand[ZeroAddress] {
      val operandType: OperandType =
        MemoryLocation

      def toShow(x: ZeroAddress): String =
        String.format("zero address 0x%02x", x.n)

      def toAddressLiteral(x: ZeroAddress): String =
        String.format("$%02x", x.n)
    }

  implicit val definitionValueForZero: DefinitionValue[ZeroAddress] =
    operandZero.toAddressLiteral(_)
}

case class ZeroAddress(n: Int) extends Address

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

  implicit val definitionValueForGlobal: DefinitionValue[GlobalAddress] =
    operandGlobal.toAddressLiteral(_)
}

case class GlobalAddress(n: Int) extends Address
