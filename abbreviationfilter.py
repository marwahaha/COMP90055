from textblob import TextBlob
import couchdb

#connect to couchdb
couch = couchdb.Server('http://*****:*****@115.146.92.94:5984/')

#connect to specific database
db = couch['collector2']

#for each document, find the ones that are related to the abbreviation of the four banks and delete them
for id in db:
    doc = db[id]
    try:
        con = doc['content']

        if (' anz ' in con.lower()) or (' cba ' in con.lower()) or (' nab ' in con.lower()) or (' wbc ' in con.lower()):
            del db[id]

    except KeyError:
        continue
