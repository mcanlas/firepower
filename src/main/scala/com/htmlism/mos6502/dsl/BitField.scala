package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

trait BitField[A] {
  def comment: String

  def labels: NonEmptyList[String]

  /**
   * ASM-safe label
   */
  def label(x: A): String

  /**
   * Comment string
   */
  def comment(x: A): String
}
