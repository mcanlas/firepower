package com.htmlism.nescant

object GlobalAddress {
  implicit val sourceForGlobalAddress: Source[GlobalAddress] =
    new Source[GlobalAddress] {}

  implicit val sinkForGlobalAddress: Sink[GlobalAddress] =
    new Sink[GlobalAddress] {}
}

case class GlobalAddress(n: Int)
