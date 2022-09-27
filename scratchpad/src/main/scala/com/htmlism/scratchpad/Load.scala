package com.htmlism.scratchpad

trait Load[A]:
  // TODO genericize to take in encoder
  // from constant
  def loadInt: String

  // from register
  def from: String

object Load:
  def apply[A: Load]: Load[A] =
    summon[Load[A]]
