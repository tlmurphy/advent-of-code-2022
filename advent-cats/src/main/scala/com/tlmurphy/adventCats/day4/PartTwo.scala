package com.tlmurphy.adventCats.day4

import cats.effect.{IO, IOApp}
import com.tlmurphy.adventCats.FileReader
import scala.collection.immutable.Range.Inclusive

object PartTwo extends IOApp.Simple:

  private case class SectionAssignment(first: Inclusive, second: Inclusive):
    def isOverlapping: Boolean = (first intersect second).nonEmpty

  private def toInclusive(s: String): Inclusive =
    val split = s.split("-")
    (split.head.toInt to split.last.toInt)

  override def run: IO[Unit] = FileReader
    .getStream("day4.txt")
    .takeWhile(_ != "")
    .map(_.split(","))
    .map(s => SectionAssignment(toInclusive(s.head), toInclusive(s.last)))
    .filter(_.isOverlapping)
    .compile
    .toList
    .flatMap(l => IO(println(l.size)))
