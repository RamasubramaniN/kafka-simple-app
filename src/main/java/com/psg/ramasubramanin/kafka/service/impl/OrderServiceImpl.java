package com.psg.ramasubramanin.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.producer.OrderProducer;
import com.psg.ramasubramanin.kafka.service.OrderService;

/**
 * @author rn51
 *
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	private OrderProducer orderProducer;
	
	@Autowired
	public OrderServiceImpl(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}
	
	@Override
	public void createOrder(Order order) {
		orderProducer.sendMessage(order);
	}
}
