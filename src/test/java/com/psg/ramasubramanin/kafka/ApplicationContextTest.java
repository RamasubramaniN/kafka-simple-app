package com.psg.ramasubramanin.kafka;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.psg.ramasubramanin.kafka.consumer.ConsumerConfiguration;


/**
 * @author rn5
 * To test application Context
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationContextTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void testApplicationContext() {
		ConsumerConfiguration consumerConfiguration = applicationContext.getBean(ConsumerConfiguration.class);
		Assert.assertNotNull(consumerConfiguration);
		System.out.println("No bean related failures. Application is up.");
	}
	
}
