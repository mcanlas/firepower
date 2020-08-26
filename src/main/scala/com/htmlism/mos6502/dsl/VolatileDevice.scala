package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

/**
  * For a memory-mapped device that may return different values across multiple reads (e.g. a random number generator)
  *
  * @param name A name for this device, used to alias its address
  *
  * @tparam A The return type of the read
  */
case class VolatileDevice[A](name: String, address: Address) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}
