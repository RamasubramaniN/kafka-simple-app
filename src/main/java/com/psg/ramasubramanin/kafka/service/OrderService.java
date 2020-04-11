/**
 * 
 */
package com.psg.ramasubramanin.kafka.service;

import com.psg.ramasubramanin.kafka.model.Order;

/**
 * @author rn51
 *
 */
public interface OrderService {
	
	/**
	 * Create Order message & publish this message to Order Topck.
	 * @param order
	 */
	public void createOrder(Order order);
}
