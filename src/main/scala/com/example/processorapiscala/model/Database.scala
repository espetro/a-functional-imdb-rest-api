package com.example.processorapiscala.model

import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.Await
import scala.concurrent.duration._
import org.mongodb.scala.model.Aggregates._
import org.mongodb.scala.model.Filters._

trait Database {
  def appName: String
  def getPlayer(name: String): Option[PlayerSchema]
  def getRandomPlayer(): Option[PlayerSchema]
  def getFilm(title: String): Option[FilmSchema]
  def getRandomFilm(): Option[FilmSchema]
}

class MongoDatabase(uri: String) extends Database {
  lazy val appName: String = "MongoDB"

  val logger = System.out// LogManager.getRootLogger
  val timeout: Duration = 5 seconds
  val conn: MongoClient = MongoClient(uri)

  val crewCodec = fromRegistries(fromProviders(classOf[PlayerSchema]), DEFAULT_CODEC_REGISTRY)
  val crewColl = conn.getDatabase("imdb")
    .withCodecRegistry(crewCodec)
    .getCollection[PlayerSchema]("crew")

  val filmCodec = fromRegistries(fromProviders(classOf[FilmSchema]), DEFAULT_CODEC_REGISTRY)
  val filmColl = conn.getDatabase("imdb")
    .withCodecRegistry(filmCodec)
    .getCollection[FilmSchema]("films")

  def close(): Unit = {
    conn.close()
  }

  /**
   *
   * @param name
   * @return
   */
  def getPlayer(name: String): Option[PlayerSchema] = {
    val _filter = regex("primaryName", name, "i")
    val query = crewColl.find(_filter).headOption

    Await.result(query, timeout)
  }

  /**
   *
   * @param title
   * @return
   */
  def getFilm(title: String): Option[FilmSchema] = {
    val _filter = regex("primaryTitle", title, "i")
    val query = filmColl.find(_filter).headOption

    Await.result(query, timeout)
  }

  /**
   *
   * @return
   */
  def getRandomPlayer(): Option[PlayerSchema] = {
    val _step = sample(1)
    val query = crewColl.aggregate(Seq(_step)).headOption

    Await.result(query, timeout)
  }

  /**
   *
   * @return
   */
  def getRandomFilm(): Option[FilmSchema] = {
    val _step = sample(1)
    val query = filmColl.aggregate(Seq(_step)).headOption

    Await.result(query, timeout)
  }
}
