package com.psg.ramasubramanin.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.psg.ramasubramanin.kafka.model.PaymentStatus;

/**
 * @author rn51
 *
 */
@EnableKafka
@Component
public class OrderApplicationListener {
	
	private final Logger logger = LoggerFactory.getLogger(OrderApplicationListener.class);
	
	@KafkaListener(topics = "${kafka.topic.paymentStatus}", groupId = "OrderApplication",
			containerFactory = "com.psg.ramasubramanin.kafka.consumer.paymentStatusContainerFactory")
	public void listenToPaymentStatusMessages(PaymentStatus paymentStatus) {
		logger.debug("Payment Done. Payment Status : {}", paymentStatus);
		if(paymentStatus.isPaymentSuccessful())
			logger.debug("Payment Successful. Placed the order.");
		else
			logger.error("Payment failed. Send a cancellation message to restaurant.");
	}
}
