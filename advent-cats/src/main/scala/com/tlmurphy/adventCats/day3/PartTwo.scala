package com.tlmurphy.adventCats.day3

import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.*
import com.tlmurphy.adventCats.FileReader
import fs2.Chunk

object PartTwo extends IOApp.Simple:

  private case class SackGroup(sacks: Chunk[String]):
    val priorites: Map[Char, Int] =
      ((('a' to 'z') ++ ('A' to 'Z')) zip (1 to 52)).toMap
    def commonItem: Char =
      sacks.toList.reduce(_ intersect _).head
    def prioVal(c: Char): Int = priorites(c)

  override def run: IO[Unit] =
    FileReader
      .getStream("day3.txt")
      .takeWhile(_ != "")
      .chunkN(3, false)
      .map(SackGroup(_))
      .map(s => s.prioVal(s.commonItem))
      .reduce(_ + _)
      .evalMap(prioSum => IO(println(prioSum)))
      .compile
      .drain
