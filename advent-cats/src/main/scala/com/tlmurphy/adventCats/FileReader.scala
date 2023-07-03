package com.tlmurphy.adventCats

import cats.effect.IO
import fs2.text
import fs2.{Stream, Chunk}
import fs2.io.file.{Files, Path}

object FileReader:
  def getStream(
      fileName: String
  ): Stream[cats.effect.IO, String] =
    Files[IO]
      .readAll(Path(fileName))
      .through(text.utf8Decode)
      .through(text.lines)
