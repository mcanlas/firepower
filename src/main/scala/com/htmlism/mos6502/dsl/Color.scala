package com.htmlism.mos6502.dsl

sealed trait Color

object Color {
  implicit val ev: Operand[Color] =
    Operand.operandInt
      .contra(toByte, _.toString)

  def toByte(x: Color): Int =
    x match {
      case Black      => 0x0
      case White      => 0x1
      case Red        => 0x2
      case Cyan       => 0x3
      case Purple     => 0x4
      case Green      => 0x5
      case Blue       => 0x6
      case Yellow     => 0x7
      case Orange     => 0x8
      case Brown      => 0x9
      case LightRed   => 0xa
      case DarkGrey   => 0xb
      case Grey       => 0xc
      case LightGreen => 0xd
      case LightBlue  => 0xe
      case LightGrey  => 0xf
    }

  case object Black      extends Color
  case object White      extends Color
  case object Red        extends Color
  case object Cyan       extends Color
  case object Purple     extends Color
  case object Green      extends Color
  case object Blue       extends Color
  case object Yellow     extends Color
  case object Orange     extends Color
  case object Brown      extends Color
  case object LightRed   extends Color
  case object DarkGrey   extends Color
  case object Grey       extends Color
  case object LightGreen extends Color
  case object LightBlue  extends Color
  case object LightGrey  extends Color
}
