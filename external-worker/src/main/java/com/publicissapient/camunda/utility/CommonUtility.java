package com.publicissapient.camunda.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Class to serialize and deserialize.
 *
 * @author Hemant Vyas (hemant.vyas@publicissapient.com)
 */
public class CommonUtility {

    public static String convertToJSONString(Object object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO: Maybe use a custom exception
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertToJSONObject(String val, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(val, clazz);
        } catch (JsonProcessingException e) {
            // TODO: Maybe use a custom exception
            throw new RuntimeException(e);
        }
    }

}
