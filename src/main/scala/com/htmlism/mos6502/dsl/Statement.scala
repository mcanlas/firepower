package com.htmlism.mos6502.dsl

import cats.syntax.all.*

import com.htmlism.mos6502.model.Instruction

sealed trait Statement:
  def toAsm: String

  def toTriplet: (String, Option[String], Option[String])

object Statement:
  val indent: String =
    "  "

case class UnaryInstruction(instruction: Instruction, comment: Option[String]) extends Statement:
  def toAsm: String =
    val left =
      instruction.toString

    comment match
      case Some(c) =>
        Statement.indent + f"$left%-16s ; " + c
      case None =>
        Statement.indent + left

  def toTriplet: (String, Option[String], Option[String]) =
    (instruction.toString, None, comment)

case class InstructionWithOperand[A](instruction: Instruction, operand: A, comment: Option[String])(using
    ev: Operand[A]
) extends Statement:
  def toAsm: String =
    val left =
      instruction.toString

    val operandStr =
      ev.toAddressLiteral(operand)

    comment match
      case Some(c) =>
        Statement.indent + f"$left%-5s $operandStr%-11s; " + c
      case None =>
        Statement.indent + f"$left%-5s $operandStr"

  def toTriplet: (String, Option[String], Option[String]) =
    (instruction.toString, ev.toAddressLiteral(operand).some, comment)

case class Label(s: String) extends Statement:
  def toAsm: String =
    s + ":"

  def toTriplet: (String, Option[String], Option[String]) =
    (s, None, None)

case class BranchingInstruction(instruction: Instruction, label: String) extends Statement:
  def toAsm: String =
    Statement.indent + instruction.toString + " " + label

  def toTriplet: (String, Option[String], Option[String]) =
    (instruction.toString, label.some, None)
