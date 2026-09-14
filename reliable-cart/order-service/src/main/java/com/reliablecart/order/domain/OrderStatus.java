
/**
 * The lifecycle of an order.
 * Only PENDING is used today. The rest arrive on Days 5 and 6.
 */
public enum OrderStatus {

    /** Saved, but nothing has been reserved or paid yet. */
    PENDING,

    /** Stock has been reserved for this order. */
    INVENTORY_RESERVED,

    /** Paid and accepted. This is the successful end state. */
    CONFIRMED,

    /** Refused before anything was reserved, for example no stock. */
    REJECTED,

    /** Payment failed. We are waiting for the reserved stock to be released. */
    CANCELLATION_PENDING,

    /** Cancelled and all reservations released. This is the failed end state. */
    CANCELLED
}