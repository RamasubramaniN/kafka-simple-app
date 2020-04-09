# kafka-basics
Exploring Kafka

Install Kafka locally. Do following steps using Kafka CLI.


Start Zookeeper
----------------
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin
zookeeper-server-start ../config/zookeeper.properties

Start Kafka Server
-------------------
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin
kafka-server-start ../config/server.properties

Create Topic
-------------
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin
kafka-topics --zookeeper 127.0.0.1:2181 --topic Order --create --partitions 3 --replication-factor 1