package com.htmlism.firepower.core

trait Loadable[A]:
  def show(x: A): String

object Loadable:
  given Loadable[Int] with
    def show(x: Int): String =
      x.toString
