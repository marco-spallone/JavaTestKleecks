import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConvertJson {
    static JSONParser parser = new JSONParser();
    static List<FormatTwo> HTTP_302 = new ArrayList<>();
    static List<FormatTwo> HTTP_NO = new ArrayList<>();
    static List<FormatTwo> MIME_ALL = new ArrayList<>();

    public static void convert() throws IOException, ParseException {
        JSONObject object = (JSONObject) parser.parse(new FileReader("src/base-format.json"));
        Set<String> keys = object.keySet();
        for (String s : keys) {
            FormatTwo http_302 = new FormatTwo();
            FormatTwo http_no = new FormatTwo();
            FormatTwo mime_all = new FormatTwo();
            http_302.setCrawlId(s);
            http_no.setCrawlId(s);
            mime_all.setCrawlId(s);
            JSONObject o = (JSONObject) object.get(s);
            JSONObject all = (JSONObject) o.get("ALL");
            http_302.setTotal((int) (long) (all.get("HTTP_302")));
            http_302.setTotalInt((int) (long) (all.get("INT-HTTP_302")));
            http_302.setTotalExt((int) (long) (all.get("EXT-HTTP_302")));
            http_no.setTotal((int) (long) (all.get("HTTP_NO")));
            http_no.setTotalInt((int) (long) (all.get("INT-HTTP_NO")));
            http_no.setTotalExt((int) (long) (all.get("EXT-HTTP_NO")));
            mime_all.setTotal((int) (long) (all.get("MIME_ALL")));
            mime_all.setTotalInt((int) (long) (all.get("INT-MIME_ALL")));
            mime_all.setTotalExt((int) (long) (all.get("EXT-MIME_ALL")));
            HTTP_302.add(http_302);
            HTTP_NO.add(http_no);
            MIME_ALL.add(mime_all);
        }
        JSONObject result = new JSONObject();
        result.put("HTTP_302", HTTP_302);
        result.put("HTTP_NO", HTTP_NO);
        result.put("MIME_ALL", MIME_ALL);
        System.out.println(result);
    }


    public static void main(String[] args) throws IOException, ParseException {
        convert();
    }
}