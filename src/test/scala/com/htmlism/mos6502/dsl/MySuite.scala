package com.htmlism.mos6502.dsl

import weaver.SimpleIOSuite
import cats.effect._

// Suites must be "objects" for them to be picked by the framework
object MySuite extends SimpleIOSuite {

  // A test for non-effectful (pure) functions
  pureTest("hello pure") {
    expect("hello".size == 6)
  }

  val random = IO(java.util.UUID.randomUUID())

  // A test for side-effecting functions
  simpleTest("hello side-effects") {
    for {
      x <- random
      y <- random
    } yield expect(x != y)
  }

  // A test with logs
  loggedTest("hello logs") { log =>
    for {
      x <- random
      _ <- log.info(s"x : $x")
      y <- random
      _ <- log.info(s"y : $y")
    } yield expect(x != y)
  }

}
