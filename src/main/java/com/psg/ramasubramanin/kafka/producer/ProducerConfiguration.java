package com.psg.ramasubramanin.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
@Configuration
public class ProducerConfiguration {
	
	private String bootstrapServerAddress;
	
	@Autowired
	public ProducerConfiguration(@Value("${kafka.bootstrapServerAddress}") String bootstrapServerAddress) {
		this.bootstrapServerAddress = bootstrapServerAddress;
	}
		
	@Bean(name="com.psg.ramasubramanin.kafka.producer.orderProducer") //Key - Long - Message Key - Order Id
	public ProducerFactory<Long, Order> orderProducerFactory() {
		Map<String, Object> orderProducerConfig = new HashMap<>();
		
		orderProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
		orderProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		orderProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(orderProducerConfig);
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.defaultProducer")//Message Key & Value both are strings.
	public ProducerFactory<String, String> defaultProducerFactory() {
		Map<String, Object> defaultProducerConfig = new HashMap<>();
		
		defaultProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
		defaultProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		defaultProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(defaultProducerConfig);
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.orderKafkaTemplate")
	public KafkaTemplate<Long, Order> orderKafkaTemplate() {
		return new KafkaTemplate<>(orderProducerFactory());
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.defaultKafkaTemplate")
	public KafkaTemplate<String, String> defaultKafkaTemplate() {
		return new KafkaTemplate<>(defaultProducerFactory());
	}
	
}
