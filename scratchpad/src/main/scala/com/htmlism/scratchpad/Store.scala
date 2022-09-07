package com.htmlism.scratchpad

trait Store[A]:
  def to: String

object Store:
  def apply[A: Store]: Store[A] =
    summon[Store[A]]
