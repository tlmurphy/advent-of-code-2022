package com.tlmurphy.adventCats.day5

import cats.effect.IOApp
import cats.effect.IO
import cats.syntax.*
import scala.annotation.tailrec
import com.tlmurphy.adventCats.FileReader

object PartOne extends IOApp.Simple:

  case class Operation(amount: Int, from: Int, to: Int):
    def execute(crates: Array[List[String]]): Unit =
      val items = crates(from - 1).take(amount)
      val newTo = items.reverse ::: crates(to - 1)
      crates(from - 1) = crates(from - 1).drop(amount)
      crates(to - 1) = newTo

  @tailrec
  def parseCrateInput(s: String, crate: List[String]): List[Option[String]] =
    if (s.isEmpty)
      crate.reverse.map(c =>
        if (c.isBlank()) None
        else Option(c.charAt(1).toString())
      )
    else
      val item = s.take(3)
      parseCrateInput(s.drop(4), item :: crate)

  def parseOperation(s: String): Operation =
    val split = s.split(" ")
    Operation(split(1).toInt, split(3).toInt, split(5).toInt)

  def topOfStacks(crates: Array[List[String]]): String =
    crates.map(_.head).mkString

  override def run: IO[Unit] =
    val stream = FileReader
      .getStream("day5.txt")
      .split(_ == "")

    val crateStream = stream
      .take(1)
      .map(_.map(s => parseCrateInput(s, List.empty)))
      .map(_.dropRight(1).toList.transpose)
      .map(_.map(_.flatten))

    val operationStream = stream.drop(1).map(c => c.map(parseOperation).toList)

    for {
      _ <- stream.compile.drain
      crates <- crateStream.compile.toList.map(_.flatten)
      crateArray <- IO(crates.toArray)
      operations <- operationStream.compile.toList.map(_.flatten)
      _ <- IO(operations.foreach(_.execute(crateArray)))
      _ <- IO(println(topOfStacks(crateArray)))
    } yield ()
