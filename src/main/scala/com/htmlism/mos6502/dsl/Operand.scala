package com.htmlism.mos6502.dsl

trait Operand[A] {
  self =>

  def toAddressLiteral(x: A): String

  /**
    * Suitable for comments
    */
  def toShow(x: A): String

  def operandType: OperandType
}

object Operand {
  implicit val operandInt: Operand[Int] =
    new Operand[Int] {
      val operandType: OperandType =
        ValueLiteral

      def toShow(x: Int): String =
        x.toString

      def toAddressLiteral(x: Int): String =
        String.format("#$%02x", x)
    }

  implicit def operandForMapping[A](implicit ev: Mapping[A]): Operand[A] =
    new Operand[A] {
      def toAddressLiteral(x: A): String =
        "#" + ev.label(x)

      def toShow(x: A): String =
        ev.label(x)

      def operandType: OperandType =
        ValueLiteral
    }
}
