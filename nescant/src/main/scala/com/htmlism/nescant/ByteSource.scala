package com.htmlism.nescant

trait ByteSource[A]

object ByteSource:
  implicit val sourceForInt: ByteSource[Int] =
    new ByteSource[Int] {}
