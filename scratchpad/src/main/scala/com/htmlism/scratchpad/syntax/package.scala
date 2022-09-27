package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps[B <: Address](reg: WriteAddress[B]):
    def write[A: Loadable](x: A): PartiallyAppliedWrite[A, B] =
      new PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable, B <: Address](reg: WriteAddress[B], x: A):
    def apply[C: Load: Store: Register]: String =
      val literal =
        summon[Loadable[A]].show(x)

      val register =
        summon[Register[C]].self

      val loadInstruction =
        Load[C].loadInt // TODO load action needs to interact with encoder

      val storeInstruction =
        Store[C].to // TODO store action needs to interact with encoder

      val first =
        s"$loadInstruction $literal"

      val second =
        s"$storeInstruction ${reg.addr.n.toString}"

      val desc =
        s"${reg.addr.alias} = $literal, via $register"

      s"$first $second ; $desc"
