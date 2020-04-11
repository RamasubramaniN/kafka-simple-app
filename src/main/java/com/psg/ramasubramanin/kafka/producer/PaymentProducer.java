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

import com.psg.ramasubramanin.kafka.model.PaymentStatus;

/**
 * @author rn51
 *
 */
@Component
public class PaymentProducer {
	
	private KafkaTemplate<Long, PaymentStatus> kafkaTemplate;
	private String topic;
	private final Logger logger = LoggerFactory.getLogger(PaymentProducer.class);
	
	@Autowired
	public PaymentProducer(@Qualifier("com.psg.ramasubramanin.kafka.producer.paymentStatusKafkaTemplate") 
		KafkaTemplate<Long, PaymentStatus> kafkaTemplate, 
		@Value("${kafka.topic.paymentStatus}") String topic) {
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	public void sendPaymentStatus(PaymentStatus paymentStatus) {
		ProducerRecord<Long, PaymentStatus> producerRecord = new ProducerRecord<Long, PaymentStatus>
				(topic, paymentStatus.getPaymentId(), paymentStatus);
		ListenableFuture<SendResult<Long, PaymentStatus>> listenableFuture =
				kafkaTemplate.send(producerRecord);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, PaymentStatus>>() {

			@Override
			public void onSuccess(SendResult<Long, PaymentStatus> result) {
				logger.debug("Message Sent. Order : {}", paymentStatus);
				RecordMetadata recordMetadata = result.getRecordMetadata();
				logger.debug("Payment Status Key={}, Partition={}, Offset={}", 
						paymentStatus.getPaymentId(), recordMetadata.partition(), recordMetadata.offset());
			}

			@Override
			public void onFailure(Throwable arg0) {
				logger.error("Message failed to send. Payment Status : {}", paymentStatus);
			}
		});
	}
}
