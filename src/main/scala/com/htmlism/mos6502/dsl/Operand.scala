package com.htmlism.mos6502.dsl

import cats.Contravariant

trait Operand[A] {
  def toAddressLiteral(x: A): String

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

  implicit val contra: Contravariant[Operand] =
    new Contravariant[Operand] {
      def contramap[A, B](fa: Operand[A])(f: B => A): Operand[B] =
        new Operand[B] {
          val operandType: OperandType =
            fa.operandType

          def toShow(x: B): String =
            fa.toShow(f(x))

          def toAddressLiteral(x: B): String =
            fa.toAddressLiteral(f(x))
        }
    }
}
