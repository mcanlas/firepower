package com.htmlism.mos6502.dsl

trait EnumAsByte[A] {
  def toByte(x: A): Int
}
