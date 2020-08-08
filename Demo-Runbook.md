Create Log directories
-------------------------

```
cd /Users/rn51/Personal/kafka_2.12-2.4.1/

mkdir data

cd /Users/rn51/Personal/kafka_2.12-2.4.1/data

mkdir kafka

cd /Users/rn51/Personal/kafka_2.12-2.4.1/data

mkdir zookeeper
```

Set the log files in config
-----------------------------

config/Zookeeper.properties
-----------------------------

```
dataDir=/Users/rn51/Personal/kafka_2.12-2.4.1/data/zookeeper

clientPort=2181
```

config/server.properties
-----------------------------

```
log.dirs=/Users/rn51/Personal/kafka_2.12-2.4.1/data/kafka

broker.id=0

num.partitions=3

log.retention.hours=168

zookeeper.connect=localhost:2181
```

Kafka broker has to register to Zookeeper. If you have more than one zookeeper, enter comma separated values.

To setup multiple brokers connecting to same set of zookeepers
----------------------------------------------------------------

https://stackoverflow.com/questions/50773941/start-multiple-brokers-in-kafka

Create server1.properties from server.properties config file and assign different broker ids to start multiple Kafka-Brokers. Use new config file while starting new Kafka Broker.

Replication factor  <= Number of brokers.

Prerequisite
---------------

1. Stop all running brokers & zookeepers.

2. If you would like to start from scratch, delete log files --> This action will delete all existing topics & messages.

```
rm -rf /Users/rn51/Personal/kafka_2.12-2.4.1/data/kafka/

rm -rf /Users/rn51/Personal/kafka_2.12-2.4.1/data/zookeeper/
```

Start Zookeeper
-----------------

```
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin/

zookeeper-server-start ../config/zookeeper.properties
```

Zookeeper will start at localhost:2181

Start Kafka
------------

```
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin/

kafka-server-start ../config/server.properties
```
Kafka/Broker will start at port 9092 & it will register itself with Zookeeper.

Create Topics
--------------

Create 2 Topics. Topics list maintained by Zookeeper. So, pass zookeeper info.

1. Order

2. PaymentStatus

```
cd /Users/rn51/Personal/kafka_2.12-2.4.1/bin

kafka-topics --zookeeper 127.0.0.1:2181 --topic Order --create --partitions 3 --replication-factor 1

kafka-topics --zookeeper 127.0.0.1:2181 --topic PaymentStatus --create --partitions 3 --replication-factor 1
```

Topics Info - CLI
-------------------

1. To get all topics. Topics list maintained by Zookeeper. So, pass zookeeper info.
```
kafka-topics --zookeeper 127.0.0.1:2181 --list
```

2. To see partitions, leaders & replicas.
```
kafka-topics --zookeeper 127.0.0.1:2181 --describe
```

3. To see partitions, leaders & replicas for a specific topic.
```
kafka-topics --zookeeper 127.0.0.1:2181 --describe --topic Order
```

Create Dummy Producers & Consumers
------------------------------------

Consumer & Producer need to know bootstrap server (Bootstrap server == Kafka Broker/Kafka)

Create a Consumer. 
```
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic Order --from-beginning
```

Create a producer. 

Send some text messages. Consumer will immediately consume.

```
kafka-console-producer --broker-list 127.0.0.1:9092 --topic Order

>Hello
>My First Message
>Happy Learning Kafka
```

Stop the Producer & Consumer. We will create Producers, Consumer Groups & Consumers from the program. Please Refer Readme.md
