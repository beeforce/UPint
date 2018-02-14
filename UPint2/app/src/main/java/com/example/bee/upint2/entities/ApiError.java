package com.example.bee.upint2.entities;

import java.util.List;
import java.util.Map;

/**
 * Created by Bee on 2/5/2018.
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

