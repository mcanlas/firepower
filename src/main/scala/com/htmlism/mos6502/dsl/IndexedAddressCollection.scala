package com.htmlism.mos6502.dsl

case class IndexedAddressCollection(baseAddress: Int) {
  def apply(n: Int): GlobalAddress =
    GlobalAddress(baseAddress + n)
}
