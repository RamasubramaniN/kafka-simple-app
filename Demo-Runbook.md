Create Log directories
-------------------------

cd /Users/rn51/Personal/kafka_2.12-2.4.1/

mkdir data

cd /Users/rn51/Personal/kafka_2.12-2.4.1/data

mkdir kafka

cd /Users/rn51/Personal/kafka_2.12-2.4.1/data

mkdir zookeeper

Set the log files in config
-----------------------------

config/Zookeeper.properties
-----------------------------

dataDir=/Users/rn51/Personal/kafka_2.12-2.4.1/data/zookeeper

clientPort=2181

config/server.properties
-----------------------------

log.dirs=/Users/rn51/Personal/kafka_2.12-2.4.1/data/kafka

broker.id=0

num.partitions=3

log.retention.hours=168

zookeeper.connect=localhost:2181

To setup multiple brokers connecting to same set of zookeepers
----------------------------------------------------------------

https://stackoverflow.com/questions/50773941/start-multiple-brokers-in-kafka

Create server1.properties from server.properties config file and assign different broker ids to start multiple Kafka-Brokers. Use new config file while starting new Kafka Broker.

Replication factor  <= Number of brokers.

cd /Users/rn51/Personal/kafka_2.12-2.4.1/data/kafka