package com.htmlism.scratchpad

trait Store[A]:
  def instruction: String

object Store:
  def apply[A: Store]: Store[A] =
    summon[Store[A]]
