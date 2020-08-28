package com.htmlism.nescant

trait Source[A]

object Source {
  implicit val sourceForInt: Source[Int] =
    new Source[Int] {}
}
