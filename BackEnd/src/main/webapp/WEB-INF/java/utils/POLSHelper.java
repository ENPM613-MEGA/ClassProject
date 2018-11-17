package utils;

import java.util.HashMap;
import java.util.Map;

public class POLSHelper {
    /**
     *  return a key-value map which contains failure information
     *  {
     *      "status" : "failure",
     *      "reason" : {reasson}
     *  }
     */
    public static Map<String, Object> failureReturnConstructor(String reason) {
        Map<String, Object> failureReturn = new HashMap<>();
        failureReturn.put("status", "failure");
        failureReturn.put("reason", reason);
        return failureReturn;
    }
}
