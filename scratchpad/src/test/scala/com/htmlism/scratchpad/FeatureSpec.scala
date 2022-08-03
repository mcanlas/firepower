package com.htmlism.scratchpad

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

import com.htmlism.scratchpad.syntax._

class FeatureSpec extends AnyFunSuite with Matchers:
  test("zero page address as write only supports writing") {
    WriteOnlyAddress(ZeroPageAddress(0x01, "example"))
      .write(2)[A] shouldBe "LDA 2 STA 1 ; example = 2, via A"
  }

  test("zero page address as read/write supports writing") {
    ReadWriteAddress(ZeroPageAddress(0x01, "example"))
      .write(2)[A] shouldBe "LDA 2 STA 1 ; example = 2, via A"
  }

  test("writing to an address can use A, X, and Y registers for bouncing") {
    ReadWriteAddress(ZeroPageAddress(0x01, "example"))
      .write(2)[A] shouldBe "LDA 2 STA 1 ; example = 2, via A"

    ReadWriteAddress(ZeroPageAddress(0x01, "example"))
      .write(2)[X] shouldBe "LDX 2 STX 1 ; example = 2, via X"

    ReadWriteAddress(ZeroPageAddress(0x01, "example"))
      .write(2)[Y] shouldBe "LDY 2 STY 1 ; example = 2, via Y"
  }

  ignore("the write payload is a typesafe enum") {}

  // intializations must be constant
  // cannot involve reading from a register, that is a side-effect
  // can also support 16-bit initializations
  ignore("initializations of the same value and register are aggregated") {}
