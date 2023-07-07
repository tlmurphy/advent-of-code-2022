package com.tlmurphy.adventCats.day6

import cats.effect.{IO, IOApp}
import com.tlmurphy.adventCats.FileReader

object PartTwo extends IOApp.Simple:

  val window = 14

  def findMarker(s: Iterator[String], count: Int = window): Int =
    s.nextOption() match
      case None => count
      case Some(value) =>
        if (value.distinct == value) count else findMarker(s, count + 1)

  override def run: IO[Unit] =
    FileReader
      .getStream("day6.txt")
      .takeWhile(_ != "")
      .map(_.sliding(window))
      .map(findMarker(_))
      .evalMap(x => IO(println(x)))
      .compile
      .drain
