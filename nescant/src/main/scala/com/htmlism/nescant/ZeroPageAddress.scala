package com.htmlism.nescant

object ZeroPageAddress:
  given sourceForZeroPageAddress: ByteSource[ZeroPageAddress] =
    new ByteSource[ZeroPageAddress] {}

  given sinkForZeroPageAddress: ByteSink[ZeroPageAddress] =
    new ByteSink[ZeroPageAddress] {}

case class ZeroPageAddress(n: Int)
