package com.psg.ramasubramanin.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psg.ramasubramanin.kafka.model.Order;
import com.psg.ramasubramanin.kafka.service.OrderService;

/**
 * @author rn51
 *
 */
@RestController
@RequestMapping(path="/orders/")
public class OrderController {
	
	private OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	/***
	 * POST : http://localhost:8080/FoodDeliveryApp/orders/ 
	 * Content-Type:application/json
	 	{
    		"name": "Mushroom Biriyani",
    		"quantity": 1,
    		"restaurantId": 1
		}
	 *
	 */
	@PostMapping(path = "/", consumes = "application/json")
	public void createOrder(@RequestBody Order order) {
		orderService.createOrder(order);
	}
}
