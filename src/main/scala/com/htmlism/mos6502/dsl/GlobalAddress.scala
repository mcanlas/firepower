package com.htmlism.mos6502.dsl

import com.htmlism._

object GlobalAddress {
  implicit val operandGlobal: Operand[GlobalAddress] =
    (x: GlobalAddress) => String.format("$%04x", x.n)
}

case class GlobalAddress(n: Int) {
  def write[A](x: A)(implicit ctx: AssemblyContext, ev: Operand[A]): Unit = {
    ctx.push(LDA, x, s"write value ${ev.toAddressLiteral(x)} to address $n")
    ctx.push(STA, this, "")
  }
}
