from re import match
from datetime import datetime, timezone
from src.date_utils import get_timestamp, get_delta_timestamps

# Unit tests for the date helper functions.
# The tests examine just the successful scenarios.
# Author: Antonio Scardace
# Version: 1.0

def test_get_timestamp():
    timestamp = get_timestamp()
    assert isinstance(timestamp, dict)
    assert '@timestamp' in timestamp
    assert isinstance(timestamp['@timestamp'], str)
    assert match(r'\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3,}Z$', timestamp['@timestamp']) is not None
    
def test_get_delta_timestamps():
    timestamp_begin = datetime(2024, 1, 1, 12, 0, 0, tzinfo=timezone.utc)
    timestamp_end = datetime(2024, 1, 1, 12, 30, 0, tzinfo=timezone.utc)
    delta_secs = get_delta_timestamps(timestamp_begin, timestamp_end)

    assert isinstance(delta_secs, dict)
    assert 'delta_secs' in delta_secs
    assert delta_secs['delta_secs'] == 1800