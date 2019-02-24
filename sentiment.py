from textblob import TextBlob
import couchdb
couch = couchdb.Server('http://admin:62397917@115.146.92.94:5984/')
db = couch['collector2']
#doc = {'name': 'OMG', 'content': 'wmc'}
#db.save(doc)
#print (doc)
for id in db:
    doc = db[id]
    try:
        con = doc['content']
        testi = TextBlob(con)
        pol = testi.sentiment.polarity
        sub = testi.sentiment.subjectivity
        doc['polarity'] = pol
        doc['subjectivity'] = sub

        db[id] = doc

    except KeyError:
        print("OMG")
