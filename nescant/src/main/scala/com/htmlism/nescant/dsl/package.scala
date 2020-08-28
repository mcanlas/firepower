package com.htmlism.nescant

package object dsl {
  implicit class AddressOps(n: Int) {
    def z: ZeroPageAddress =
      ZeroPageAddress(n)

    def g: GlobalAddress =
      GlobalAddress(n)
  }
}
