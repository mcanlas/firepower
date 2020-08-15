package com.htmlism.mos6502.dsl

sealed trait OperandType

case object ValueLiteral extends OperandType

case object MemoryLocation extends OperandType
