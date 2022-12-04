package com.htmlism.firepower.core

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

  object PlayerOne extends PlayerOne with GrantsWriteLeases[PlayerOne]:
    given Companion[PlayerOne] with
      def canon: PlayerOne =
        PlayerOne

  class PlayerTwo extends Player[PlayerTwo](80)

  object PlayerTwo extends PlayerTwo with GrantsWriteLeases[PlayerTwo]:
    given Companion[PlayerTwo] with
      def canon: PlayerTwo =
        PlayerTwo

  test("use write lease") {
    val asm =
      PlayerOne
        .withWriteLease { implicit w =>
          PlayerOne.setHead(23).xs
        }

    expect.eql(List("LDA 23", "STA 40"), asm)
  }
