package com.htmlism.nescant

//import com.htmlism.mos6502.model._

/**
  * For a memory-mapped device that may return different values across multiple reads (e.g. a random number generator)
  *
  * @param name
  *   A name for this device, used to alias its address
  *
  * @tparam A
  *   The output type of the read
  */
case class VolatileDevice[A](name: String, address: ZeroPageAddress)

object VolatileDevice {
  implicit def sourceForVolatileDevice[A]: ByteSource[VolatileDevice[A]] =
    new ByteSource[VolatileDevice[A]] {}

  implicit def operandForVolatileDevice[A]: Operand[VolatileDevice[A]] =
    new Operand[VolatileDevice[A]] {
      def encode(x: VolatileDevice[A]): String = ""
    }
}
