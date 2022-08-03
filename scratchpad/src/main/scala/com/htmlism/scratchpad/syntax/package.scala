package com.htmlism.scratchpad

package object syntax:
  implicit class WriteRegisterOps(reg: WriteAddress):
    def write[A: Loadable](x: A): PartiallyAppliedWrite[A] =
      new PartiallyAppliedWrite(reg, x)

  class PartiallyAppliedWrite[A: Loadable](reg: WriteAddress, x: A):
    def apply[B: Register]: String =
      reg.addr.n.toString + summon[Loadable[A]].show(x) + summon[Register[B]].self
