package com.tlmurphy.adventZio.daythree

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader
import zio.stream.ZStream
import scala.io.Source

object PartOne extends ZIOAppDefault:

  def getStream(fileName: String): ZStream[Any, Throwable, String] =
    ZStream
      .fromIteratorScoped(
        ZIO
          .fromAutoCloseable(
            ZIO.attempt(Source.fromFile(fileName))
          )
          .map(_.getLines())
      )

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    getStream("example.txt").map(x => println(x)).runCollect
