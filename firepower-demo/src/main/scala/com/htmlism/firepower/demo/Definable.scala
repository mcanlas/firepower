package com.htmlism.firepower.demo

import scala.collection.immutable._

trait Definable[A]:
  def table(x: A): ListMap[String, Int]

  extension (x: A) def toComment: String

  extension (x: A) def toValue: Int

  extension (x: A) def toDefine: String

object Definable:
  def apply[A](using ev: Definable[A]): Definable[A] =
    ev
