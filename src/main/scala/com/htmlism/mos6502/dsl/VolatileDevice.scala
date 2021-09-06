package com.htmlism.mos6502.dsl

//import com.htmlism.mos6502.model._

/**
  * For a memory-mapped device that may return different values across multiple reads (e.g. a random number generator)
  *
  * @param name
  *   A name for this device, used to alias its address
  *
  * @tparam A
  *   The return type of the read
  */
case class VolatileDevice[A](name: String, address: ZeroAddress) {
  def read(implicit ctx: AssemblyContext): Unit = {
    val _ = ctx
//    ctx.push(LDA, ev, s"write ${ev.toShow(x)} to $name ($n)")
  }
}

object VolatileDevice {
  implicit def namedResourceForVolatileDevice[A]: NamedResource[VolatileDevice[A]] =
    new NamedResource[VolatileDevice[A]] {
      def toDefinitions(x: VolatileDevice[A]): List[Definition[ZeroAddress]] =
        List {
          Definition(x.name, x.address, "Volatile generator for A values")
        }
    }
}
