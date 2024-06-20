import os

from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, StringType, TimestampType
from pyspark.sql.functions import from_json, col

from batch_processing import process_batch

# Defines the schema of the data to be read from the Kafka topic.
# Author: Antonio Scardace
# Version: 1.0

def get_record_schema() -> StructType:
    return StructType([
        StructField(name='uuid',            dataType=StringType()),
        StructField(name='window_title',    dataType=StringType()),
        StructField(name='ip_address',      dataType=StringType()),
        StructField(name='timestamp_begin', dataType=TimestampType()),
        StructField(name='timestamp_end',   dataType=TimestampType())
    ])

# Initializes the Spark session.

APP_NAME = 'SystemsMonitor-MetadataAnalysis'
spark = SparkSession.builder.appName(APP_NAME).getOrCreate()
spark.sparkContext.setLogLevel('ERROR')

# Extracts the data stream from the specified Kafka topic.
# Converts the data from string to structure defined by the schema.

df = spark.readStream \
    .format('kafka') \
    .option('kafka.bootstrap.servers', os.environ['KAFKA_URL']) \
    .option('subscribe', os.environ['KAFKA_TOPIC']) \
    .load() \
    .select(from_json(col("value").cast("string"), get_record_schema()).alias("data")) \
    .select("data.*")

# Processes each batch of data using the defined "process_batch" function
# and waits for the stream processing to terminate.

df.writeStream \
    .foreachBatch(process_batch) \
    .start() \
    .awaitTermination()