package com.psg.ramasubramanin.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
@Component
public class OrderProducer {
	
	private KafkaTemplate<Long, Order> kafkaTemplate;
	private String topicName;
	
	private Logger logger = LoggerFactory.getLogger(OrderProducer.class);
	
	@Autowired
	public OrderProducer(@Qualifier("com.psg.ramasubramanin.kafka.producer.orderKafkaTemplate")
			KafkaTemplate<Long, Order> kafkaTemplate, 
			@Value("${kafka.order.topicName}") String topicName) {
		this.kafkaTemplate = kafkaTemplate;
		this.topicName = topicName;
	}
	
	public boolean sendMessage(Order order) {
		ProducerRecord<Long, Order> producerRecord = //topicName, Key, Message
				new ProducerRecord<Long, Order>(topicName, order.getId(), order);
		ListenableFuture<SendResult<Long, Order>> future = kafkaTemplate.send(producerRecord);
		
		future.addCallback(new ListenableFutureCallback<SendResult<Long, Order>>() {

			@Override
			public void onSuccess(SendResult<Long, Order> result) {
				logger.debug("Message Sent. Order : {}", order);
				RecordMetadata recordMetadata = result.getRecordMetadata();
				logger.debug("Order Key={}, Partition={}, Offset={}", 
						order.getId(), recordMetadata.partition(), recordMetadata.offset());
			}

			@Override
			public void onFailure(Throwable arg0) {
				logger.debug("Message failed to send. Order : {}", order);
			}
		
		});
		
		return true;
	}
	
}
