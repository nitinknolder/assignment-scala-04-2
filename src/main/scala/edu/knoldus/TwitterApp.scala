package edu.knoldus

import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Logger, TwitterFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn
import scala.util.{Failure, Success}


object TwitterApp extends App {

  val log = Logger.getLogger (this.getClass)
  val config = ConfigFactory.load ("application.conf")

  val consumerKey = config.getString ("Twitter.key.consumerKey")
  val consumerSecretKey = config.getString ("Twitter.key.consumerSecretKey")
  val accessToken = config.getString ("Twitter.key.accessToken")
  val accessTokenSecret = config.getString ("Twitter.key.accessTokenSecret")
  val configurationBuilder = new ConfigurationBuilder ()
  configurationBuilder.setDebugEnabled (false)

    .setOAuthConsumerKey (consumerKey)
    .setOAuthConsumerSecret (consumerSecretKey)
    .setOAuthAccessToken (accessToken)
    .setOAuthAccessTokenSecret (accessTokenSecret)
  val twitter = new TwitterFactory (configurationBuilder.build ()).getInstance ()

  val twitterObject = new Configuration


  log.info ("Twitter Menu: \n")
  log.info (" 1: GetTweets \n 2: Count Tweets \n 3: Average ReTweets \n 4: Average Likes \n")
  log.info ("Enter Your Choice: ")
  val choice = StdIn.readInt
  val hashTag = "#ViratKohli"
  val sleepTimer = 1000

  choice match {
    case 1 => val fetchAllTweets = twitterObject.getHashTagTweets (hashTag)
      fetchAllTweets onComplete {
        case Success (fetchAllTweets) => log.info ("\n Successful\n" + fetchAllTweets)
        case Failure (exception) => log.info ("Exception Occurred: \n" + exception)
      }
      val sleepTimer = 1000
      Thread.sleep (sleepTimer)

    case 2 => val noOfTweets = twitterObject.countNumberOfTweets (hashTag)
      noOfTweets onComplete {
        case Success (noOfTweets) => log.info ("\n ***Successful***\n" + noOfTweets)
        case Failure (exception) => log.info ("Exception Occurred: \n" + exception)
      }
      val sleepTimer = 1000
      Thread.sleep (sleepTimer)

    case 3 => val averageTweets = twitterObject.averageReTweets (hashTag)
      averageTweets onComplete {
        case Success (averageTweets) => log.info ("\n ***Successful***\n" + averageTweets)
        case Failure (exception) => log.info ("Exception Occurred: \n" + exception)
      }
      Thread.sleep (sleepTimer)

    case 4 => val averageLikes = twitterObject.averageLikes (hashTag)
      averageLikes onComplete {
        case Success (averageLikes) => log.info ("\n ***Successful***\n" + averageLikes)
        case Failure (exception) => log.info ("Exception Occurred \n" + exception)
      }
      val sleepTimer = 1000
      Thread.sleep (sleepTimer)

    case _ => "Entered Wrong choice-->"
  }
  Thread.sleep (sleepTimer)
}
