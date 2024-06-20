from os import environ
from typing import Any
from elasticsearch import Elasticsearch

# Use this mapping if you need to save GeoPoints in ElasticSearch.

ES_MAPPING = {
    "mappings": {
        "properties": {
            "location": {
                "type": "geo_point"
            }
        }
    }
}

# A client for interacting with Elasticsearch, disabling certificate verification for simplicity.
# Provides methods for creating an index and working with its documents.
# Author: Antonio Scardace
# Version: 1.0

class ElasticSearchClient:
    
    def __init__(self, url: str):
        self.client = Elasticsearch(url, verify_certs=False)

    # Creates an index in Elasticsearch.
    # If the index already exists (error 400), the method ignores the creation request to avoid conflicts.

    def create_index(self, index_name: str, mapping_body: dict) -> None:
        self.client.indices.create(index=index_name, body=mapping_body, ignore=400)

    def get_document(self, index_name: str, document_id: str) -> Any:
        return self.client.get(index=index_name, id=document_id)

    def index_document(self, index_name: str, document_id: str, document_body: dict) -> Any:
        return self.client.index(index=index_name, id=document_id, document=document_body)

    def delete_document(self, index_name: str, document_id: str) -> Any:
        return self.client.delete(index=index_name, id=document_id)