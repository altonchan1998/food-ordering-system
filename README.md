### Kafka start up
-   docker-compose -f common.yaml -f zookeeper.yaml up
-   docker-compose -f common.yaml -f kafka_cluster.yaml up
-   docker-compose -f common.yaml -f init_kafka.yaml up