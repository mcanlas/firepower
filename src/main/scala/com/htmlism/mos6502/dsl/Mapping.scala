package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

/**
 * Like an enum, but values are specified
 */
trait Mapping[A] {
  def comment: String

  /**
    * An ordered list of every value in this enumeration
    */
  def all: NonEmptyList[A]

  def value(x: A): Int

  /**
    * ASM-safe label
    */
  def label(x: A): String

  /**
    * Comment string
    */
  def comment(x: A): String
}
