# COMP90055

This is the repository for my computing project. Including:

1. Java files to use Twitter4j to retrieve tweets:
       
   Stream Data:  
   		  MyFullStream.java: Used to retrieve stream data.  
		  MyStream.java: Used to retrieve partially stream data (only those tweets that has latitude and longitude data). It is used at               first to retrieve stream data but collect just a few tweets and abondoned. Might be used if anyone is interested in.

   Historical Data:
	ANZFollowerTimeline.java: Used to retrieve historical data of tweets from the followers of @ANZ_AU.
	CBAFollowerTimeline.java: Used to retrieve historical data of tweets from the followers of @CommBank.
	NABFollowerTimeline.java: Used to retrieve historical data of tweets from the followers of @NAB.
	WBCFollowerTimeline.java: Used to retrieve historical data of tweets from the followers of @Westpac.\
        
   MyTweet.java: In the above java files, tweets are first stored in MyTweet object and then store in couchdb.

   Dependencies:
	commons-codec-1.10.jar
        commons-io-2.0.1.jar
        commons-logging-1.2.jar
        httpclient-4.5.6.jar
        httpclient-cache-4.5.2.jar
        httpcore-4.4.10.jar
        jackson-annotations-2.2.2.jar
        jackson-core-2.2.2.jar
        jackson-core-asl-1.8.6.jar
        jackson-core-lgpl-1.0.1.jar
        jackson-databind-2.2.2.jar
        jackson-mapper-asl-1.8.6.jar
        org.ekorp-1.4.1.jar
        slf4j-api-1.7.24.jar
        slf4j-api-1.7.25.jar
        slf4j-jdk14-1.7.24.jar
        twitter4j-async-4.0.7.jar
        twitter4j-core-4.0.7.jar
        twitter4j-examples-4.0.7.jar
        twitter4j-stream-4.0.7.jar       
	
2. Python files to do couchdb documents manipulations such as sentiment analysis:
	
   sentiment.py: Used to get sentiment score for each tweet including polarity and subjectivity.   
   locationfilter.py: Used to standardize the location field of the tweets into one of the largest 15 cities in Australia.
   abbreviationfilter.py: Used to find the tweets in Stream Data that are retrieve by abbreviation and delete them because they may not        refer to the four banks.
   
   Dependencies:
        pip install CouchDB
        pip install textblob





