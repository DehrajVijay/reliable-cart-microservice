package com.reliablecart.order.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.reliablecart.order.api.dto.CreateOrderRequest;
import com.reliablecart.order.api.dto.OrderResponse;
import com.reliablecart.order.application.OrderApplicationService;
import com.reliablecart.order.application.OrderNotFoundException;
import com.reliablecart.order.domain.OrderStatus;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OrderApplicationService orderApplicationService;

  @Test
  void createsOrderAndReturns201() throws Exception {
    UUID orderId = UUID.randomUUID();
    given(orderApplicationService.createOrder(any()))
        .willReturn(new OrderResponse(orderId, "customer-1", OrderStatus.PENDING));

    String body = """
        {
          "customerId": "customer-1",
          "items": [ { "productId": "product-1", "quantity": 2 } ]
        }
        """;

    mockMvc.perform(post("/api/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/orders/" + orderId))
        .andExpect(jsonPath("$.status").value("PENDING"));
  }

  @Test
  void rejectsBlankCustomerId() throws Exception {
    String body = """
        {
          "customerId": "",
          "items": [ { "productId": "product-1", "quantity": 2 } ]
        }
        """;

    mockMvc.perform(post("/api/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isBadRequest());
  }

  @Test
  void rejectsZeroQuantity() throws Exception {
    String body = """
        {
          "customerId": "customer-1",
          "items": [ { "productId": "product-1", "quantity": 0 } ]
        }
        """;

    mockMvc.perform(post("/api/orders")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isBadRequest());
  }

  @Test
  void returns404ForUnknownOrder() throws Exception {
    UUID unknownId = UUID.randomUUID();
    given(orderApplicationService.findById(unknownId))
        .willThrow(new OrderNotFoundException(unknownId));

    mockMvc.perform(get("/api/orders/{orderId}", unknownId))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.title").value("Order not found"));
  }
}