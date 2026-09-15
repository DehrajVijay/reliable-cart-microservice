package com.reliablecart.order.application;

import com.reliablecart.order.api.dto.CreateOrderRequest;
import com.reliablecart.order.api.dto.OrderResponse;
import com.reliablecart.order.domain.OrderStatus;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * The application service
 * The order use cases.
 *
 * Storage is in memory today, so everything is lost on restart.
 * Day 3 replaces this map with a real database.
 * 
 * @Service tells Spring to create one instance of this class at startup and
 *          manage it. That managed instance is called a bean.
 * 
 *          We use ConcurrentHashMap rather than HashMap because a web server
 *          handles several requests at the same time on different threads. A
 *          plain HashMap can corrupt itself under concurrent writes.
 */
@Service
public class OrderApplicationService {

    private final Map<UUID, OrderResponse> orders = new ConcurrentHashMap<>();

    public OrderResponse createOrder(CreateOrderRequest request) {
        UUID orderId = UUID.randomUUID();

        OrderResponse order = new OrderResponse(
                orderId,
                request.customerId(),
                OrderStatus.PENDING);

        orders.put(orderId, order);
        return order;
    }

    public OrderResponse findById(UUID orderId) {
        OrderResponse order = orders.get(orderId);

        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }

        return order;
    }
}