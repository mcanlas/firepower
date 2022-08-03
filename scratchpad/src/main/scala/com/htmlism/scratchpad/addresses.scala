package com.htmlism.scratchpad

case class ZeroPageAddress(n: Int, alias: String)

case class GlobalAddress(n: Int, alias: String)

sealed trait ReadAddress:
  def addr: ZeroPageAddress

sealed trait WriteAddress:
  def addr: ZeroPageAddress

case class Volatile(addr: ZeroPageAddress) extends ReadAddress

case class ReadWriteAddress(addr: ZeroPageAddress) extends ReadAddress with WriteAddress

case class WriteOnlyAddress(addr: ZeroPageAddress) extends WriteAddress
