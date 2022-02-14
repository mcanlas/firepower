package com.htmlism.nescant

object GlobalAddress:
  implicit val sourceForGlobalAddress: ByteSource[GlobalAddress] =
    new ByteSource[GlobalAddress] {}

  implicit val sinkForGlobalAddress: ByteSink[GlobalAddress] =
    new ByteSink[GlobalAddress] {}

case class GlobalAddress(n: Int)
