package com.publicissapient.camunda.worker;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

/**
 * Camunda worker class subscribing to BookSlot topic.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
@Slf4j
@Service
public class BookSlotWorker {

    @Value("${service.camunda.url}")
    String camundaBaseUrl;

    @PostConstruct
    public void subscribe() {
        // bootstrap the client
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(camundaBaseUrl + "/engine-rest")
                .asyncResponseTimeout(1000)
                .build();

        // subscribe to the topic
        client.subscribe("BookSlot")
                .handler((externalTask, externalTaskService) -> {

                    log.info("Order: '" + externalTask.getVariable("order") + "'...");

                    Date date = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 10);
                    date = cal.getTime();
                    String deliveryDate = date.toString();

                    VariableMap variables = Variables.createVariables()
                            .putValue("deliveryDate", deliveryDate);

                    externalTaskService.complete(externalTask, variables);

                    log.info("The External Task [BookSlotWorker]" + externalTask.getId() +
                            " has been completed!");

                }).open();
    }

}
