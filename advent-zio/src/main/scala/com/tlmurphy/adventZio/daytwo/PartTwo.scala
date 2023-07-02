package com.tlmurphy.adventZio.daytwo

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

case class RoundTwo(opponent: Char, me: Char):
  def score: Int = me match
    case 'X' =>
      opponent match
        case 'A' => 3
        case 'B' => 1
        case 'C' => 2
    case 'Y' =>
      opponent match
        case 'A' => 3 + 1
        case 'B' => 3 + 2
        case 'C' => 3 + 3
    case 'Z' =>
      opponent match
        case 'A' => 6 + 2
        case 'B' => 6 + 3
        case 'C' => 6 + 1

object PartTwo extends ZIOAppDefault:
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    val stream = FileReader.getStream("day2.txt")
    for
      chunks <- stream.runCollect
      scores <- ZIO.succeed(
        chunks.flatten.map(x => RoundTwo(x.head, x.last).score)
      )
      _ <- printLine(scores.sum)
    yield ()
