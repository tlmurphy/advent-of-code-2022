package com.tlmurphy.adventCats.day1

import cats.effect.{IO, IOApp}
import cats.syntax.all.*
import fs2.Chunk
import com.tlmurphy.adventCats.FileReader

object PartTwo extends IOApp.Simple:

  def toIntChunk(chunk: Chunk[String]): Chunk[Int] =
    chunk.map(_.toInt)

  def computeSums(chunk: Chunk[Int]): Int =
    chunk.sumAll

  override def run: IO[Unit] =
    FileReader
      .getStream("day1.txt")
      .split(_ == "")
      .map(toIntChunk)
      .map(computeSums)
      .compile
      .toList
      .map(_.sorted(Ordering[Int].reverse))
      .map(_.take(3))
      .flatMap(topThree => IO(println(topThree.sum)))
