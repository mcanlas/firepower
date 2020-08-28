package com.htmlism.nescant

sealed trait Operand

case class LiteralOperand(value: String) extends Operand

case class NamedOperand(name: String, value: String) extends Operand
