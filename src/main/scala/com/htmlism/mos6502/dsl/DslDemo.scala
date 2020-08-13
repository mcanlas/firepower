package com.htmlism.mos6502.dsl

import scala.collection.mutable.ListBuffer

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
  }
}


object registers {
  sealed trait Register

  sealed trait DestinationA

  case object A extends Register {
    def add(n: Int)(implicit ctx: AssemblyContext): Unit = {
      ctx.describe(s"add $n to a")
    }

    def add(n: ZeroAddress)(implicit ctx: AssemblyContext): Unit = {
      ctx.describe(s"add to A value from zero page $n")
    }
  }

  case object X extends Register with DestinationA {
    def incr(implicit ctx: AssemblyContext): Unit = {
      ctx.describe("incr x")
    }
  }

  case object Y extends Register with DestinationA
}

class CPU {
  def A: registers.A.type =
    registers.A

  def A_=(n: Int)(implicit ctx: AssemblyContext): Unit = {
    ctx.describe(s"set a to value $n")
  }

  def A_=(reg: registers.DestinationA)(implicit ctx: AssemblyContext): Unit =
    ctx.describe(s"set a to register $reg")

  def X: registers.X.type =
    registers.X

  def X_=(reg: registers.A.type)(implicit ctx: AssemblyContext): Unit =
    ctx.describe(s"set x to register $reg")

  def Y: registers.Y.type =
    registers.Y

  def Y_=(reg: registers.A.type)(implicit ctx: AssemblyContext): Unit =
    ctx.describe(s"set y to register $reg")
}

class AssemblyContext {
  val xs: ListBuffer[String] =
    ListBuffer()

  def describe(s: String): Unit =
    xs.append(s)

  def printOut(): Unit =
    xs.foreach(println)
}