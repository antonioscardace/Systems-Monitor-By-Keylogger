from pytest import fixture
from unittest.mock import patch
from src.geolocation_service import GeolocationService

# Unit tests for the GeolocationService class.
# The tests examine just the successful scenarios.
# Author: Antonio Scardace
# Version: 1.0

@fixture
def mock_response():
    return '''callback({
        "country_code": "US",
        "country_name": "United States",
        "city": null,
        "postal": null,
        "latitude": 37.751,
        "longitude": -97.822,
        "IPv4": "8.8.8.8",
        "state": "California"
    })'''

@patch('geolocation_service.requests.Session.get')
def test_get_country_code(mock_get, mock_response):
    mock_get.return_value.content.decode.return_value = mock_response
    geo_service = GeolocationService('8.8.8.8')
    assert geo_service.get_country_code() == { 'country_code': 'US' }

@patch('geolocation_service.requests.Session.get')
def test_get_country_name(mock_get, mock_response):
    mock_get.return_value.content.decode.return_value = mock_response
    geo_service = GeolocationService('8.8.8.8')
    assert geo_service.get_country_name() == { 'country_name': 'United States' }

@patch('geolocation_service.requests.Session.get')
def test_get_state_name(mock_get, mock_response):
    mock_get.return_value.content.decode.return_value = mock_response
    geo_service = GeolocationService('8.8.8.8')
    assert geo_service.get_state_name() == { 'state_name': 'California' }

@patch('geolocation_service.requests.Session.get')
def test_get_coords(mock_get, mock_response):
    mock_get.return_value.content.decode.return_value = mock_response
    geo_service = GeolocationService('8.8.8.8')
    assert geo_service.get_coords() == { 'location': '37.751, -97.822' }