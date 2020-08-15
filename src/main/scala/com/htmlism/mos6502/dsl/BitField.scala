package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

trait BitField[A] {
  def comment: String

  def labels: NonEmptyList[String]
}
