import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by Deepu on 3/14/16.
 */

public class TwitterSentimentalAnalysis {
    public List<Status> search(String keyword) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("c0QHCgrMM5HCbyhbEEHDOZRW5")
                .setOAuthConsumerSecret("4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
                .setOAuthAccessToken("4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
                .setOAuthAccessTokenSecret("MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(50);
        query.setLocale("en");
        query.setLang("en");
        try {
            QueryResult queryResult = twitter.search(query);
            return queryResult.getTweets();
        } catch (TwitterException e) {
            // ignore
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    public static void main(String[] args) {
        TwitterSentimentalAnalysis twitterSentimentalAnalysis = new TwitterSentimentalAnalysis();
        List<Status> statuses = twitterSentimentalAnalysis.search("Robot");

        for (Status status : statuses) {
            SentimentAnalyzer doAnalysis = new SentimentAnalyzer();
            TweetWithSentiment tweetWithSentiment = doAnalysis.findSentiment(status.getText());
            System.out.println(tweetWithSentiment);
        }
    }


}
