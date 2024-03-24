from elasticsearch import Elasticsearch

# Initializes an instance of the ElasticSearchClient class with the provided Elasticsearch URL.
# The Elasticsearch client is instantiated with the specified URL, disabling certificate verification for simplicity.
# Creates an index in Elasticsearch with the specified "index_name" and "mapping_body".
# If the index already exists, the method ignores the creation request to avoid conflicts.

class ElasticSearchClient:
    
    def __init__(self, url: str):
        self.client = Elasticsearch(url, verify_certs=False)

    def create_index(self, index_name: str, mapping_body: dict):
        self.client.indices.create(index=index_name, body=mapping_body, ignore=400)

    def index_document(self, index_name: str, document_id: str, document_body: dict):
        return self.client.index(index=index_name, id=document_id, document=document_body)