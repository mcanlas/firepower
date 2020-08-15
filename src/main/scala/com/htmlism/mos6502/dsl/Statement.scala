package com.htmlism.mos6502.dsl

import com.htmlism.mos6502.model.Instruction

sealed trait Statement {
  def toAsm: String
}

case class UnaryInstruction(instruction: Instruction, comment: Option[String]) extends Statement {
  def toAsm: String = {
    val left =
      instruction.toString

    comment match {
      case Some(c) =>
        f"$left%-16s ; " + c
      case None =>
        left
    }
  }
}

case class InstructionWithOperand[A](instruction: Instruction, operand: A, comment: Option[String])(
    implicit ev: Operand[A]
) extends Statement {
  def toAsm: String = {
    val left =
      instruction.toString

    val operandStr =
      ev.toAddressLiteral(operand)

    comment match {
      case Some(c) =>
        f"$left%-5s $operandStr%-11s; " + c
      case None =>
        f"$left%-5s $operandStr"
    }
  }
}
