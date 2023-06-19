package com.htmlism.firepower.core

import weaver.*

object RegisterSuite extends FunSuite:
  private def reg[A](using ev: Register[A]) =
    ev

  test("the accumulator is a register"):
    expect.eql("A", reg[Reg.A].name)

  test("X is a register"):
    expect.eql("X", reg[Reg.X].name)

  test("Y is a register"):
    expect.eql("Y", reg[Reg.Y].name)
