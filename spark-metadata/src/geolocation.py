import requests
import json

from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

# Gets the geographical coordinates (latitude and longitude) for a given IP address.
# The geolocation is done by using "geolocation-db.com" APIs.

class GeoLocationService:
    
    def __init__(self):
        self.session = requests.Session()
        self.session.mount('https://', HTTPAdapter(max_retries=Retry(connect=5, backoff_factor=0.5)))

    def get_geo_ip_coords(self, ip_address: str) -> dict:
        api_response = self.session.get(f'https://geolocation-db.com/jsonp/{ip_address}')
        geoip = json.loads(api_response.content.decode().split('(')[1][:-1])
        coords = f"{geoip['latitude']}, {geoip['longitude']}"
        return { 'location': coords }