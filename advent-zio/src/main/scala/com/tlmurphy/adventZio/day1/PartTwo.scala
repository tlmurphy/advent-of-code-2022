package com.tlmurphy.adventZio.day1

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartTwo extends ZIOAppDefault:

  private def toIntChunks(chunks: Chunk[String]): Chunk[Int] =
    chunks.map(_.toInt)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day1.txt")
      .split(_ == "")
      .map(toIntChunks)
      .map(_.sum)
      .runCollect
      .map(_.sorted(Ordering[Int].reverse).take(3))
      .map(_.sum)
      .flatMap(printLine(_))
