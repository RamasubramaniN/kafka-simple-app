# kafka-Setup
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
kafka-topics --zookeeper 127.0.0.1:2181 --topic PaymentStatus --create --partitions 3 --replication-factor 1


Post an order
-------------------
POST : http://localhost:8080/FoodDeliveryApp/orders/ 

Content-Type:application/json

Request Body :
{
	"name": "Mushroom Biriyani",
	"quantity": 1,
	"restaurantId": 1
}

This is a simple app. In real world, all the consumers will be part of different microservices.
To simplify things, all services/consumers are in single microservice in this example.
1. Customer Posts an order via REST API. 
2. This order message is sent to Kafka Topic Order, This topic has 3 partitions.
3. Restaurant & Payment Applications listen to this topic. Each application/consumerGroup has 3 consumers. Every consumer
reads from one partition.
4. Payment Application processes the payment. It creates a Payment Status message and published this to PaymentStatus Topic. This topic has 3 partitions.
5. Order Application reads PaymentStatus message & completes the order if payment is successful. If payment fails it sends to order cancellation message to Order Microservice (This part yet to implement).


Some Logs - Each Consumer Group has 3 consumers.
-------------------------------------------------------
2020-04-11 09:50:10.593  INFO 498 --- [ntainer#2-2-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-3, groupId=RestaurantApplication] Setting newly assigned partitions: Order-2
2020-04-11 09:50:10.595  INFO 498 --- [ntainer#2-1-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-2, groupId=RestaurantApplication] Setting newly assigned partitions: Order-1
2020-04-11 09:50:10.595  INFO 498 --- [ntainer#2-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-1, groupId=RestaurantApplication] Setting newly assigned partitions: Order-0


2020-04-11 09:54:31.030  INFO 2278 --- [ntainer#1-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-7, groupId=PaymentApplication] Setting newly assigned partitions: Order-0
2020-04-11 09:54:31.028  INFO 2278 --- [ntainer#1-1-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-8, groupId=PaymentApplication] Setting newly assigned partitions: Order-1
2020-04-11 09:54:31.028  INFO 2278 --- [ntainer#1-2-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-9, groupId=PaymentApplication] Setting newly assigned partitions: Order-2

2020-04-11 09:54:30.957  INFO 2278 --- [ntainer#0-0-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-4, groupId=OrderApplication] Setting newly assigned partitions: PaymentStatus-0
2020-04-11 09:54:30.957  INFO 2278 --- [ntainer#0-2-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-6, groupId=OrderApplication] Setting newly assigned partitions: PaymentStatus-2
2020-04-11 09:54:30.961  INFO 2278 --- [ntainer#0-1-C-1] o.a.k.c.c.internals.ConsumerCoordinator  : [Consumer clientId=consumer-5, groupId=OrderApplication] Setting newly assigned partitions: PaymentStatus-1


2020-04-11 10:02:15.059 DEBUG 2278 --- [ad | producer-1] c.p.r.kafka.producer.OrderProducer       : Message Sent. Order : com.psg.ramasubramanin.kafka.model.Order@2dda33c4[id=2,name=Paneer Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:15.059 DEBUG 2278 --- [ad | producer-1] c.p.r.kafka.producer.OrderProducer       : Order Key=2, Partition=2, Offset=1
2020-04-11 10:02:15.060 DEBUG 2278 --- [ntainer#1-2-C-1] c.p.r.k.c.PaymentApplicationListener     : Processing payment. Recieved Order Message. Order = com.psg.ramasubramanin.kafka.model.Order@7421fe11[id=2,name=Paneer Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:15.060 DEBUG 2278 --- [ntainer#2-2-C-1] c.p.r.k.c.ResturantApplicationListener   : Restaurnt Processing Order Message. Order = com.psg.ramasubramanin.kafka.model.Order@5cd34d23[id=2,name=Paneer Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:17.514 DEBUG 2278 --- [ad | producer-1] c.p.r.kafka.producer.OrderProducer       : Message Sent. Order : com.psg.ramasubramanin.kafka.model.Order@5a752c96[id=5,name=Mushroom Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:17.515 DEBUG 2278 --- [ad | producer-1] c.p.r.kafka.producer.OrderProducer       : Order Key=5, Partition=0, Offset=0
2020-04-11 10:02:17.516 DEBUG 2278 --- [ntainer#1-0-C-1] c.p.r.k.c.PaymentApplicationListener     : Processing payment. Recieved Order Message. Order = com.psg.ramasubramanin.kafka.model.Order@3a982d1b[id=5,name=Mushroom Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:17.516 DEBUG 2278 --- [ntainer#2-0-C-1] c.p.r.k.c.ResturantApplicationListener   : Restaurnt Processing Order Message. Order = com.psg.ramasubramanin.kafka.model.Order@56fb0ce4[id=5,name=Mushroom Biriyani,quantity=1,restaurantId=1]
2020-04-11 10:02:20.062 DEBUG 2278 --- [ntainer#1-2-C-1] c.p.r.k.c.PaymentApplicationListener     : Payment Done. Sending the status.
2020-04-11 10:02:20.065 DEBUG 2278 --- [ad | producer-2] c.p.r.kafka.producer.PaymentProducer     : Message Sent. Order : com.psg.ramasubramanin.kafka.model.PaymentStatus@2bde631a
2020-04-11 10:02:20.066 DEBUG 2278 --- [ad | producer-2] c.p.r.kafka.producer.PaymentProducer     : Payment Status Key=2, Partition=2, Offset=1
2020-04-11 10:02:20.066 DEBUG 2278 --- [ntainer#0-2-C-1] c.p.r.k.c.OrderApplicationListener       : Payment Done. Payment Status : com.psg.ramasubramanin.kafka.model.PaymentStatus@12a9a98f
2020-04-11 10:02:22.519 DEBUG 2278 --- [ntainer#1-0-C-1] c.p.r.k.c.PaymentApplicationListener     : Payment Done. Sending the status.
2020-04-11 10:02:22.522 DEBUG 2278 --- [ad | producer-2] c.p.r.kafka.producer.PaymentProducer     : Message Sent. Order : com.psg.ramasubramanin.kafka.model.PaymentStatus@6ee4656f
2020-04-11 10:02:22.522 DEBUG 2278 --- [ad | producer-2] c.p.r.kafka.producer.PaymentProducer     : Payment Status Key=4, Partition=1, Offset=0
2020-04-11 10:02:22.524 DEBUG 2278 --- [ntainer#0-1-C-1] c.p.r.k.c.OrderApplicationListener       : Payment Done. Payment Status : com.psg.ramasubramanin.kafka.model.PaymentStatus@6cf8d149
2020-04-11 10:02:22.554 DEBUG 2278 --- [ntainer#0-1-C-1] c.p.r.k.c.OrderApplicationListener       : Payment Successful. Placed the order
