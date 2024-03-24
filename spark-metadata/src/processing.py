import os

from es_client import ElasticSearchClient
from geolocation import GeoLocationService
from window_classifier import WindowClassifier

from deep_translator import GoogleTranslator
from datetime import datetime

# Mapping is needed to save GeoPoints.

ES_INDEX = 'keylogger_stats'
ES_MAPPING = {
    "mappings": {
        "properties": {
            "location": {
                "type": "geo_point"
            }
        }
    }
}
    
# Loads window titles from multiple files.
# Classifies the window based on its title.

def get_delta_timestamps(t_begin, t_end) -> dict:
    tdelta = t_end - t_begin
    delta_secs = tdelta.total_seconds()
    return { 'delta_secs': delta_secs }

# Loads window titles from multiple files.
# Classifies the window based on its title.

def get_current_timestamp_utc() -> dict:
    now = datetime.now()
    return { '@timestamp': now.strftime('%Y-%m-%dT%H:%M:%S.%fZ') }

# Processes each batch of data and perform various operations on each row.
# Translates "windowName" to English and convert to lowercase, then update the document.
# Finally, it indexes the document in Elasticsearch.

def process_batch(df, _id):
    window_classifier = WindowClassifier()
    geolocation_service = GeoLocationService()
    elasticsearch_client = ElasticSearchClient(os.environ['ELASTICSEARCH_URL'])
    elasticsearch_client.create_index(ES_INDEX, ES_MAPPING)
    
    for idx, row in enumerate(df.collect()):

        doc = row.asDict()
        doc.update(get_current_timestamp_utc())
        doc.update(get_delta_timestamps(doc['timestampBegin'], doc['timestampEnd']))
        doc.update(geolocation_service.get_geo_ip_coords(doc['ipAddress']))

        doc['windowName'] = GoogleTranslator(source='auto', target='en').translate(doc['windowName']).lower()
        doc.update(window_classifier.classify_window(doc['windowName']))
        print(doc)

        _id = '{}-{}-1'.format(_id, idx)
        response = elasticsearch_client.index_document(ES_INDEX, _id, doc)
        print(response)