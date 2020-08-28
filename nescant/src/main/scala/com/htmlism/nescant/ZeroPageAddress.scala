package com.htmlism.nescant

object ZeroPageAddress {
  implicit val sourceForZeroPageAddress: Source[ZeroPageAddress] =
    new Source[ZeroPageAddress] {}

  implicit val sinkForZeroPageAddress: Sink[ZeroPageAddress] =
    new Sink[ZeroPageAddress] {}
}

case class ZeroPageAddress(n: Int)
