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
public class PaymentApplicationListener {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentApplicationListener.class);
	
	@KafkaListener(topics = "Order Topic2", groupId = "PaymentApplication",
			containerFactory = "com.psg.ramasubramanin.kafka.consumer.orderContainerFactory")
	public void listenToOrderMessages(Order order) {
		logger.debug("Processing payment. Recieved Order Message. Order = {}", order);
	}
}
