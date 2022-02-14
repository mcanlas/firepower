package com.htmlism.mos6502.dsl

import com.htmlism.mos6502.model._

/**
  * A typed collection, like memory mapped to screen pixels. Access by index instead of by address.
  */
case class IndexedAddressCollection[A](baseAddress: Int, name: String)(implicit ev: Operand[A]):
  def apply(n: Int): GlobalAddress =
    GlobalAddress(baseAddress + n)

  def write(n: Int, x: A)(implicit ctx: AssemblyContext): Unit =
    ctx.push(LDA, x, s"write ${ev.toShow(x)} to $name ($n)")
    ctx.push(STA, GlobalAddress(baseAddress + n), "")
