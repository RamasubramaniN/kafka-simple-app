package com.psg.ramasubramanin.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.psg.ramasubramanin.kafka.interceptor.OrderInterceptor;
import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.model.PaymentStatus;

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
		
		orderProducerConfig.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, OrderInterceptor.class.getName());
		
		//all - Let all InSyncReplicas acknowledge after receiving the record.
		//0 - None, 1 - Only the leader.
		orderProducerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
		orderProducerConfig.put(ProducerConfig.RETRIES_CONFIG, "3");//Failed Message Number of retries 3.
		
		//When number of retries 3. If partition x offset 5 is failed. 5 will be sent again before processing 6
		//This ordering is maintained by setting Number of in flights connection to 1.
		orderProducerConfig.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
	
		//Batch Config
		//orderProducerConfig.put(ProducerConfig.BATCH_SIZE_CONFIG, 100); --Batch Size - 100.
		//orderProducerConfig.put(ProducerConfig.LINGER_MS_CONFIG, "10"); --Wait for 10ms, accumulate
		//messages for each partitions & send it at one shot. If 100 records arrive for a partition before 10ms,
		//send the batch immediately.
		
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
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.paymentStatusProducer") 
	public ProducerFactory<Long, PaymentStatus> paymentStatusProducerFactory(){
		Map<String, Object> config = new HashMap<>();
		
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerAddress);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.orderKafkaTemplate")
	public KafkaTemplate<Long, Order> orderKafkaTemplate() {
		return new KafkaTemplate<>(orderProducerFactory());
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.defaultKafkaTemplate")
	public KafkaTemplate<String, String> defaultKafkaTemplate() {
		return new KafkaTemplate<>(defaultProducerFactory());
	}
	
	@Bean(name = "com.psg.ramasubramanin.kafka.producer.paymentStatusKafkaTemplate")
	public KafkaTemplate<Long, PaymentStatus> paymentStatusKafkaTemplate() {
		return new KafkaTemplate<>(paymentStatusProducerFactory());
	}
	
}
