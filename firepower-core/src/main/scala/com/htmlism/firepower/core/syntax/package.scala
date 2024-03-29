package com.htmlism.firepower.core

package object syntax:
  implicit class WriteRegisterOps[Addr](reg: WriteByteAddress[Addr]):
    def writeConst[A: Loadable](x: A): syntax.PartiallyAppliedWrite[A, Addr] =
      new syntax.PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[Addr: Loadable, A](reg: WriteByteAddress[A], x: Addr):
    def apply[R: Register]: String =
      val literal =
        summon[Loadable[Addr]].show(x)

      val register =
        summon[Register[R]]

      val loadInstruction =
        register.load // TODO load action needs to interact with encoder

      val storeInstruction =
        register.store // TODO store action needs to interact with encoder

      val first =
        s"$loadInstruction $literal"

      val second =
        s"$storeInstruction ${reg.n.toString}"

      val desc =
        s"${reg.alias} = $literal, via ${register.name}"

      s"$first $second ; $desc"
