package com.publicissapient.camunda.model;

import lombok.Data;

/**
 * Order model.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
@Data
public class Order {
    String orderId;
    String orderType;
    String destination;
    String emailId;
    Integer numberOfItems;
    boolean isOrderValid = false;
    boolean isOrderConfirmed = false;
}
