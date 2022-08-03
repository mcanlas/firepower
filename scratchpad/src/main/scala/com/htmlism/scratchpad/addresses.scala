package com.htmlism.scratchpad

object Address:
  def zero(n: Int): ZeroPageAddress =
    ZeroPageAddress(n % 256)

  def absolute(n: Int): GlobalAddress =
    GlobalAddress(n % (256 * 256))

case class ZeroPageAddress(n: Int)

case class GlobalAddress(n: Int)

sealed trait ReadAddress:
  def addr: ZeroPageAddress

sealed trait WriteAddress:
  def addr: ZeroPageAddress

case class Volatile(addr: ZeroPageAddress) extends ReadAddress

case class ReadWriteAddress(addr: ZeroPageAddress) extends ReadAddress with WriteAddress

case class WriteOnlyAddress(addr: ZeroPageAddress) extends WriteAddress
