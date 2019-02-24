from textblob import TextBlob
import couchdb

#link to couchdb
couch = couchdb.Server('http://*****:*****@115.146.92.94:5984/')

#link to specific database
db = couch['collector2']

#calculate sentiment score for each document in the database
for id in db:
    doc = db[id]
    try:
        con = doc['content']
        testi = TextBlob(con)
        pol = testi.sentiment.polarity
        sub = testi.sentiment.subjectivity
        doc['polarity'] = pol
        doc['subjectivity'] = sub

        #modify the document by adding the polarity and subjectivity
        db[id] = doc

    except KeyError:
        continue
