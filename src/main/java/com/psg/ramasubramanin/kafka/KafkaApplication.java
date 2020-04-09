package com.psg.ramasubramanin.kafka;

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
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaApplication.class, args);
        System.out.println("Kafka Application started...");
    }
}
