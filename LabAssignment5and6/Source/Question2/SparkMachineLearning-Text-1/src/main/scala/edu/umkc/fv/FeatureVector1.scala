package edu.umkc.fv

import edu.umkc.fv.NLPUtils._
import edu.umkc.fv.Utils._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Deepu on 02-Mar-16.
  */
object FeatureVector1 {

  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "F:\\winutils")

    val filters = args

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials

    System.setProperty("twitter4j.oauth.consumerKey", "c0QHCgrMM5HCbyhbEEHDOZRW5")
    System.setProperty("twitter4j.oauth.consumerSecret", "4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
    System.setProperty("twitter4j.oauth.accessToken", "4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0")

    //Create a spark configuration with a custom name and master
    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
    val sparkConf = new SparkConf().setAppName("Deepuapp1").setMaster("local[*]").setAppName("FeatureVector1").set("spark.driver.memory", "3g").set("spark.executor.memory", "3g")
    //Create a Streaming COntext with 30 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream (By the way you can also use filters)
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)
    stream.print()

    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))


    val sc = ssc.sparkContext
    val stopWords = sc.broadcast(loadStopWords("/stopwords.txt")).value
    val labelToNumeric = createLabelMap("data/training/")
    var model: NaiveBayesModel = null
    // Training the data
    val training = sc.wholeTextFiles("data/training/*")
      .map(rawText => createLabeledDocument(rawText, labelToNumeric, stopWords))
    val X_train = tfidfTransformer(training)
    X_train.foreach(vv => println(vv))

    model = NaiveBayes.train(X_train, lambda = 1.0)

    val lines=sc.wholeTextFiles("data/testing/*")
    val data = lines.map(line => {

      val test = createLabeledDocumentTest(line._2, labelToNumeric, stopWords)
      println(test.body)
      test


    })

    val X_test = tfidfTransformerTest(sc, data)

    val predictionAndLabel = model.predict(X_test)
    println("PREDICTION")
    predictionAndLabel.foreach(x => {
      labelToNumeric.foreach { y => if (y._2 == x) {
        println(y._1)
      }
      }
    })

    ssc.start()

    ssc.awaitTerminationOrTimeout(300)



  }


}

