package com.htmlism.mos6502

package object dsl {
  def hex(n: Int): String =
    String.format("$%02x", n)

  def hex(n: ZeroAddress): String =
    String.format("$%02x", n.n)

  def hex(n: GlobalAddress): String =
    String.format("$%04x", n.n)
}
