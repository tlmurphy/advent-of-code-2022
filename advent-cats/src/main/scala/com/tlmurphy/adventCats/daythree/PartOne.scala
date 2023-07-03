package com.tlmurphy.adventCats.daythree

import cats.effect.IOApp
import cats.effect.IO
import com.tlmurphy.adventCats.FileReader

object PartOne extends IOApp.Simple:

  private case class Sack(contents: String):
    val compartments = contents.splitAt(contents.length() / 2)
    val priorites: Map[Char, Int] =
      ((('a' to 'z') ++ ('A' to 'Z')) zip (1 to 52)).toMap
    def commonItem: Char =
      (compartments._1 intersect compartments._2).head
    def prioVal(c: Char): Int = priorites(c)

  override def run: IO[Unit] =
    FileReader
      .getStream("day3.txt")
      .takeWhile(_ != "")
      .map(Sack(_))
      .map(s => s.prioVal(s.commonItem))
      .reduce(_ + _)
      .evalMap(prioSum => IO(println(prioSum)))
      .compile
      .drain
