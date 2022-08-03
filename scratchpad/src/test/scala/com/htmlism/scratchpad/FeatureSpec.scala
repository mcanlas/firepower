package com.htmlism.scratchpad

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

import com.htmlism.scratchpad.syntax._

class FeatureSpec extends AnyFunSuite with Matchers:
  test("zero page address as write only supports writing") {
    WriteOnlyAddress(Address.zero(0x01))
      .write(2)[Register.A] shouldBe "12A"
  }

  test("zero page address as read/write supports writing") {
    ReadWriteAddress(Address.zero(0x01))
      .write(2)[Register.A] shouldBe "12A"
  }

  test("writing to an address can use A, X, and Y registers for bouncing ") {
    ReadWriteAddress(Address.zero(0x01))
      .write(2)[Register.A] shouldBe "12A"

    ReadWriteAddress(Address.zero(0x01))
      .write(2)[Register.X] shouldBe "12X"

    ReadWriteAddress(Address.zero(0x01))
      .write(2)[Register.Y] shouldBe "12Y"
  }
