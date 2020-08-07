package com.example.processorapiscala.model

trait Schema {}

case class PlayerSchema(
  primaryName: String,
  birthYear: Int,
  deathYear: Option[Int],
) extends Schema

case class FilmSchema(
  primaryTitle: String,
  startYear: Int,
  endYear: Option[Int],
  genres: String,
) extends Schema