package com.htmlism.firepower.core

trait Reg[A]:
  def hello: Boolean

given tuple2reg[A, B](using a: Reg[A], b: Reg[B]): Reg[(A, B)] with
  val _ = a
  val _ = b

  def hello: Boolean =
    true
