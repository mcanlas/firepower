package com.htmlism.nescant

object ZeroPageAddress {
  implicit val sourceForZeroPageAddress: ByteSource[ZeroPageAddress] =
    new ByteSource[ZeroPageAddress] {}

  implicit val sinkForZeroPageAddress: ByteSink[ZeroPageAddress] =
    new ByteSink[ZeroPageAddress] {}
}

case class ZeroPageAddress(n: Int)
