package com.htmlism.firepower

import java.nio.charset.Charset
import java.nio.file.Files

import zio.*

package object demo:
  implicit class FileOps(f: demo.File):
    def contents: Task[String] =
      ZIO.attempt {
        Files.readAllBytes(f.path)
      }.map(xs => new String(xs, Charset.defaultCharset()))
