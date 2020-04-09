package com.psg.ramasubramanin.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
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
	
	@KafkaListener(topics = "Order", groupId = "RestaurantApplication", 
			containerFactory = "com.psg.ramasubramanin.kafka.consumer.orderContainerFactory")
	public void listenToOrderMessages(Order order) {
		logger.debug("Restaurnt Processing Order Message. Order = {}", order);
	}
}
