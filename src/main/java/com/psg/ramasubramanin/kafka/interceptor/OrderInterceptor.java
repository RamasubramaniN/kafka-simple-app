package com.psg.ramasubramanin.kafka.interceptor;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
@Component
public class OrderInterceptor implements ProducerInterceptor<Long, Order> {
	
	private final Logger logger = LoggerFactory.getLogger(OrderInterceptor.class);
	
	//TODO : Calculate & Save orders per day...
	private int totalOrdersAckReceived;
	private int totalOrdersAttempted;
	

	public OrderInterceptor() {
		totalOrdersAckReceived = 0;
		totalOrdersAttempted = 0;
	}

	//The onAck method gets called after the broker acknowledges the record.
	@Override
	public void onAcknowledgement(RecordMetadata recordMetadata, Exception arg1) {
		totalOrdersAckReceived++;
		logger.debug("Interceptor After sending the message. Total Orders Placed :{}. "
				+ "Current Order Details. Topic:{}, Partition:{},", 
				", Offset:{}", totalOrdersAckReceived, recordMetadata.topic(), 
				recordMetadata.partition(), recordMetadata.offset());
	}

	@Override
	public void configure(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProducerRecord<Long, Order> onSend(ProducerRecord<Long, Order> producerRecord) {
		//If we want to change something in the message before it is sent, implement here.
		totalOrdersAttempted++;
		logger.debug("Interceptor before sending the message. Total Orders Attempted:{},"
				+ "Order : {}", totalOrdersAttempted, producerRecord.value());
		return producerRecord;
	}
}
