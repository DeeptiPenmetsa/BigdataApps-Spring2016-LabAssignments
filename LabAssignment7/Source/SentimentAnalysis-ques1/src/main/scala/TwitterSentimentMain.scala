/**
  * Created by DEEPU on 3/7/2016.
  */
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TwitterSentimentMain {
  def main(args: Array[String]) {
    val filters = args

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials
    System.setProperty("twitter4j.oauth.consumerKey", "c0QHCgrMM5HCbyhbEEHDOZRW5")
    System.setProperty("twitter4j.oauth.consumerSecret", "4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
    System.setProperty("twitter4j.oauth.accessToken", "4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0")

    //Create a spark configuration with a custom name and master
    val sparkConf = new SparkConf().setAppName("Deepuapp1").setMaster("local[*]")
    //Create a Streaming COntext with 2 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)
    //  stream.print()

    val sentiment:DStream[TweetWithSentiment]=stream.map{Status=>{
      val st=Status.getText()
      val sa=new SentimentAnalyzer()
      val tw=sa.findSentiment(st)
      tw
    }}

    sentiment.foreachRDD{
      rdd=>rdd.foreach{
        tw=> {
          if(tw!=null)
            println(tw.getLine+"      "+tw.getCssClass)
        }}}
    ssc.start()

    ssc.awaitTermination()
  }

}