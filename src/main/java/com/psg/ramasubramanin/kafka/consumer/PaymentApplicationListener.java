package com.psg.ramasubramanin.kafka.consumer;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.service.PaymentService;

/**
 * @author rn51
 *
 */
@EnableKafka
@Component
public class PaymentApplicationListener {
	
	private PaymentService paymentService;
	
	@Autowired
	public PaymentApplicationListener(PaymentService paymentService) {
		 this.paymentService = paymentService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(PaymentApplicationListener.class);
	
	@KafkaListener(topics = "${kafka.topic.order}", groupId = "PaymentApplication",
			containerFactory = "com.psg.ramasubramanin.kafka.consumer.orderContainerFactory")
	public void listenToOrderMessages(Order order) {
		logger.debug("Processing payment. Recieved Order Message. Order = {}", order);
		try {
			TimeUnit.SECONDS.sleep(5L);
			logger.debug("Payment Done. Sending the status.");
			paymentService.sendPaymentStatus(order);
		} catch (InterruptedException e) {
			logger.error("Payment Done. Sending the status failed.");
		}
		
	}
}
