package com.htmlism.firepower.core

import cats.syntax.all.*

import com.htmlism.firepower.core.AsmBlock._

case class Subroutine(name: String, description: String, intents: List[MetaIntent.Jump]):
  def call: MetaIntent.Jump =
    MetaIntent.Jump(name, description, () => intents)

  def attach: NamedCodeBlock =
    NamedCodeBlock(name, "this is a named block".some, intents.map(_ => Intent("TODO".some, Nil)))

object Subroutine:
  def apply(name: String, description: String): Subroutine =
    Subroutine(name, description, Nil)
