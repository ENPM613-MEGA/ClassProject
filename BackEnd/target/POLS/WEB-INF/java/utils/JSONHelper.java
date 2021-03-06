package utils;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JSONHelper {

    private static final String TYPE = "application/json";
    private static final String ALLOWED = "Access-Control-Allow-Origin";
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

    // write a JSONArray to http response
    public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(TYPE);
        response.addHeader(ALLOWED, "*");
        out.print(array);
        out.close();
    }

    // write a JSONObject to http response
    public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(TYPE);
        response.addHeader(ALLOWED, "*");
        out.print(obj);
        out.close();
    }


}
