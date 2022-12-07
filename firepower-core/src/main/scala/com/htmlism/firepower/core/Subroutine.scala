package com.htmlism.firepower.core

import cats.syntax.all.*

import com.htmlism.firepower.core.AsmBlock._

case class Subroutine(name: String, description: String, intents: () => List[MetaIntent]):
  def call: MetaIntent =
    MetaIntent.Jump(name, description, intents())

object Subroutine:
  def apply(name: String, description: String): Subroutine =
    Subroutine(name, description, () => Nil)
