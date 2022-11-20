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

  class PlayerOne extends Player[PlayerOne](40)

  object PlayerOne extends PlayerOne:
    given WriteLease[PlayerOne] with
      def canon: PlayerOne =
        PlayerOne

  class PlayerTwo extends Player[PlayerTwo](80)

  object PlayerTwo extends PlayerTwo:
    given WriteLease[PlayerTwo] with
      def canon: PlayerTwo =
        PlayerTwo

  test("use write lease") {
    // TODO actually this doesn't make sense. the lease shouldn't be canonical/globally available. only when lease is given...
    expect.eql(List("LDA", "STA"), PlayerOne.setHead(0).xs)
  }
