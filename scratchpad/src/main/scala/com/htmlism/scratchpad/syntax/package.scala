package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps[Addr](reg: WriteByteAddress[Addr]):
    def writeConst[A: Loadable](x: A): syntax.PartiallyAppliedWrite[A, Addr] =
      new syntax.PartiallyAppliedWrite(reg, x)

    def writeFrom[R: Register]: Asm2[R, Addr] =
      StoreTo[R, Addr]()

  class PartiallyAppliedWrite[Addr: Loadable, A](reg: WriteByteAddress[A], x: Addr):
    def apply[R: Register]: String =
      val literal =
        summon[Loadable[Addr]].show(x)

      val register =
        summon[Register[R]].name

      val loadInstruction =
        "LD" + register // TODO load action needs to interact with encoder

      val storeInstruction =
        "ST" + register // TODO store action needs to interact with encoder

      val first =
        s"$loadInstruction $literal"

      val second =
        s"$storeInstruction ${reg.n.toString}"

      val desc =
        s"${reg.alias} = $literal, via $register"

      s"$first $second ; $desc"
