package com.htmlism.mos6502.dsl

trait Definable[A] {
  def toDefinition(x: A): Definition[_]
}
