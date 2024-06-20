from pytest import fixture
from unittest.mock import MagicMock
from src.elasticsearch_client import ElasticSearchClient

# Unit tests for the ElasticSearchClient class.
# The tests examine just the successful scenarios.
# Author: Antonio Scardace
# Version: 1.0

@fixture(scope='module')
def es_client():
    es_mock = MagicMock()
    client = ElasticSearchClient('https://test-elasticsearch:9200')
    client.client = es_mock
    return client

def test_create_index(es_client):
    es_client.client.indices.create.return_value = { 'acknowledged': True }
    es_client.create_index('test_index', {'mappings': {'properties': {'name': {'type': 'text'}}}})
    es_client.client.indices.create.assert_called_once_with(
        index='test_index', body={'mappings': {'properties': {'name': {'type': 'text'}}}}, ignore=400)

def test_index_document(es_client):
    es_client.client.index.return_value = {'result': 'created'}
    document = {'name': 'Test Document'}
    es_client.index_document('test_index', '1', document)
    es_client.client.index.assert_called_once_with(index='test_index', id='1', document=document)

def test_get_document(es_client):
    es_client.client.get.return_value = { '_source': { 'name': 'Test Document' } }
    retrieved_document = es_client.get_document('test_index', '1')
    es_client.client.get.assert_called_once_with(index='test_index', id='1')
    assert retrieved_document['_source'] == { 'name': 'Test Document' }

def test_delete_document(es_client):
    es_client.client.delete.return_value = { 'result': 'deleted' }
    es_client.delete_document('test_index', '1')
    es_client.client.delete.assert_called_once_with(index='test_index', id='1')