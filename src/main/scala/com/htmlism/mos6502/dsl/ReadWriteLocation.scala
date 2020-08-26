package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

/**
  * @param name A name for this location, used to alias its address
  *
  * @tparam A The input type of the write and the output type of the read
  */
case class ReadWriteLocation[A](name: String, address: ZeroAddress) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }

  def write(x: A)(implicit ctx: AssemblyContext): Unit = {
    val _ = (x, ctx)
    //    ctx.push(STA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}

object ReadWriteLocation {
  implicit def readWriteLocationDefinable[A]: Definable[ReadWriteLocation[A]] =
    new Definable[ReadWriteLocation[A]] {
      def toDefinitions(x: ReadWriteLocation[A]): List[Definition[ZeroAddress]] =
        List {
          Definition(x.name, x.address)
        }
    }
}
