package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

/**
  * @tparam A The return type of the read
  */
case class VolatileDevice[A](address: Address) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}
