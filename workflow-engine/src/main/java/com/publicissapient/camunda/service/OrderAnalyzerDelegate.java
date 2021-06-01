package com.publicissapient.camunda.service;

import java.util.logging.Logger;

import com.publicissapient.camunda.model.Order;
import com.publicissapient.camunda.utility.CommonUtility;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


/**
 * This class should/can be replaced with a standalone microservice.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
public class OrderAnalyzerDelegate implements JavaDelegate {

    private final static Logger LOGGER = Logger.getLogger("OrderAnalyzerDelegate");

    public void execute(DelegateExecution execution) throws Exception {
        String businessKey = execution.getProcessBusinessKey();
        boolean isOrderValid = (Boolean) execution.getVariable("isOrderValidDecision");
        LOGGER.info("Process business Key '" + businessKey + "'...");
        LOGGER.info("Is Order Valid: '" + execution.getVariable("isOrderValidDecision") + "'...");

        if (!isOrderValid) {
            execution.setVariable("isOrderValid", false);
            execution.setVariable("error", "GENERAL_CONFIG_VALIDATION_FAILURE");
            throw new BpmnError("VALIDATION_FAILURE");
        }


        Order order = new Order();
        order.setOrderId((String) execution.getVariable("orderId"));
        order.setOrderType((String) execution.getVariable("orderType"));
        order.setDestination((String) execution.getVariable("destination"));
        order.setEmailId((String) execution.getVariable("emailId"));
        order.setNumberOfItems((Integer) execution.getVariable("numberOfItems"));
        order.setOrderValid(true);

        LOGGER.info(order.toString());
        String orderJson = CommonUtility.convertToJSONString(order);
        execution.setVariable("order", orderJson);

    }

}
