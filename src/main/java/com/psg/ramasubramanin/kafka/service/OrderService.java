package com.psg.ramasubramanin.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.producer.OrderProducer;

/**
 * @author rn51
 *
 */
@Service
public class OrderService {
	
	private OrderProducer orderProducer;
	
	@Autowired
	public OrderService(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}
	
	public void createOrder(Order order) {
		orderProducer.sendMessage(order);
	}
}
