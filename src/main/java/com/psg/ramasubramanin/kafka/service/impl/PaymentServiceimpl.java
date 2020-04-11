package com.psg.ramasubramanin.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.model.PaymentStatus;
import com.psg.ramasubramanin.kafka.producer.PaymentProducer;
import com.psg.ramasubramanin.kafka.service.PaymentService;

/**
 * @author rn51
 *
 */
@Service
public class PaymentServiceimpl implements PaymentService {
	private PaymentProducer paymentProducer;
	
	@Autowired
	public PaymentServiceimpl(PaymentProducer paymentProducer) {
		this.paymentProducer = paymentProducer;
	}
	
	@Override
	public void sendPaymentStatus(Order order) {
		
		PaymentStatus paymentStatus = new PaymentStatus();
		
		paymentStatus.setAmount(570.50);
		paymentStatus.setPaymentSuccessful(true);
		paymentStatus.setBank("Axis Bank");
		paymentStatus.setOrderId(order.getId());
		
		paymentProducer.sendPaymentStatus(paymentStatus);
	}
}
