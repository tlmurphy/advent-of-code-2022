package com.tlmurphy.adventCats.dayone

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import com.tlmurphy.adventCats.FileReader

object PartOne extends IOApp.Simple:

  override def run: IO[Unit] =
    FileReader
      .getStream("day1.txt")
      .split(_ == "")
      .map(_.map(_.toInt))
      .map(_.sumAll)
      .reduce(_ max _)
      .evalMap(max => IO(println(max)))
      .compile
      .drain
