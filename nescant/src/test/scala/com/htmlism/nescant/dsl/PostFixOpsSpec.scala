package com.htmlism.nescant
package dsl

import org.scalatest.flatspec.*
import org.scalatest.matchers.*

class PostFixOpsSpec extends AnyFlatSpec with should.Matchers:
  "Numbers" should "support zero page ops" in:
    123.z shouldBe ZeroPageAddress(123)

  it should "support global address ops" in:
    123.g shouldBe GlobalAddress(123)
