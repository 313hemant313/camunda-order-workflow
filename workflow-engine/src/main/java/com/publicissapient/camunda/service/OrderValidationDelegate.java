package com.publicissapient.camunda.service;

import com.publicissapient.camunda.model.Order;
import com.publicissapient.camunda.utility.CommonUtility;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

/**
 * This class should/can be replaced with a standalone microservice.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
public class OrderValidationDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("OrderValidationDelegate");

    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("Order: '" + execution.getVariable("order") + "' ...");
        LOGGER.info("Is Order Valid: '" + execution.getVariable("isOrderValidDecision") + "'...");
        Order order = CommonUtility.convertToJSONObject((String) execution.getVariable("order"), Order.class);
        boolean isOrderValid = (Boolean) execution.getVariable("isOrderValidDecision");

        if (!isOrderValid) {
            execution.setVariable("isOrderValid", false);
            execution.setVariable("error", String.format("[%s] CONFIG_VALIDATION_FAILURE", order.getOrderType()));
            throw new BpmnError("VALIDATION_FAILURE");
        }

        execution.setVariable("isOrderValid", isOrderValid);


    }

}
