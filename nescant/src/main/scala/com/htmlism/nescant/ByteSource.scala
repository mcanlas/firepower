package com.htmlism.nescant

trait ByteSource[A]

object ByteSource:
  given sourceForInt: ByteSource[Int] =
    new ByteSource[Int] {}
