package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

/**
  * @tparam A The input type of the write and the output type of the read
  */
case class ReadWriteLocation[A](address: Address) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }

  def write(x: A)(implicit ctx: AssemblyContext): Unit = {
    val _ = (x, ctx)
    //    ctx.push(STA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}
