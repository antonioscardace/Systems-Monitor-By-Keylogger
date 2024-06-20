import requests
import json

from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

# Estimates the geographic position of a given IP address.
# The geolocation is done by using "geolocation-db.com" APIs.
# The methods provide the Country Name, the Country Code, the State, and the Coordinates (latitude and longitude).
# Author: Antonio Scardace
# Version: 1.0

class GeolocationService:
    
    API_URL = 'https://geolocation-db.com/jsonp/'

    def __init__(self, ip_address: str):
        self.session = requests.Session()
        self.session.mount('https://', HTTPAdapter(max_retries=Retry(connect=5, backoff_factor=0.5)))
        self.geo_info = self._fetch_ip_geo_info(ip_address)

    def _fetch_ip_geo_info(self, ip_address: str) -> dict:
        response = self.session.get(self.API_URL + ip_address)
        return json.loads(response.content.decode().split('(')[1][:-1])
    
    def get_country_code(self) -> dict:
        return { 'country_code': self.geo_info['country_code'] }

    def get_country_name(self) -> dict:
        return { 'country_name': self.geo_info['country_name'] }
    
    def get_state_name(self) -> dict:
        return { 'state_name': self.geo_info['state'] }

    def get_coords(self) -> dict:
        coords = '{}, {}'.format(self.geo_info['latitude'], self.geo_info['longitude'])
        return { 'location': coords }