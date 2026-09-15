package com.reliablecart.order.api.dto;

import com.reliablecart.order.domain.OrderStatus;
import java.util.UUID;

/**
 * The response DTO
 * What we send back to the client for a single order.
 * We use a UUID for the id rather than a counter. A counter would tell
 * competitors how many orders you take per day, and it also stops two services
 * generating ids independently.
 */
public record OrderResponse(UUID id,String customerId,OrderStatus status){}