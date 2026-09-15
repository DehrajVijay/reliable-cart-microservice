package com.reliablecart.order.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * The body of POST /api/orders.
 * Look closely at List<@Valid OrderItemRequest>. The @NotEmpty only checks that
 * the list has something in it. Without the @Valid inside the angle brackets,
 * the rules on each item would be silently ignored, and an order with a blank
 * product id would sail straight through. This is a very common and very quiet
 * bug.
 */
public record CreateOrderRequest(

@NotBlank(message="customerId must not be blank")String customerId,

@NotEmpty(message="items must contain at least one line")List<@Valid OrderItemRequest>items){}