package com.example.processorapiscala

import org.apache.logging.log4j.LogManager
import cats.effect.IO
import com.example.processorapiscala.model.{FilmSchema, MongoDatabase, PlayerSchema}
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._
import org.mongodb.scala.bson.collection.immutable.Document

object Main extends App {
  val logger = LogManager.getLogger("main")
  lazy val db = new MongoDatabase("mongodb://localhost:27017")

  def healthCheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  /**
   *
   * @return
   */
  def player: Endpoint[IO, PlayerSchema] = get("player" :: param[String]("name")) {
    name: String =>
      val player = db.getPlayer(name)

      player match {
        case Some(p) => Ok(p)
        case None => {
          println("Received no object")
          NoContent
        }
      }
  }

  /**
   *
   * @return
   */
  def film: Endpoint[IO, FilmSchema] = get("film" :: param[String]("title")) {
    title: String =>
      val film = db.getFilm(title)

      film match {
        case Some(f) => Ok(f)
        case None => {
          println("Received no object")
          NoContent
        }
      }
  }

  /**
   *
   * @return
   */
  def randomPlayer: Endpoint[IO, PlayerSchema] = get("player" :: path("random")) {
    val player = db.getRandomPlayer()

    player match {
      case Some(p) => Ok(p)
      case None => {
        println("Received no object")
        NoContent
      }
    }

    Ok(PlayerSchema("Pepe", 1984, Some(2014)))
  }

  /**
   *
   * @return
   */
  def randomFilm: Endpoint[IO, FilmSchema] = get("film" :: path("random")) {
    val film = db.getRandomFilm()

    film match {
      case Some(f) => Ok(f)
      case None => {
        println("Received no object")
        NoContent
      }
    }
  }

  def service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthCheck)
    .serve[Application.Json](player)
    .serve[Application.Json](randomPlayer)
    .serve[Application.Json](film)
    .serve[Application.Json](randomFilm)
    .toService

  Await.ready(Http.server.serve("localhost:8080", service))
}