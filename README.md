# Advent of Code 2022

## Introduction to Project

This repo contains solutions to the Advent of Code 2022 challenge
written in Scala using two different libraries, `cats-effect` and `zio`.

In particular, the `advent-cats` project utilizes `fs2` for file streaming
while `advent-zio` utilizes `ZStream` for file streaming. They are fairly
similar with just a couple of differing nuances.

The project is structed by days, with multiple main files for running
the various "parts" of each day. Typically, each day is separated into at least
two parts.

## Running

To run the different files via sbt:

1. `sbt`
2. `project adventCats` or `project adventZio` to switch between the two projects
3. `runMain com.tlmurphy.adventZio.dayone.PartOne` or `run` then select the number of the main
file you wish to run.

> TODO: Make running the files easier?
