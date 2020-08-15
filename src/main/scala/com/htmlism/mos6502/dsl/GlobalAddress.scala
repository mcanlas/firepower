package com.htmlism.mos6502.dsl

import com.htmlism._

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

case class GlobalAddress(n: Int) {
  def write[A](x: A)(implicit ctx: AssemblyContext, ev: Operand[A]): Unit = {
    ctx.push(LDA, x, s"write ${ev.toShow(x)} to address $n")
    ctx.push(STA, this, "")
  }
}
