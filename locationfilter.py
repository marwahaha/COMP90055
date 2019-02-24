import couchdb
couch = couchdb.Server('http://*****:*****@115.146.92.94:5984/')
db = couch['collector']
for id in db:
    doc = db[id]
    try:
        if doc['location'] == "":
            continue
        elif 'sydney' in doc['location'].lower():
            doc['location'] = 'sydney'
        elif 'melbourne' in doc['location'].lower():
            doc['location'] = 'melbourne'
        elif 'brisbane' in doc['location'].lower():
            doc['location'] = 'brisbane'
        elif 'perth' in doc['location'].lower():
            doc['location'] = 'perth'
        elif 'adelaide' in doc['location'].lower():
            doc['location'] = 'adelaide'
        elif ('gold coast' in doc['location'].lower()) or ('goldcoast' in doc['location'].lower()):
            doc['location'] = 'gold coast'
        elif 'newcastle' in doc['location'].lower():
            doc['location'] = 'newcastle'
        elif 'canberra' in doc['location'].lower():
            doc['location'] = 'canberra'
        elif ('sunshine coast' in doc['location'].lower()) or ('sunshinecoast' in doc['location'].lower()):
            doc['location'] = 'sunshine coast'
        elif 'wollongong' in doc['location'].lower():
            doc['location'] = 'wollongong'
        elif 'geelong' in doc['location'].lower():
            doc['location'] = 'geelong'
        elif 'hobart' in doc['location'].lower():
            doc['location'] = 'hobart'
        elif 'townsville' in doc['location'].lower():
            doc['location'] = 'townsville'
        elif 'cairns' in doc['location'].lower():
            doc['location'] = 'cairns'
        elif 'darwin' in doc['location'].lower():
            doc['location'] = 'darwin'
        elif 'australia' in doc['location'].lower():
            doc['location'] = 'australia'


        db[id] = doc

    except KeyError:
        continue
