package com.tlmurphy.adventCats.daytwo

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import com.tlmurphy.adventCats.FileReader

object PartTwo extends IOApp.Simple:

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

  override def run: IO[Unit] =
    FileReader
      .getStream("day2.txt")
      .takeWhile(_ != "")
      .map(s => Round(s.head, s.last))
      .map(_.score)
      .reduce(_ + _)
      .evalMap(scoreSum => IO(println(scoreSum)))
      .compile
      .drain
