import os

from pyspark.sql import SparkSession
from pyspark.sql import types as tp
from pyspark.sql.functions import from_json, col
from processing import process_batch

# Extracts batches from Kafka and processes them.
# Author: Antonio Scardace

APP_NAME = 'SystemsMonitor'
KAFKA_TOPIC = 'metadata'

# Defines the schema of the data to be read from the Kafka topic.

def get_log_schema() -> tp.StructType:
    return tp.StructType([
        tp.StructField(name='uuid', dataType=tp.StringType()),
        tp.StructField(name='windowName', dataType=tp.StringType()),
        tp.StructField(name='ipAddress', dataType=tp.StringType()),
        tp.StructField(name='timestampBegin', dataType=tp.TimestampType()),
        tp.StructField(name='timestampEnd', dataType=tp.TimestampType()),
        tp.StructField(name='@timestamp', dataType=tp.TimestampType())
    ])

# Initializes the Spark session.

spark = SparkSession.builder.appName(APP_NAME).getOrCreate()
spark.sparkContext.setLogLevel('ERROR')

# Reads the data stream from the specified Kafka topic.
# Converts the data from string to structure defined by the schema.

df = spark.readStream \
    .format('kafka') \
    .option('kafka.bootstrap.servers', os.environ['KAFKA_URL']) \
    .option('subscribe', KAFKA_TOPIC) \
    .load() \
    .select(from_json(col("value").cast("string"), get_log_schema()).alias("data")) \
    .select("data.*")

# Processes each batch of data using the "process_batch" function.
# And waits for the stream processing to terminate.

df.writeStream \
    .foreachBatch(process_batch) \
    .start() \
    .awaitTermination()