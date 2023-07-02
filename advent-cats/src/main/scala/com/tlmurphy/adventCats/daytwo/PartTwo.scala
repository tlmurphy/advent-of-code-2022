package com.tlmurphy.adventCats.daytwo

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import com.tlmurphy.adventCats.FileReader

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

object PartTwo extends IOApp.Simple:
  override def run: IO[Unit] =
    FileReader
      .getStream("day2.txt")
      .map(chunk => chunk.map(c => RoundTwo(c.head, c.last)))
      .map(chunk => chunk.map(_.score))
      .map(_.sumAll)
      .evalMap(x => IO(println(x)))
      .compile
      .drain
