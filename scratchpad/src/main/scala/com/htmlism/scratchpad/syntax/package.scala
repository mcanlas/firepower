package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps[B <: Address](reg: WriteAddress[B]):
    def write[A: Loadable](x: A): PartiallyAppliedWrite[A, B] =
      new PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable, B <: Address](reg: WriteAddress[B], x: A):
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
