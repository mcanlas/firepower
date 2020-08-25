package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

case class ReadWriteDevice[A](address: Address) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }

  def write(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
    //    ctx.push(STA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}
