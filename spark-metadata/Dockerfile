FROM jupyter/pyspark-notebook:spark-3.1.2
LABEL maintainer="Antonio Scardace"

WORKDIR /usr/app/
COPY . .

USER root
RUN chmod -R 755 /usr/app/

ARG PYTHONPATH
ENV PYTHONPATH /usr/app/src:$PYTHONPATH

RUN pip3 install --no-cache-dir -r requirements.txt
RUN pytest tests/

USER jovyan
CMD ["spark-submit", "--packages", "org.apache.spark:spark-sql-kafka-0-10_2.12:3.1.1", "--master", "local[4]", "src/spark_cluster.py"]