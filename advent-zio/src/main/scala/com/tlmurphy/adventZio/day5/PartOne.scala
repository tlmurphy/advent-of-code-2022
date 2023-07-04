package com.tlmurphy.adventZio.day5

import zio.*
import zio.Console.*
import com.tlmurphy.adventZio.FileReader
import scala.annotation.tailrec

object PartOne extends ZIOAppDefault:

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

  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] =
    val stream = FileReader
      .getStream("day5.txt")
      .split(_ == "")
    val crateStream = stream
      .take(1)
      .map(s => s.map(x => parseCrateInput(x, List.empty)))
      .map(_.dropRight(1).transpose)
      .map(c => c.map(_.flatten.toList))
    val operationStream = stream
      .drop(1)
      .map(_.map(parseOperation))

    for {
      _ <- stream.runDrain
      crates <- crateStream.runCollect
      operations <- operationStream.runCollect
      crateArray <- ZIO.succeed(crates.flatten.toArray)
      _ <- ZIO.succeed(operations.flatten.foreach(_.execute(crateArray)))
      _ <- printLine(topOfStacks(crateArray))
    } yield ()
