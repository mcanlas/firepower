package com.htmlism.scratchpad

import weaver.*
object ComplicatedResourceSuite extends FunSuite:
  class Player[A <: Player[_]](address: Int):
    def headAddr: Int =
      address

    def tailAddres: Int =
      address + 1

    def setHead(x: Int)(using W: WriteLease[A]): Asm1[Register.A] =
      Load.Const[Register.A, Int](x)

  class PlayerOne extends Player[PlayerOne](40)

  class PlayerTwo extends Player[PlayerTwo](80)
