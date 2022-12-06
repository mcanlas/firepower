package com.htmlism.firepower.demo

trait Definable[A]:
  extension (x: A) def toComment: String

  extension (x: A) def toValue: Int
