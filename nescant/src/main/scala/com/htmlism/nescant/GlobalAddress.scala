package com.htmlism.nescant

object GlobalAddress:
  given sourceForGlobalAddress: ByteSource[GlobalAddress] =
    new ByteSource[GlobalAddress] {}

  given sinkForGlobalAddress: ByteSink[GlobalAddress] =
    new ByteSink[GlobalAddress] {}

case class GlobalAddress(n: Int)
