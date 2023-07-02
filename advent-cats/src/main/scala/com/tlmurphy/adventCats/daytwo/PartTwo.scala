package com.tlmurphy.adventCats.daytwo

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import com.tlmurphy.adventCats.FileReader

case class RoundTwo(opponent: Char, me: Char):
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
