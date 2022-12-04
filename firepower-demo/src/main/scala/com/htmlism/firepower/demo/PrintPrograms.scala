package com.htmlism.firepower.demo

import zio.*

import java.io.IOException

import com.htmlism.rufio.withzio._

object PrintPrograms extends ZIOAppDefault:
  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- File("data/out.txt")
        .writeLine("out")

      _ <- File("data/example.txt")
        .contents
        .map(Console.printLine(_))
    } yield ()
