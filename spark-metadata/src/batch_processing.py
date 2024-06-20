import os

from pyspark.sql.dataframe import DataFrame

from elasticsearch_client import ES_MAPPING
from elasticsearch_client import ElasticSearchClient
from geolocation_service import GeolocationService
from window_title_classifier import WindowTitleClassifier
from date_utils import get_timestamp, get_delta_timestamps 

# Processes each batch of data and performs various operations on each row.
# (1) Classifies the application used based on heuristics.
# (2) Determines device geolocation based on IP address.
# (3) Estimates the time taken to write this record entry.
# (4) Adds an "@timestamp" field for further time series analysis.
# After some simple data processing, the document is indexed in Elasticsearch.

def process_batch(df: DataFrame, _id: int) -> None:
    elasticsearch_client = ElasticSearchClient(os.environ['ELASTICSEARCH_URL'])
    elasticsearch_client.create_index(os.environ['ELASTICSEARCH_INDEX'], ES_MAPPING)
    
    for idx, row in enumerate(df.collect()):

        doc = row.asDict()
        geolocation = GeolocationService(doc['ip_address'])
        window_classifier = WindowTitleClassifier(doc['window_title'])
        
        doc.update(window_classifier.classify_window_title())
        doc.update(geolocation.get_coords())
        doc.update(geolocation.get_country_code())
        doc.update(geolocation.get_country_name())
        doc.update(geolocation.get_state_name())
        doc.update(get_delta_timestamps(doc['timestamp_begin'], doc['timestamp_end']))
        doc.update(get_timestamp())

        _id = '{}-{}-{}'.format(_id, idx, os.getpid())
        print(doc)

        response = elasticsearch_client.index_document(os.environ['ELASTICSEARCH_INDEX'], _id, doc)
        assert response['_shards']['successful'] == 1