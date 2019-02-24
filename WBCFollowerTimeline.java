import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ANZFollowerTimeline {
    public static void main(String[] args) throws MalformedURLException, TwitterException{
    	
	//twitter developer account configuration
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey("577zgTjzybqSNSaNQdzU*****")
    	  .setOAuthConsumerSecret("Qa5PgAVWBcgTA73qMIecwcg4ZbAuvj5tfUiXvICLbibmp*****")
    	  .setOAuthAccessToken("1090964838291890176-fZfV8W2Ubbc4oXHaf14BdUwXU*****")
    	  .setOAuthAccessTokenSecret("Vez7KvSJ6OIa7a60Utng2oTM4kL4ljcm1Xrv2CVY*****");
    	
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();
    	
    	//Ekorp configuration to connect to couchdb
    	HttpClient httpClient = new StdHttpClient.Builder()
				.url("http://115.146.92.16:5984/")
				.username("*****")
				.password("*****")
				.build();
		
	CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
	CouchDbConnector db = dbInstance.createConnector("collector", false);
		
	//variables for getting timeline
	long cursor = -1;
	IDs WBCFollowers;
	ResponseList<Status> timeLine;
		
	//set the bound of the search all the way to one year ago
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.YEAR, -1);
		
	//get follower ID lists, 20 per page, one loop get one page 
	do {			
		WBCFollowers = twitter.getFollowersIDs("Westpac", cursor, 20);
		long[] WBCIDs = WBCFollowers.getIDs();
			
		//get tweets for each of the 20 followers per loop
		for (int i=0; i<WBCIDs.length; i++) {
			int page = 1;
			int count = 50;
			Date collectTime = Calendar.getInstance().getTime();
			long userID = WBCIDs[i];
				
			//get tweets for the follower, 50 tweets per page, until reaches one year ago			    
			for (int j=0; cal.getTime().before(collectTime); j++) {
					
				Paging paging = new Paging(page, count);
				try {
					timeLine = twitter.getUserTimeline(userID, paging);
				}catch(TwitterException e) {
					break;
				}
					
				long start = System.currentTimeMillis();
					
				for (Status tweet: timeLine) {
						
					//check the content
					String content = tweet.getText();
		            		Boolean NAB1 = content.contains(" NAB ");
		            		Boolean NAB2 = content.toLowerCase().contains("national australia bank");
		            		Boolean NAB3 = content.contains("@NAB");
		            		Boolean CBA1 = content.contains(" CBA ");
		            		Boolean CBA2 = content.toLowerCase().contains("commonwealth bank");
		            		Boolean CBA3 = content.contains("@CommBank ");
		            		Boolean ANZ1 = content.contains(" ANZ ");
		            		Boolean ANZ2 = content.toLowerCase().contains("australia and new zealand banking");
		            		Boolean ANZ3 = content.contains("@ANZ_AU ");
		            		Boolean WBC1 = content.contains(" WBC ");
		            		Boolean WBC2 = content.toLowerCase().contains("westpac");
		            	
		            		if (NAB1||NAB2|| NAB3|| CBA1|| CBA2|| CBA3|| ANZ1|| ANZ2|| ANZ3|| WBC1|| WBC2) {
		            			//retrieve useful tweets attributes
		            			String id = String.valueOf(tweet.getId());
		            			String user = String.valueOf(tweet.getUser().getScreenName());
		            			String cont = String.valueOf(tweet.getText());
		            			String location = String.valueOf(tweet.getUser().getLocation());
		            			//change time format
		            			Date tweetTime = tweet.getCreatedAt();
		                    		String[] timeSplit = tweetTime.toString().split(" ");
		                    		String time = timeSplit[5] + " " + timeSplit[1] + " " + timeSplit[2];
		                    
		                    		Boolean nab = false;
		                    		Boolean cba = false;
		                    		Boolean anz = false;
		                    		Boolean wbc = false;
		                                        
		                    		if (NAB1||NAB2||NAB3) {
		                    			nab = true;
		                    		}
		                    
		                    		if (CBA1||CBA2||CBA3) {
		                    			cba = true;
		                    		}
		                    
		                    		if (ANZ1||ANZ2||ANZ3) {
		                    			anz = true;
		                    		}
		                    
		                    		if (WBC1||WBC2) {
		                    			wbc = true;
		                    		}
		            		
		            			try {
		            				//store in couchdb
		            				MyTweet t1 = new MyTweet(user, cont, location, time, nab, cba, anz, wbc);
		                			db.create(id, t1);
		            			}catch(org.ektorp.UpdateConflictException ue) {
		            			
		            			}
		            		}		            	
				}
				long end = System.currentTimeMillis();
				long duration = end - start;
					
				//prevent the limitation of Twitter API (180 per 15min)
				if (duration < 5050) {
					try {
						Thread.sleep(5050-duration);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				int length = timeLine.size();
				if (length > 0) {
					collectTime = timeLine.get(length-1).getCreatedAt();
				}else {
					break;
				}
				
					
				page = page + 1;
			}
		}
	} while ((cursor = WBCFollowers.getNextCursor()) != 0);
		
     }
}
