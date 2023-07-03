package com.tlmurphy.adventZio.daythree

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader
import zio.stream.ZStream

object PartTwo extends ZIOAppDefault:

  private case class SackGroup(sacks: Chunk[String]):
    val priorites: Map[Char, Int] =
      ((('a' to 'z') ++ ('A' to 'Z')) zip (1 to 52)).toMap
    def commonItem: Char =
      (sacks.reduce(_ intersect _)).head
    def prioVal(c: Char): Int = priorites(c)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day3.txt")
      .grouped(3)
      .map(SackGroup(_))
      .map(s => s.prioVal(s.commonItem))
      .runSum
      .flatMap(printLine(_))
