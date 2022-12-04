package com.htmlism.firepower.demo

import zio.*

import java.io.IOException

import com.htmlism.rufio.withzio._

object PrintPrograms extends ZIOAppDefault:
  private def writeLine(file: String)(s: String) =
    File(s"data/$file.txt")
      .writeLine(s)

  private val programs =
    List[(String, String)](
      "one-line.txt"  -> "one line",
      "two-lines.txt" -> (Line.mkString _)
        .apply(List("foo", "bar").map(Line(_)))
    )

  def run: Task[Unit] =
    for {
      // just a traverse in slow motion...
      _ <- programs
             .map { case (f, s) => File(s"data/$f").writeLine(s) }
             .foldLeft[Task[Unit]](ZIO.unit)((acc, z) => acc *> z)
    } yield ()
