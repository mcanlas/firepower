package com.htmlism.nescant
package dsl

import org.scalatest.flatspec._
import org.scalatest.matchers._

class SourceSpec extends AnyFlatSpec with should.Matchers {
  "A number" should "be a source" in {
    123.z.write(456)
  }

  "A zero page address" should "be a source" in {
    123.z.write(456.z)
  }

  "A global address" should "be a source" in {
    123.z.write(456.g)
  }

  "A volatile device" should "be a source" in {
    val src = VolatileDevice[Int]("", 0.z)

    123.z.write(src)
  }

  "A read write location" should "be a source" in {
    val src = ReadWriteLocation[Int]("", 0.z)

    123.z.write(src)
  }
}
