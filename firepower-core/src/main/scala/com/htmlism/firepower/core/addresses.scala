package com.htmlism.firepower.core

sealed trait Address:
  def n: Int

  def alias: String

class ZeroPageAddress(val n: Int, val alias: String) extends Address

class AbsoluteAddress(val n: Int, val alias: String) extends Address

sealed trait ReadByteAddress[A] extends Address

sealed trait WriteByteAddress[A] extends Address

/**
  * To be extended by the address companion object, to signify that this is a volatile resource of length byte
  */
trait VolatileByte[A] extends ReadByteAddress[A]

/**
  * To be extended by the address companion object, to signify that this is a read/write resource of length byte
  */
trait ReadWriteByteAddress[A] extends ReadByteAddress[A] with WriteByteAddress[A]

/**
  * To be extended by the address companion object, to signify that this is a write-only resource of length byte
  */
trait WriteOnlyByteAddress[A] extends WriteByteAddress[A]
