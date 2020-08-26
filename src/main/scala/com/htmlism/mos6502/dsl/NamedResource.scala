package com.htmlism.mos6502.dsl

trait NamedResource[A] {

  /**
    * A `Definable` can emit multiple definitions. Usually in the case of `word`s being split across two byte-definitions
    */
  def toDefinitions(x: A): List[Definition[_]]
}
