package com.htmlism.mos6502.dsl

import cats.implicits._

import com.htmlism.mos6502.model.Instruction

sealed trait Statement {
  def toAsm: String

  def toTriplet: (String, Option[String], Option[String])
}

object Statement {
  val indent: String =
    "  "
}

case class UnaryInstruction(instruction: Instruction, comment: Option[String]) extends Statement {
  def toAsm: String = {
    val left =
      instruction.toString

    comment match {
      case Some(c) =>
        Statement.indent + f"$left%-16s ; " + c
      case None =>
        Statement.indent + left
    }
  }

  def toTriplet: (String, Option[String], Option[String]) =
    (instruction.toString, None, comment)
}

case class InstructionWithOperand[A](instruction: Instruction, operand: A, comment: Option[String])(implicit
    ev: Operand[A]
) extends Statement {
  def toAsm: String = {
    val left =
      instruction.toString

    val operandStr =
      ev.toAddressLiteral(operand)

    comment match {
      case Some(c) =>
        Statement.indent + f"$left%-5s $operandStr%-11s; " + c
      case None =>
        Statement.indent + f"$left%-5s $operandStr"
    }
  }

  def toTriplet: (String, Option[String], Option[String]) =
    (instruction.toString, ev.toAddressLiteral(operand).some, comment)
}
