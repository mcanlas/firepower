package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

sealed trait Color

object Color {
  implicit val colorOperand: Operand[Color] =
    new Operand[Color] {
      def toAddressLiteral(x: Color): String =
        "#" + x.toString.toLowerCase()

      def toShow(x: Color): String =
        x.toString

      def operandType: OperandType =
        ValueLiteral
    }

  implicit val colorEnum: EnumAsm[Color] =
    new EnumAsm[Color] {
      def comment: String =
        "Colors"

      def all: NonEmptyList[Color] =
        NonEmptyList.of(
          Black,
          White,
          Red,
          Cyan,
          Purple,
          Green,
          Blue,
          Yellow,
          Orange,
          Brown,
          LightRed,
          DarkGrey,
          Grey,
          LightGreen,
          LightBlue,
          LightGrey
        )

      def label(x: Color): String =
        x.toString.toLowerCase()

      def comment(x: Color): String =
        x.toString
    }

  case object Black extends Color
  case object White extends Color
  case object Red extends Color
  case object Cyan extends Color
  case object Purple extends Color
  case object Green extends Color
  case object Blue extends Color
  case object Yellow extends Color
  case object Orange extends Color
  case object Brown extends Color
  case object LightRed extends Color
  case object DarkGrey extends Color
  case object Grey extends Color
  case object LightGreen extends Color
  case object LightBlue extends Color
  case object LightGrey extends Color
}
