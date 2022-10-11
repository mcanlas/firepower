package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps[Addr](reg: WriteAddress[Addr]):
    def writeConst[A: Loadable](x: A): syntax.PartiallyAppliedWrite[A, Addr] =
      new syntax.PartiallyAppliedWrite(reg, x)

    def writeFrom[R: Store]: Asm2[R, Addr] =
      val storeInstruction =
        Store[R].to

      val storeStr =
        s"$storeInstruction ${reg.n.toString}"

      // TODO encoding now already makes the structures lose semantic meaning
      // TODO maybe AsmN's should be traits such that semantic structures can just obey the contracts and be AND'ed
      Asm2(List(s"$storeInstruction ${reg.n.toString}"))

  class PartiallyAppliedWrite[Addr: Loadable, A](reg: WriteAddress[A], x: Addr):
    def apply[R: Load: Store: Register]: String =
      val literal =
        summon[Loadable[Addr]].show(x)

      val register =
        summon[Register[R]].self

      val loadInstruction =
        Load[R].instruction // TODO load action needs to interact with encoder

      val storeInstruction =
        Store[R].to // TODO store action needs to interact with encoder

      val first =
        s"$loadInstruction $literal"

      val second =
        s"$storeInstruction ${reg.n.toString}"

      val desc =
        s"${reg.alias} = $literal, via $register"

      s"$first $second ; $desc"
