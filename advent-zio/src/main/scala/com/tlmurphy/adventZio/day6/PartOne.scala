package com.tlmurphy.adventZio.day6

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader

object PartOne extends ZIOAppDefault:

  val window = 4

  def findMarker(s: Iterator[String], count: Int = window): Int =
    s.nextOption() match
      case None => count
      case Some(value) =>
        if (value.distinct == value) count else findMarker(s, count + 1)

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day6.txt")
      .map(_.sliding(window))
      .map(findMarker(_))
      .map(println(_))
      .runDrain
