from datetime import datetime, timezone

# Generates the "@timestamp" field to be added to the doc.
# It will be useful to allow further time series analysis.

def get_timestamp() -> dict:
    date_format = '%Y-%m-%dT%H:%M:%S.%fZ'
    return { '@timestamp': datetime.now(timezone.utc).strftime(date_format) }

# Calculates the difference between two timestamps in seconds.
# It will be useful to understand how much time the user spends on each window category.

def get_delta_timestamps(timestamp_begin: datetime, timestamp_end: datetime) -> dict:
    tdelta = timestamp_end - timestamp_begin
    tdelta_secs = tdelta.total_seconds()
    return { 'delta_secs': tdelta_secs }