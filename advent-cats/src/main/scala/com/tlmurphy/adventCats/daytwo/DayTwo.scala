package com.tlmurphy.adventCats.daytwo

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import com.tlmurphy.adventCats.FileReader

object DayTwo extends IOApp.Simple:

  override def run: IO[Unit] =
    FileReader
      .getStream("day1.txt")
      .map(_.map(_.toInt))
      .map(_.sumAll)
      .reduce(_ max _)
      .evalMap(max => IO(println(max)))
      .compile
      .drain
