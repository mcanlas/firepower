package com.htmlism.scratchpad

import weaver.*

object RegisterSuite extends FunSuite:
  private def reg[A](implicit ev: Register[A]) =
    ev

  test("the accumulator is a register") {
    expect.eql("A", reg[A].self)
  }

  test("X is a register") {
    expect.eql("X", reg[X].self)
  }

  test("Y is a register") {
    expect.eql("Y", reg[Y].self)
  }
