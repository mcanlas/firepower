package com.htmlism.nescant
package dsl

import org.scalatest.flatspec._
import org.scalatest.matchers._

class ByteSourceSpec extends AnyFlatSpec with should.Matchers:
  private val sink =
    123.z

  "A number" should "be a byte-wide source" in:
    sink.write(456)

  "A zero page address" should "be a byte-wide source" in:
    sink.write(456.z)

  "A global address" should "be a byte-wide source" in:
    sink.write(456.g)

  "A volatile device" should "be a byte-wide source" in:
    val src = VolatileDevice[Int]("", 0.z)

    sink.write(src)

  "A read write location" should "be a byte-wide source" in:
    val src = ReadWriteLocation[Int]("", 0.z)

    sink.write(src)
