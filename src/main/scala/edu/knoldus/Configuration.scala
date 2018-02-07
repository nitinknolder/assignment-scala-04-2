package edu.knoldus

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import twitter4j.{Logger, Query, Status}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Configuration {

  val log = Logger.getLogger (this.getClass)
  val count = 100
  /** Calculate Date difference */
  val twitter = TwitterApp.twitter
  val startDate = "2018-01-01"
  val endDate = "2018-01-31"
  val formatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd")
  val oldDate = LocalDate.parse (startDate, formatter)
  val currentDate = LocalDate.parse (endDate, formatter)
  val dateDifference = currentDate.toEpochDay () - oldDate.toEpochDay ()

  /** Used to print Selected tweets **/
  def getHashTagTweets (search: String): Future[List[Status]] = Future {

    val query = new Query (search)
    val result = twitter.search (query).getTweets.asScala.toList
    result
  }

  /** Count number Of Tweets **/
  def countNumberOfTweets (search: String): Future[Int] = Future {

    val query = new Query (search)
    query.setSince ("2017-01-01")
    query.setCount (count)
    val result = twitter.search (query).getTweets.asScala.toList.size
    result
  }

  def averageReTweets (search: String): Future[Long] = Future {
    val query = new Query (search)
    query.setCount (count)
    log.info ("Date Difference: \n" + dateDifference) //To print Difference Between Dates
    val listOfTweets = twitter.search (query)
    val reTweet = listOfTweets.getTweets.asScala.toList
    val result = reTweet.map (_.getRetweetCount)
    val sumOfTweets = result.sum
    log.info ("SumOfTweet" + sumOfTweets) //To print Sum
    val averageRetweet = sumOfTweets / dateDifference
    averageRetweet
  }

  def averageLikes (search: String): Future[Long] = Future {

    val query = new Query (search)
    print (dateDifference)
    query.setCount (count)
    val listOfTweets = twitter.search (query)
    val likes = listOfTweets.getTweets.asScala.toList
    val likesResult = likes.map (_.getFavoriteCount)
    print ("Result:" + likesResult)
    val sumOfLikes = likesResult.sum
    print ("SumOfLikes: " + sumOfLikes)
    val averageLikes = sumOfLikes / dateDifference
    averageLikes
  }
}
