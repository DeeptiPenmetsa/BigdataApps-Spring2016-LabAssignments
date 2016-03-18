import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Deepu on 3/9/16.
 */
public class GetCategoryTrainingData {
    public List<Status> GetCategoryData(String keyword) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("c0QHCgrMM5HCbyhbEEHDOZRW5")
                .setOAuthConsumerSecret("4lZbQLsAI8V8HcFHX0RFSPmSWNiPaAYqaaEoMftyte598Fs1rJ")
                .setOAuthAccessToken("4565837185-yKlzgnTW3Es2r4po5bRqpp53pipejpeTNjv58Q4")
                .setOAuthAccessTokenSecret("MsR84cLRkFBgjcnaNH6WaE6on5OE7DeoYPr6H6HpOjtR0");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Query query;
        query = new Query(keyword +" -filter:retweets -filter:links -filter:replies -filter:images");
        query.setCount(100);
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

    public static void main(String[] args) throws IOException {
        GetCategoryTrainingData getCategoryTraining1 = new GetCategoryTrainingData();
        List<Status> statuses = getCategoryTraining1.GetCategoryData("games");

        int i = 0;
        for (Status status : statuses) {
            if (status.getText() != null) {
                i++;
                File gamesTextFile = new File("data/categoryTraining/games/" + i + ".txt");
                FileWriter fw = new FileWriter(gamesTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }


        GetCategoryTrainingData getCategoryTraining2 = new GetCategoryTrainingData();
        List<Status> statuses2 = getCategoryTraining2.GetCategoryData("movies");

        i = 0;
        for (Status status : statuses2) {
            if (status.getText() != null) {
                i++;
                File moviesTextFile = new File("data/categoryTraining/movies/" + i + ".txt");
                FileWriter fw = new FileWriter(moviesTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }

        GetCategoryTrainingData getCategoryTraining3 = new GetCategoryTrainingData();
        List<Status> statuses3 = getCategoryTraining3.GetCategoryData("music");

        i = 0;
        for (Status status : statuses3) {
            if (status.getText() != null) {
                i++;
                File musicTextFile = new File("data/categoryTraining/music/" + i + ".txt");                FileWriter fw = new FileWriter(musicTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }
        GetCategoryTrainingData getCategoryTraining4 = new GetCategoryTrainingData();
        List<Status> statuses4 = getCategoryTraining4.GetCategoryData("travel");

        i = 0;
        for (Status status : statuses4) {
            if (status.getText() != null) {
                i++;
                File travelTextFile = new File("data/categoryTraining/travel/" + i + ".txt");
                FileWriter fw = new FileWriter(travelTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }
        GetCategoryTrainingData getCategoryTraining5 = new GetCategoryTrainingData();
        List<Status> statuses5 = getCategoryTraining5.GetCategoryData("books");

        i = 0;
        for (Status status : statuses5) {
            if (status.getText() != null) {
                i++;
                File bookTextFile = new File("data/categoryTraining/books/" + i + ".txt");
                FileWriter fw = new FileWriter(bookTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }

        GetCategoryTrainingData getCategoryTraining6 = new GetCategoryTrainingData();
        List<Status> statuses6 = getCategoryTraining6.GetCategoryData("computers");

        i = 0;
        for (Status status : statuses6) {
            if (status.getText() != null) {
                i++;
                File computersTextFile = new File("data/categoryTraining/computers/" + i + ".txt");
                FileWriter fw = new FileWriter(computersTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }
        GetCategoryTrainingData getCategoryTraining7 = new GetCategoryTrainingData();
        List<Status> statuses7 = getCategoryTraining7.GetCategoryData("technology");

        i = 0;
        for (Status status : statuses7) {
            if (status.getText() != null) {
                i++;
                File technologyTextFile = new File("data/categoryTraining/technology/" + i + ".txt");
                FileWriter fw = new FileWriter(technologyTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }

        GetCategoryTrainingData getCategoryTraining8 = new GetCategoryTrainingData();
        List<Status> statuses8 = getCategoryTraining8.GetCategoryData("health");

        i = 0;
        for (Status status : statuses8) {
            if (status.getText() != null) {
                i++;
                File healthTextFile = new File("data/categoryTraining/health/" + i + ".txt");
                FileWriter fw = new FileWriter(healthTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }
        GetCategoryTrainingData getCategoryTraining9 = new GetCategoryTrainingData();
        List<Status> statuses9 = getCategoryTraining9.GetCategoryData("wealth");

        i = 0;
        for (Status status : statuses9) {
            if (status.getText() != null) {
                i++;
                File wealthTextFile = new File("data/categoryTraining/wealth/" + i + ".txt");
                FileWriter fw = new FileWriter(wealthTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }

        GetCategoryTrainingData getCategoryTraining10 = new GetCategoryTrainingData();
        List<Status> statuses10 = getCategoryTraining10.GetCategoryData("");

        i = 0;
        for (Status status : statuses10) {
            if (status.getText() != null) {
                i++;
                File otherTextFile = new File("data/categoryTraining/other/" + i + ".txt");
                FileWriter fw = new FileWriter(otherTextFile);
                fw.write(status.getText());
                fw.close();
            }
        }
    }

}
