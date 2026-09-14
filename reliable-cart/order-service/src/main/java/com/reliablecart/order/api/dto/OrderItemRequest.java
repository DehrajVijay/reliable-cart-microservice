
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * One line of an order as the client sends it.
 * A record is a short way to write a class that only carries data. Java
 * generates the constructor,
 * the getters, equals, hashCode and toString for you. The getter for productId
 * is called productId(),
 * with no get prefix. Records arrived in Java 16, so Java 17 has them.
 * 
 * Note the import is jakarta.validation, not javax.validation. Spring Boot 3
 * moved to the Jakarta namespace. If you copy an old example from the internet
 * and it uses javax, it will not compile.
 */
public record OrderItemRequest(

        @NotBlank(message = "productId must not be blank") String productId,

        @Positive(message = "quantity must be greater than zero") int quantity) {
}