package com.htmlism.scratchpad

sealed trait Address:
  def n: Int

  def alias: String

case class ZeroPageAddress(n: Int, alias: String) extends Address

case class AbsoluteAddress(n: Int, alias: String) extends Address

sealed trait ReadAddress[A <: Address]:
  def addr: A

sealed trait WriteAddress[A <: Address]:
  def addr: A

case class Volatile[A <: Address](addr: A) extends ReadAddress[A]

case class ReadWriteAddress[A <: Address](addr: A) extends ReadAddress[A] with WriteAddress[A]

case class WriteOnlyAddress[A <: Address](addr: A) extends WriteAddress[A]
