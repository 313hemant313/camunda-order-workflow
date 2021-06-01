package com.publicissapient.camunda.worker;

import com.publicissapient.camunda.model.Route;
import com.publicissapient.camunda.utility.CommonUtility;
import com.publicissapient.camunda.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.UUID;


/**
 * Camunda worker class subscribing to FindRoute topic.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
@Slf4j
@Service
public class RouteWorker {

    @Value("${service.camunda.url}")
    String camundaBaseUrl;

    @PostConstruct
    public void subscribe() {

        // bootstrap the client
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(camundaBaseUrl+"/engine-rest")
                .asyncResponseTimeout(1000)
                .build();

        // subscribe to the topic
        client.subscribe("FindRoute")
                .handler((externalTask, externalTaskService) -> {

                    log.info("Order: '" + externalTask.getVariable("order") + "'...");
                    Order order = CommonUtility.convertToJSONObject((String) externalTask.getVariable("order"), Order.class);

                    Route route = new Route();
                    route.setId(UUID.randomUUID().toString());
                    route.setSource("LOC- A");
                    route.setTransitLocations(Arrays.asList("LOC- C", "LOC- K"));
                    route.setDestination(order.getDestination());

                    // Should be calculated or fetched from some other service.
                    Integer noOfBorder = 4;

                    String routeJson = CommonUtility.convertToJSONString(route);
                    VariableMap variables = Variables.createVariables()
                            .putValue("route", routeJson)
                            .putValue("noOfBorder", noOfBorder);

                    log.info("Number of Border: '" + noOfBorder + "'...");

                    if ("Ambient".equalsIgnoreCase(order.getOrderType())) {
                        Integer transitLocationCount = route.getTransitLocations().size();
                        variables.putValue("transitLocationCount", transitLocationCount);
                        log.info("Number of Transit Location: '" + transitLocationCount + "'...");
                    }

                    externalTaskService.complete(externalTask, variables);

                    log.info("The External Task [RouteWorker]" + externalTask.getId() +
                            " has been completed!");

                }).open();
    }

}
