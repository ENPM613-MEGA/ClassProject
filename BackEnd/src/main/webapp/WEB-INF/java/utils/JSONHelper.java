package utils;


import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class JSONHelper {

    /*
    * Transform a json format request to an JSON object
    * */
    public static JSONObject readJSONObject(HttpServletRequest request) {
        StringBuilder sBuilder = new StringBuilder();
        try(BufferedReader reader = request.getReader()) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                sBuilder.append(line);
            }
            return new JSONObject(sBuilder.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }


}
