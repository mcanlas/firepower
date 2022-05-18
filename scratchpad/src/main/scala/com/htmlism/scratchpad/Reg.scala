package com.htmlism.scratchpad

trait Reg[A]:
  def hello: Boolean

given tuple2reg[A, B](using a: Reg[A], b: Reg[B]): Reg[(A, B)] with
  def hello: Boolean =
    true
