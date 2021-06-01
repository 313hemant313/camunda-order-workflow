package com.publicissapient.camunda.model;

import lombok.Data;

import java.util.List;

/**
 * Route model.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
@Data
public class Route {
    String id;
    String source;
    List<String> transitLocations;
    String destination;
}
