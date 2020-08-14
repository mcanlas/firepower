package com.htmlism.mos6502.dsl

case class GlobalAddress(n: Int) {
  def write[A : EnumAsByte](x: A)(implicit ctx: AssemblyContext): Unit = {
    val b = implicitly[EnumAsByte[A]].toByte(x)

    ctx.describe(s"write value $b to address $n")
    ctx.pushAsm("LDA #" + hex(b))
    ctx.pushAsm("STA " + hex(this))
  }
}
