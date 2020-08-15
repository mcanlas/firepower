package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

trait EnumAsm[A] {
  def comment: String

  /**
    * An ordered list of every value in this enumeration
    */
  def all: NonEmptyList[A]

  /**
    * ASM-safe label
    */
  def label(x: A): String

  /**
    * Comment string
    */
  def comment(x: A): String
}
