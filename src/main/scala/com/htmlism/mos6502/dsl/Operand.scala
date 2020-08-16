package com.htmlism.mos6502.dsl

trait Operand[A] {
  self =>

  def toAddressLiteral(x: A): String

  def toShow(x: A): String

  def operandType: OperandType

  def toDefinitionLiteral(x: A): String

  def contra[B](f: B => A, show: B => String): Operand[B] =
    new Operand[B] {
      val operandType: OperandType =
        self.operandType

      def toShow(x: B): String =
        show(x)

      def toDefinitionLiteral(x: B): String =
        toAddressLiteral(x)

      def toAddressLiteral(x: B): String =
        self.toAddressLiteral(f(x))
    }
}

object Operand {
  implicit val operandInt: Operand[Int] =
    new Operand[Int] {
      val operandType: OperandType =
        ValueLiteral

      def toShow(x: Int): String =
        x.toString

      def toDefinitionLiteral(x: Int): String =
        x.toString

      def toAddressLiteral(x: Int): String =
        String.format("#$%02x", x)
    }
}
