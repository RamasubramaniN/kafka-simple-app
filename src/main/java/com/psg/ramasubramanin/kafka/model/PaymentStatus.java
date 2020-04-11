package com.psg.ramasubramanin.kafka.model;

import java.io.Serializable;

/**
 * @author rn51
 *
 */
public class PaymentStatus implements Serializable {

	private static Long sequence = Long.valueOf(0);
	private Long paymentId;
	private String bank;
	private Double amount;
	private boolean isPaymentSuccessful;
	private Long orderId;
	
	public PaymentStatus() {
		paymentId = sequence++;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isPaymentSuccessful() {
		return isPaymentSuccessful;
	}

	public void setPaymentSuccessful(boolean isPaymentSuccessful) {
		this.isPaymentSuccessful = isPaymentSuccessful;
	}

}
