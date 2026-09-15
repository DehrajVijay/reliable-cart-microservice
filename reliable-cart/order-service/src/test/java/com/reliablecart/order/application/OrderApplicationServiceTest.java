package com.reliablecart.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.reliablecart.order.api.dto.CreateOrderRequest;
import com.reliablecart.order.api.dto.OrderItemRequest;
import com.reliablecart.order.api.dto.OrderResponse;
import com.reliablecart.order.domain.OrderStatus;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OrderApplicationServiceTest {

    private final OrderApplicationService service = new OrderApplicationService();

    @Test
    void createsOrderWithPendingStatus() {
        CreateOrderRequest request = new CreateOrderRequest(
                "customer-1",
                List.of(new OrderItemRequest("product-1", 2)));

        OrderResponse created = service.createOrder(request);

        assertThat(created.id()).isNotNull();
        assertThat(created.customerId()).isEqualTo("customer-1");
        assertThat(created.status()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    void findsAnOrderThatWasCreated() {
        CreateOrderRequest request = new CreateOrderRequest(
                "customer-1",
                List.of(new OrderItemRequest("product-1", 1)));

        OrderResponse created = service.createOrder(request);
        OrderResponse found = service.findById(created.id());

        assertThat(found).isEqualTo(created);
    }

    @Test
    void throwsWhenOrderDoesNotExist() {
        UUID unknownId = UUID.randomUUID();

        assertThatThrownBy(() -> service.findById(unknownId))
                .isInstanceOf(OrderNotFoundException.class);
    }
}