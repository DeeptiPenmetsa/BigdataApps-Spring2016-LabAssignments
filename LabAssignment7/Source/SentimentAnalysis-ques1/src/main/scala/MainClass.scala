/**
 * Created by Mayanka on 23-Jul-15.
 */
/*object MainClass {

  def main(args: Array[String]) {
    val sentimentAnalyzer: SentimentAnalyzer = new SentimentAnalyzer
    val tweetWithSentiment: TweetWithSentiment = sentimentAnalyzer.findSentiment("I hate you")
    System.out.println(tweetWithSentiment)
  }
}*/
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object MainClass {

  def main(args: Array[String]) {

    val filters = Array("oscar","leo","Primary","election","clinton","cruz","trump","sanders");
    // val filters = args

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials

    System.setProperty("twitter4j.oauth.consumerKey", "c0QHCgrMM5HCbyhbEEHDOZRW5")
    System.setProperty("twitter4j.oauth.consumerSecret", "4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
    System.setProperty("twitter4j.oauth.accessToken", "4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0")

    //Create a spark configuration with a custom name and master
    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
    val sparkConf = new SparkConf().setAppName("DeepuApp1").setMaster("local[*]")
    //Create a Streaming COntext with 2 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream (By the way you can also use filters)
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)
    //stream.print()

    val text = stream.map(tweet => tweet.getText())

    text.foreachRDD(rdd => {
      //val tweets = rdd
      //  rdd.foreach(println)
      rdd.foreach{string =>
        val sentimentAnalyzer: SentimentAnalyzer = new SentimentAnalyzer
        val tweetWithSentiment: TweetWithSentiment = sentimentAnalyzer.findSentiment(string)
        //System.out.println(tweetWithSentiment)
        SocketClient.sendCommandToRobot(tweetWithSentiment.toString)
      }

    })
    ssc.start()

    ssc.awaitTermination()
  }
}
