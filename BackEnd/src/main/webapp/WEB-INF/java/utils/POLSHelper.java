package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String getCurDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date()).toString();
    }
}
