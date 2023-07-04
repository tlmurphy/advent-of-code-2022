package com.tlmurphy.adventZio.day4

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader
import scala.collection.immutable.Range.Inclusive

object PartOne extends ZIOAppDefault:

  private case class SectionAssignment(first: Inclusive, second: Inclusive):
    def isOverlapping: Boolean =
      val firstOverlapsSecond =
        (first contains second.start) && (first contains second.end)
      val secondOverlapsFirst =
        (second contains first.start) && (second contains first.end)
      firstOverlapsSecond || secondOverlapsFirst

  private def toInclusive(s: String): Inclusive =
    val split = s.split("-")
    (split.head.toInt to split.last.toInt)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day4.txt")
      .map(s => s.split(","))
      .map(s => SectionAssignment(toInclusive(s.head), toInclusive(s.last)))
      .filter(_.isOverlapping)
      .runCount
      .flatMap(printLine(_))
