package com.reliablecart.order.api;

import com.reliablecart.order.api.dto.CreateOrderRequest;
import com.reliablecart.order.api.dto.OrderResponse;
import com.reliablecart.order.application.OrderApplicationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request) {

        OrderResponse created = orderApplicationService.createOrder(request);
        URI location = URI.create("/api/orders/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{orderId}")
    public OrderResponse findById(@PathVariable UUID orderId) {
        return orderApplicationService.findById(orderId);
    }
}