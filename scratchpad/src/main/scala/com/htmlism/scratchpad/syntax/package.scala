package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps(reg: WriteAddress):
    def write[A: Loadable](x: A): PartiallyAppliedWrite[A] =
      new PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable](reg: WriteAddress, x: A):
    def apply[B: Register]: String =
      val first =
        "LD" + summon[Register[B]].self + " " + summon[Loadable[A]].show(x)

      val second =
        "ST" + summon[Register[B]].self + " " + reg.addr.n.toString

      first + " " + second
