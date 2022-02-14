package com.htmlism.mos6502.dsl

sealed trait RangeSpec:
  def from: Int

  def to: Int

case class Incrementing(from: Int, to: Int) extends RangeSpec

case class Decrementing(from: Int, to: Int) extends RangeSpec
