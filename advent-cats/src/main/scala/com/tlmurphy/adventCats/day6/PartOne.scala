package com.tlmurphy.adventCats.day6

import cats.effect.IOApp
import cats.effect.IO
import com.tlmurphy.adventCats.FileReader

object PartOne extends IOApp.Simple:

  def findMarker(s: Iterator[String], count: Int = 4): Int =
    s.nextOption() match
      case None => count
      case Some(value) =>
        if (value.distinct == value) count else findMarker(s, count + 1)

  override def run: IO[Unit] =
    FileReader
      .getStream("day6.txt")
      .takeWhile(_ != "")
      .map(_.sliding(4))
      .map(findMarker(_))
      .evalMap(x => IO(println(x)))
      .compile
      .drain
