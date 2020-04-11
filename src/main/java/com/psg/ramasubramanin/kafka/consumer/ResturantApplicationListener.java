package com.psg.ramasubramanin.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
@EnableKafka
@Component
public class ResturantApplicationListener {
	
	private final Logger logger = LoggerFactory.getLogger(ResturantApplicationListener.class);
	
	@KafkaListener(topics = "${kafka.topic.order}", groupId = "RestaurantApplication", 
			containerFactory = "com.psg.ramasubramanin.kafka.consumer.orderContainerFactory")
	public void listenToOrderMessages(Order order, Acknowledgment acknowledgment, 
			@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, 
			@Header(KafkaHeaders.OFFSET) int offset) { 
		//headers are optional - In case if you want to know about current partition & offset.
		logger.debug("Restaurnt Processing Order Message. Order = {}", order);
		logger.debug("Consumer. Topic : {}, Partition : {}, Offset  {}", "Order", partition, offset);
		acknowledgment.acknowledge();
	}
}
