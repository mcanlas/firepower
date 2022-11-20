package com.htmlism.scratchpad

import weaver.*
object ComplicatedResourceSuite extends FunSuite:
  class Player[A <: Player[_]](address: Int):
    def headAddr: Int =
      address

    def tailAddr: Int =
      address + 1

    def setHead(x: Int)(using W: WriteLease[A]): Asm2[Register.A, A] =
      Move.constA(x, W.to(_.headAddr))

  class FastLease[A](x: A) extends WriteLease[A]:
    def canon: A =
      x

  class PlayerOne extends Player[PlayerOne](40)

  object PlayerOne extends PlayerOne:
    val lease: WriteLease[PlayerOne] =
      FastLease(PlayerOne)

  class PlayerTwo extends Player[PlayerTwo](80)

  object PlayerTwo extends PlayerTwo:
    val lease: WriteLease[PlayerTwo] =
      FastLease(PlayerTwo)

  test("use write lease") {
    expect.eql(List("LDA", "STA"), PlayerOne.setHead(0)(using PlayerOne.lease).xs)
  }
