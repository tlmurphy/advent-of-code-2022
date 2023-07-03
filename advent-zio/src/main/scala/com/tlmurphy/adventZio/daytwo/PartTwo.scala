package com.tlmurphy.adventZio.daytwo

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartTwo extends ZIOAppDefault:

  private case class Round(opponent: Char, me: Char):
    private val loss: Int = opponent match
      case 'A' => 3
      case 'B' => 1
      case 'C' => 2

    private val tie: Int = opponent match
      case 'A' => 1
      case 'B' => 2
      case 'C' => 3

    private val win: Int = opponent match
      case 'A' => 2
      case 'B' => 3
      case 'C' => 1

    def score: Int = me match
      case 'X' => loss
      case 'Y' => 3 + tie
      case 'Z' => 6 + win

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day2.txt")
      .map(s => Round(s.head, s.last).score)
      .runSum
      .flatMap(printLine(_))
