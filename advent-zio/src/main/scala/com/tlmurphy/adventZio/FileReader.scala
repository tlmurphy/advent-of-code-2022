package com.tlmurphy.adventZio

import zio.*
import zio.stream.*
import scala.io.Source

object FileReader:
  def getStream(fileName: String): ZStream[Any, Throwable, String] =
    ZStream
      .fromIteratorScoped(
        ZIO
          .fromAutoCloseable(
            ZIO.attempt(Source.fromFile(fileName))
          )
          .map(_.getLines())
      )
