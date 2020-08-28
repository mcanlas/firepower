package com.htmlism.nescant
package dsl

import org.scalatest.flatspec._
import org.scalatest.matchers._

class ByteSinkSpec extends AnyFlatSpec with should.Matchers {
  "A zero page address" should "be a byte-wide sync" in {
    123.z.write(456)
  }

  "A global address" should "be a byte-wide sync" in {
    123.g.write(456)
  }

  "A read write location" should "be a byte-wide sync" in {
    val sink = ReadWriteLocation[Int]("", 0.z)

    sink.write(456)
  }
}
