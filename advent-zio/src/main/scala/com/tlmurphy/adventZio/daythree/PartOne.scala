package com.tlmurphy.adventZio.daythree

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader
import zio.stream.ZStream
import scala.io.Source

object PartOne extends ZIOAppDefault:

  private case class Sack(contents: String):
    val compartments = contents.splitAt(contents.length() / 2)
    val priorites: Map[Char, Int] =
      ((('a' to 'z') ++ ('A' to 'Z')) zip (1 to 52)).toMap
    def commonItem: Char =
      (compartments._1 intersect compartments._2).head
    def prioVal(c: Char): Int = priorites(c)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    val stream = FileReader.getStream("day3.txt")
    for
      chunks <- stream.runCollect
      sacks <- ZIO.succeed(chunks.map(Sack(_)))
      prioVals <- ZIO.succeed(sacks.map(s => s.prioVal(s.commonItem)))
      _ <- printLine(prioVals.sum)
    yield ()
