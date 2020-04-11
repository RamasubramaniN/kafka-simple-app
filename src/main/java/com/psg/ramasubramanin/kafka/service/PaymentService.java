package com.psg.ramasubramanin.kafka.service;

import com.psg.ramasubramanin.kafka.model.Order;

public interface PaymentService {
	
	/**
	 * Create PaymentStatus message & send to PaymentStatus Topic.
	 * @param order
	 */
	public void sendPaymentStatus(Order order);
}
