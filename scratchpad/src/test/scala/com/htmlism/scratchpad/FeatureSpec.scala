package com.htmlism.scratchpad

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

import com.htmlism.scratchpad.syntax._

class FeatureSpec extends AnyFunSuite with Matchers:
  test("zero page address as write only supports writing") {
    WriteOnlyAddress(Address.zero(0x01))
      .write(2)[A] shouldBe "LDA 2 STA 1"
  }

  test("zero page address as read/write supports writing") {
    ReadWriteAddress(Address.zero(0x01))
      .write(2)[A] shouldBe "LDA 2 STA 1"
  }

  test("writing to an address can use A, X, and Y registers for bouncing ") {
    ReadWriteAddress(Address.zero(0x01))
      .write(2)[A] shouldBe "LDA 2 STA 1"

    ReadWriteAddress(Address.zero(0x01))
      .write(2)[X] shouldBe "LDX 2 STX 1"

    ReadWriteAddress(Address.zero(0x01))
      .write(2)[Y] shouldBe "LDY 2 STY 1"
  }
