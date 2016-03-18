import NLPUtils._
import Utils._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Deepu on 3/16/16.
  */

class TwitterCategoryAnalysis {

  def CategoryAnalysis() : Int = {

    val sparkConf = new SparkConf().setAppName("TwitterCategoryRecommendation").setMaster("local[*]").set("spark.driver.memory", "3g").set("spark.executor.memory", "3g")
    val ssc = new StreamingContext(sparkConf.set("spark.driver.allowMultipleContexts", "true"), Seconds(2))
    val sc = ssc.sparkContext

    //Train tweets datasets
    val stopWords = sc.broadcast(loadStopWords("/stopwords.txt")).value
    val labelToNumeric = createLabelMap("data/TrainingCategoryData/")
    println(labelToNumeric)

    var model: NaiveBayesModel = null

    val training = sc.wholeTextFiles("data/TrainingCategoryData/*")
      .map(rawText => createLabeledDocument(rawText, labelToNumeric, stopWords))
    val X_train = tfidfTransformer(training)
    X_train.foreach(vv => println(vv))

    model = NaiveBayes.train(X_train, lambda = 1)


    val lines=sc.wholeTextFiles("data/testing/*")

    val data = lines.map(line => {

      val test = createLabeledDocumentTest(line._2, labelToNumeric, stopWords)
      println(test.body)
      test

    })

    val X_test = tfidfTransformerTest(sc, data)

    val predictionAndLabel = model.predict(X_test)



    println("PREDICTION")

    var returnValue = 0.0
    returnValue = predictionAndLabel.first()

    sc.stop()

    returnValue.toInt
  }
}
