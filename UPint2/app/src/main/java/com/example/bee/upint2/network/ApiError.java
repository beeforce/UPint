package com.example.bee.upint2.network;

import java.util.List;
import java.util.Map;

/**
 * Created by Bee on 2/1/2018.
 */

public class ApiError {

    String message;
    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
