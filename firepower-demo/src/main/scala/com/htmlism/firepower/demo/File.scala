package com.htmlism.firepower.demo

import java.nio.file.Path

case class File(path: Path)

object File:
  def apply(first: String, fragments: String*): File =
    File(Path.of(first, fragments: _*))
