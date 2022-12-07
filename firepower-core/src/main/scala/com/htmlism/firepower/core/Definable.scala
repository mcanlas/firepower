package com.htmlism.firepower.core

trait Definable[A]:
  def table(x: A): Definable.Table

  extension (x: A) def toComment: String

  extension (x: A) def toValue: Int

  extension (x: A) def toDefine: String

  extension (x: A) def toDefineWithMath: String

object Definable:
  case class Table(description: String, xs: List[(String, Int)])

  def apply[A](using ev: Definable[A]): Definable[A] =
    ev
