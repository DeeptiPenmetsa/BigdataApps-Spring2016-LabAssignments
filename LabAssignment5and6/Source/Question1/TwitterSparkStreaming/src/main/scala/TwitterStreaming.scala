import org.apache.spark.{rdd, SparkConf}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by Deepu on 03/02/2016.
 */
object TwitterStreaming {

  def main(args: Array[String]) {


    val filters = args

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials

    System.setProperty("twitter4j.oauth.consumerKey", "c0QHCgrMM5HCbyhbEEHDOZRW5")
    System.setProperty("twitter4j.oauth.consumerSecret", "4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
    System.setProperty("twitter4j.oauth.accessToken", "4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0")

    //Create a spark configuration with a custom name and master
    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
    val sparkConf = new SparkConf().setAppName("Deepuapp1").setMaster("local[*]")
    //Create a Streaming COntext with 2 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream (By the way you can also use filters)
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)
    stream.print()
    //Map : Retrieving Hash Tags
    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))

    //Finding the top hash Tgas on 60 second window
    val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
    .map{case (topic, count) => (count, topic)}
    .transform(_.sortByKey(false))

    topCounts30.foreachRDD(rdd => {
      val topList = rdd.take(30)
      println("\nPopular topics in last 60 seconds (%s total):".format(rdd.count()))
     topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
     var s:String="Popular topics used in last 10 seconds: \nWords:Count \n"
      topList.foreach{case(count,tag)=>{

       s+=tag+" : "+count+"\n"

      }}
     SocketClient.sendCommandToRobot(s)
    })
    ssc.start()
    ssc.awaitTermination()
  }
}