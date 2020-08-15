package com.htmlism.mos6502.dsl

import scala.collection.mutable.ListBuffer
import cats.implicits._
import com.htmlism._

object DslDemo extends App {
  val cpu =
    new CPU

  import registers.{A, X}

  // address demonstration
  withAssemblyContext { implicit ctx =>
    val payloadLocation =
      0x01.z

    cpu.A = 0x40

    A.add(payloadLocation)
  }

  // a becomes others
  withAssemblyContext { implicit ctx =>
    cpu.A = cpu.X
    cpu.A = cpu.Y
  }

  // demonstrate first example
  withAssemblyContext { implicit ctx =>
    cpu.A = 0xC0

    cpu.X = cpu.A

    X.incr

    A.add(0xc4)
  }

  // first color example
  withAssemblyContext { implicit ctx =>
    val scr =
      0x0200.indexed

    scr(0).write[Color](Color.White)
    scr(1).write[Color](Color.Green)
    scr(2).write[Color](Color.Orange)
  }

  def withAssemblyContext(f: AssemblyContext => Unit): Unit = {
    val ctx: AssemblyContext =
      new AssemblyContext

    f(ctx)

    ctx.printOut()
    println()
    println()
  }

  implicit class AddressOps(n: Int) {
    def z: ZeroAddress =
      ZeroAddress(n)

    def addr: GlobalAddress =
      GlobalAddress(n)

    def indexed: IndexedAddressCollection =
      IndexedAddressCollection(n)
  }
}


object registers {
  sealed trait Register

  sealed trait DestinationA

  case object A extends Register {
    def add[A](x: A)(implicit ctx: AssemblyContext, ev: Operand[A]): Unit =
      ev.operandType match {
        case ValueLiteral =>
          ctx.push(ADC, x, s"add LITERAL to a")

        case MemoryLocation =>
          ctx.push(ADC, x, s"add ADDR to a")
      }
  }

  case object X extends Register with DestinationA {
    def incr(implicit ctx: AssemblyContext): Unit =
      ctx.push(INX, "incr x")
  }

  case object Y extends Register with DestinationA
}

class CPU {
  def A: registers.A.type =
    registers.A

  def A_=[A](x: A)(implicit ctx: AssemblyContext, ev: Operand[A]): Unit =
    ctx.push(LDA, x, "set A to value " + ev.toAddressLiteral(x))

  def A_=(reg: registers.DestinationA)(implicit ctx: AssemblyContext): Unit =
    reg match {
      case registers.X =>
        ctx.push(TXA)
      case registers.Y =>
        ctx.push(TYA)
    }

  def X: registers.X.type =
    registers.X

  def X_=(reg: registers.A.type)(implicit ctx: AssemblyContext): Unit =
    ctx.push(TAX, s"set x to register $reg")

  def Y: registers.Y.type =
    registers.Y

  def Y_=(reg: registers.A.type)(implicit ctx: AssemblyContext): Unit =
    ctx.push(TAY, s"set x to register $reg")
}

class AssemblyContext {
  val xs: ListBuffer[Statement] =
    ListBuffer()

  def push(instruction: Instruction): Unit =
    xs.append(UnaryInstruction(instruction, None))

  def push(instruction: Instruction, s: String): Unit =
    xs.append(UnaryInstruction(instruction, s.some))

  def push[A : Operand](instruction: Instruction, x: A): Unit =
    xs.append(InstructionWithOperand(instruction, x, None))

  def push[A : Operand](instruction: Instruction, x: A, s: String): Unit =
    xs.append(InstructionWithOperand(instruction, x: A, s.some))

  def printOut(): Unit = {
    xs
      .map(_.toAsm)
      .foreach(println)
  }
}
