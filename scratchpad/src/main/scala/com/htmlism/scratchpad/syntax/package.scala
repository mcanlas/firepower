package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps(reg: WriteAddress):
    def write[A: Loadable](x: A): PartiallyAppliedWrite[A] =
      new PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable](reg: WriteAddress, x: A):
    def apply[B: Register]: String =
      val literal =
        summon[Loadable[A]].show(x)

      val register =
        summon[Register[B]].self

      val first =
        s"LD$register $literal"

      val second =
        s"ST$register ${reg.addr.n.toString}"

      val desc =
        s"${reg.addr.alias} = $literal, via $register"

      s"$first $second ; $desc"
