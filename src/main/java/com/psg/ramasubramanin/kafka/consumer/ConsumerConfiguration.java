package com.psg.ramasubramanin.kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
@Component
public class ConsumerConfiguration {
	
	private String bootstrapServerAddress;
	
	@Autowired
	public ConsumerConfiguration(@Value("${kafka.bootstrapServerAddress}") String bootstrapServerAddress) {
		this.bootstrapServerAddress = bootstrapServerAddress;
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.consumer.defaultConsumerFactory")
	public ConsumerFactory<String, String> defaultConsumerFactory() {
		Map<String, Object> consumerConfig =  new HashMap<String, Object>();
		
		consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
		consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		
		return new DefaultKafkaConsumerFactory<>(consumerConfig);
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.consumer.defaultContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> defaultContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> defaultContainerFactory =
				new ConcurrentKafkaListenerContainerFactory<>();
		defaultContainerFactory.setConcurrency(3); //Number of Consumers to create under this container
		defaultContainerFactory.setConsumerFactory(defaultConsumerFactory());
		return defaultContainerFactory;
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.consumer.orderConsumerFactory")
	public ConsumerFactory<Long, Order> orderConsumerFactory() {
		Map<String, Object> consumerConfig = new HashMap<>();
		
		consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
		consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		
		return new DefaultKafkaConsumerFactory<>(consumerConfig);
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.consumer.orderContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Long, Order> orderContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Long, Order> orderContainerFactory =
				new ConcurrentKafkaListenerContainerFactory<>();
		orderContainerFactory.setConcurrency(3);
		orderContainerFactory.setConsumerFactory(orderConsumerFactory());
		
		return orderContainerFactory;
	}
}
