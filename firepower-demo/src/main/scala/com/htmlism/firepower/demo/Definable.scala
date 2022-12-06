package com.htmlism.firepower.demo

import scala.collection.immutable._

trait Definable[A]:
  def table: ListMap[String, String]

  extension (x: A) def toComment: String

  extension (x: A) def toValue: Int

  extension (x: A) def toDefine: String
