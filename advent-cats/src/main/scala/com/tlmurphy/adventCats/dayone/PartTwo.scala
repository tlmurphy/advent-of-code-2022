package com.tlmurphy.adventCats.dayone

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
    val stream = FileReader.getStream("day1.txt").split(_ == "")
    for
      chunks <- stream.compile.toList
      intChunks <- IO(chunks.map(toIntChunk))
      sums <- IO(intChunks.map(computeSums))
      sortedSums <- IO(sums.sorted(Ordering[Int].reverse))
      topThree <- IO(sortedSums.take(3))
    yield println(topThree.sum)
