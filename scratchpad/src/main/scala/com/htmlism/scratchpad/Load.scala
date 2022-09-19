package com.htmlism.scratchpad

trait Load[A]:
  // from constant
  def init: String

  // from register
  def from: String

object Load:
  def apply[A: Load]: Load[A] =
    summon[Load[A]]
