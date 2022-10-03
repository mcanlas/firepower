package com.htmlism.scratchpad

sealed trait Address:
  def n: Int

  def alias: String

class ZeroPageAddress(val n: Int, val alias: String) extends Address

class AbsoluteAddress(val n: Int, val alias: String) extends Address

sealed trait ReadAddress[A] extends Address

sealed trait WriteAddress[A] extends Address

trait Volatile[A] extends ReadAddress[A]

trait ReadWriteAddress[A] extends ReadAddress[A] with WriteAddress[A]

trait WriteOnlyAddress[A] extends WriteAddress[A]
