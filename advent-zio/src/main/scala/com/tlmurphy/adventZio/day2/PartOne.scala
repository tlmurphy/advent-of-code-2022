package com.tlmurphy.adventZio.day2

import zio.*
import zio.Console.*
import zio.stream.*
import com.tlmurphy.adventZio.FileReader

object PartOne extends ZIOAppDefault:

  private case class Hand(x: Char):
    def >(that: Hand): Boolean = x match
      case 'A' | 'X' =>
        that.x match
          case 'C' | 'Z' => true
          case _         => false

      case 'B' | 'Y' =>
        that.x match
          case 'A' | 'X' => true
          case _         => false

      case 'C' | 'Z' =>
        that.x match
          case 'B' | 'Y' => true
          case _         => false

    def ==(that: Hand): Boolean = (x, that.x) match
      case ('A' | 'X', 'X' | 'A') => true
      case ('B' | 'Y', 'Y' | 'B') => true
      case ('C' | 'Z', 'Z' | 'C') => true
      case _                      => false

  private case class Round(opponent: Hand, me: Hand):
    def score: Int =
      val myScore: Int = if (me > opponent) 6 else if (me == opponent) 3 else 0
      me.x match
        case 'X' => 1 + myScore
        case 'Y' => 2 + myScore
        case 'Z' => 3 + myScore

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    FileReader
      .getStream("day2.txt")
      .map(s => Round(Hand(s.head), Hand(s.last)).score)
      .runSum
      .flatMap(printLine(_))
