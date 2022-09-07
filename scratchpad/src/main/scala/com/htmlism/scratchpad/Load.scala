package com.htmlism.scratchpad

trait Load[A]:
  // from constant
  def init: String

  // from register
  def from: String
