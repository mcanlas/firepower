package com.htmlism.usingtypes

// cannot reconcile adding `A : Reg` when needing `Reg[A & B]`
class AsmProgram[+A]:
  def join[B](that: AsmProgram[B]): AsmProgram[A & B] =
    new AsmProgram

  def use: Int =
    4
