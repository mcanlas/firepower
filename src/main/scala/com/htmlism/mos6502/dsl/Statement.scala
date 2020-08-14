package com.htmlism.mos6502.dsl

import com.htmlism._

sealed trait Statement

case class UnaryInstruction(instruction: Instruction, comment: Option[String]) extends Statement

case class InstructionWithOperand[A : Operand](instruction: Instruction, operand: A, comment: Option[String]) extends Statement
