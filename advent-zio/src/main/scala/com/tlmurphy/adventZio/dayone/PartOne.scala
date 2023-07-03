package com.tlmurphy.adventZio.dayone

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartOne extends ZIOAppDefault:

  private def toIntChunks(chunks: Chunk[String]): Chunk[Int] =
    chunks.map(_.toInt)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    val stream = FileReader.getStream("day1.txt").split(_ == "")
    for
      chunks <- stream.runCollect
      intChunks <- ZIO.succeed(chunks.map(toIntChunks))
      sums <- ZIO.succeed(intChunks.map(_.sum))
      max <- ZIO.succeed(sums.max)
      _ <- printLine(max)
    yield ()
