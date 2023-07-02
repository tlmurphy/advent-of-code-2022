package com.tlmurphy.adventZio.dayone

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartTwo extends ZIOAppDefault:

  def toIntChunks(chunks: Chunk[String]): Chunk[Int] =
    chunks.map(_.toInt)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    val stream = FileReader.getStream("day1.txt")
    for
      chunks <- stream.runCollect
      intChunks <- ZIO.succeed(chunks.map(toIntChunks))
      sums <- ZIO.succeed(intChunks.map(_.sum))
      topThree <- ZIO.succeed(sums.sorted(Ordering[Int].reverse).take(3))
      sumOfTopThree <- ZIO.succeed(topThree.sum)
      _ <- printLine(sumOfTopThree)
    yield ()
