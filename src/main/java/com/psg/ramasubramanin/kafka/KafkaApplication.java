package com.psg.ramasubramanin.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class KafkaApplication 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaApplication.class);
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaApplication.class, args);
        LOGGER.debug("Kafka Application started...");
    }
}
