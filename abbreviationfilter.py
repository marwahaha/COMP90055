from textblob import TextBlob
import couchdb
couch = couchdb.Server('http://admin:62397917@115.146.92.94:5984/')
db = couch['collector2']
for id in db:
    doc = db[id]
    try:
        con = doc['content']

        if (' anz ' in con.lower()) or (' cba ' in con.lower()) or (' nab ' in con.lower()) or (' wbc ' in con.lower()):
            del db[id]

    except KeyError:
        continue
