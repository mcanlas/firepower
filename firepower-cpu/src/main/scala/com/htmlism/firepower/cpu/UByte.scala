package com.htmlism.firepower.cpu

opaque type UByte =
  Byte

extension (b: UByte)
  def inc: UByte =
    ((b & 0xff) + 1).toByte

  def dec: UByte =
    ((b & 0xff) - 1).toByte

object UByte:
  def apply(n: Int): UByte =
    n.toByte
