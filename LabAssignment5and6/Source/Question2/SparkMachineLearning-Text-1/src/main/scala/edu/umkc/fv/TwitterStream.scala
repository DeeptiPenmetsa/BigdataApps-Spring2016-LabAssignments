/**
  * Created by DEEPU on 3/2/2016.
  */
package edu.umkc.fv

import twitter4j._
import java.io.PrintWriter
import scala.io.StdIn.readLine


object StatusStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
     twitterStream.addListener(Util.simpleStatusListener)
     twitterStream.filter(new FilterQuery().track(Array[String]{"#"}))
    Thread.sleep(50000)
    twitterStream.cleanUp
    twitterStream.shutdown

  }
}

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("c0QHCgrMM5HCbyhbEEHDOZRW5")
    .setOAuthConsumerSecret("4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
    .setOAuthAccessToken("4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
    .setOAuthAccessTokenSecret("MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0")
    .build
  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { println(status.getText) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

}


