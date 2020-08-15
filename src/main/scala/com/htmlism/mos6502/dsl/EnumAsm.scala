package com.htmlism.mos6502.dsl

import cats.data.NonEmptyList

trait EnumAsm[A] {
  def comment: String

  def labels: NonEmptyList[String]
}
