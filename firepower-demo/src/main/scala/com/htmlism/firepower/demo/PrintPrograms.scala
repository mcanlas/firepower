package com.htmlism.firepower.demo

import zio.*

import java.io.IOException

object PrintPrograms extends ZIOAppDefault:
  def run: ZIO[Any, Throwable, Unit] =
    File("data/example.txt")
      .contents
      .map(println)
