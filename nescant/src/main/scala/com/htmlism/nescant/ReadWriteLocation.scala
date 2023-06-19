package com.htmlism.nescant

/**
  * @param name
  *   A name for this location, used to alias its address
  *
  * @tparam A
  *   The input type of the write and the output type of the read
  */
case class ReadWriteLocation[A](name: String, address: ZeroPageAddress)

object ReadWriteLocation:
  given sourceForReadWriteLocation[A]: ByteSource[ReadWriteLocation[A]] =
    new ByteSource[ReadWriteLocation[A]] {}

  given sinkForReadWriteLocation[A]: ByteSink[ReadWriteLocation[A]] =
    new ByteSink[ReadWriteLocation[A]] {}

  given operandForReadWriteLocation[A]: Operand[ReadWriteLocation[A]] =
    new Operand[ReadWriteLocation[A]]:
      def encode(x: ReadWriteLocation[A]): String = ""
