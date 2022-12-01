import zio.*

import java.io.IOException

object PrintPrograms extends ZIOAppDefault:
  def run: IO[IOException, Unit] =
    Console
      .printLine("Hello, World!")
