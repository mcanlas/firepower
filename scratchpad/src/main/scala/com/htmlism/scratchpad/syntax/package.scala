package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps[A](reg: WriteAddress[A]):
    def write[B: Loadable](x: B): syntax.PartiallyAppliedWrite[B, A] =
      new syntax.PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable, B](reg: WriteAddress[B], x: A):
    def apply[C: Load: Store: Register]: String =
      val literal =
        summon[Loadable[A]].show(x)

      val register =
        summon[Register[C]].self

      val loadInstruction =
        Load[C].instruction // TODO load action needs to interact with encoder

      val storeInstruction =
        Store[C].to // TODO store action needs to interact with encoder

      val first =
        s"$loadInstruction $literal"

      val second =
        s"$storeInstruction ${reg.n.toString}"

      val desc =
        s"${reg.alias} = $literal, via $register"

      s"$first $second ; $desc"
