package com.tlmurphy.adventZio.dayone

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartOne extends ZIOAppDefault:

  private def toIntChunks(chunks: Chunk[String]): Chunk[Int] =
    chunks.map(_.toInt)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day1.txt")
      .split(_ == "")
      .map(toIntChunks)
      .map(_.sum)
      .runCollect
      .flatMap(c => printLine(c.max))
