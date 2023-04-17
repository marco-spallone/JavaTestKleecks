import difflib.Delta;
import difflib.DiffUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class Tasks {
    static JSONParser parser = new JSONParser();
    static List<FormatTwo> HTTP_302 = new ArrayList<>();
    static List<FormatTwo> HTTP_NO = new ArrayList<>();
    static List<FormatTwo> MIME_ALL = new ArrayList<>();
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    //TASK 1
    public static JSONObject convert(String body) throws IOException, ParseException {
        //DECOMMENTARE LA RIGA SEGUENTE PER AVERE IL METODO FUNZIONANTE SENZA REST SERVICE,
        //CHIAMANDO SEMPLICEMENTE LA FUNZIONE CONVERT NEL MAIN (COME RICHIESTO NELLA TASK 1) E ELIMINANDO IL PARAMETRO body
        //JSONObject object = (JSONObject) parser.parse(new FileReader("src/base-format.json"));
        JSONObject object = (JSONObject) parser.parse(body);
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
        //System.out.println(result);
        return result;
    }

    //TASK 5
    private static List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    //TASK 5
    public static void defineRules(File html1, File html2) throws IOException {
        ArrayList<String> rules = new ArrayList<>();
        final List<String> originalFileLines = fileToLines(html1);
        final List<String> revisedFileLines = fileToLines(html2);
        List<Delta> deltas = DiffUtils.diff(originalFileLines, revisedFileLines).getDeltas();
        for (Delta d : deltas) {
            String type = String.valueOf(d.getType());
            switch (type) {
                case "CHANGE":
                    String test = String.valueOf(d.getOriginal().getLines().get(0)).substring(String.valueOf(
                                    d.getOriginal().getLines().get(0)).lastIndexOf("id=") + 4,
                            String.valueOf(d.getOriginal().getLines().get(0)).lastIndexOf(" ") + 1);
                    String id = test.substring(0, test.length() - 1);
                    System.out.println(id);
                    rules.add("REMOVE (#" + id + ")");
                    rules.add("INSERT " + d.getRevised().getLines().get(0) + " AT ROW " + (d.getOriginal().getPosition() + 1));
                    break;
                case "INSERT":
                    rules.add("INSERT " + d.getRevised().getLines().get(0) + " AT ROW " + (d.getOriginal().getPosition() + 1));
                    break;
                default:
                    break;
            }
        }
        /*FileWriter myWriter = new FileWriter("src/rules.txt");
        for(String s:rules){
            myWriter.write(s+"\n");
        }
        myWriter.close();*/
    }

    public static void changeHTML(File rules, File html1, File html2) throws IOException {
        org.jsoup.nodes.Document document1 = Jsoup.parse(html1, "UTF-8");
        org.jsoup.nodes.Document document2 = Jsoup.parse(html2, "UTF-8");
        Scanner myReader = new Scanner(rules);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] words = data.split(" ");
            switch (words[0]) {
                case "INSERT":

                    break;
                case "REMOVE":
                    break;
                default:
                    break;
            }
        }
        myReader.close();
    }


    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException {
        //convert();

        //TASK 2
        /*get("/convert", (req, res) -> {
            return convert(req.body());
        });*/

        //TASK 4

        /*try {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/hibernate_db?useSSL=false";
            String username = "root";
            String password = "root";
            System.setProperty(driver, "");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();*/

        //DI SEGUITO IL CODICE PER CREARE LA TABELLA E PER INSERIRE I DATI
            /*statement.executeUpdate("CREATE TABLE task3 (user VARCHAR(1), year INTEGER, sales INTEGER)");
            System.out.println("Table created");*/
            /*String sql1 = "INSERT INTO task3 VALUES ('A', 2021, 500)";
            statement.executeUpdate(sql1);
            String sql2 = "INSERT INTO task3 VALUES ('B', 2021, 1000)";
            statement.executeUpdate(sql2);
            String sql3 = "INSERT INTO task3 VALUES ('C', 2022, 900)";
            statement.executeUpdate(sql3);
            String sql4 = "INSERT INTO task3 VALUES ('A', 2022, 1200)";
            statement.executeUpdate(sql4);
            String sql5 = "INSERT INTO task3 VALUES ('B', 2021, 600)";
            statement.executeUpdate(sql5);
            String sql6 = "INSERT INTO task3 VALUES ('A', 2022, 900)";
            statement.executeUpdate(sql6);
            String sql7 = "INSERT INTO task3 VALUES ('B', 2021, 500)";
            statement.executeUpdate(sql7);
            String sql8 = "INSERT INTO task3 VALUES ('C', 2021, 1000)";
            statement.executeUpdate(sql8);
            String sql9 = "INSERT INTO task3 VALUES ('C', 2022, 700)";
            statement.executeUpdate(sql9);
            String sql10 = "INSERT INTO task3 VALUES ('B', 2021, 500)";
            statement.executeUpdate(sql10);
            System.out.println("Inserted");*/

        //QUERY PER LA SELECT
            /*String query = "SELECT user, year, SUM(sales) AS sales,\n" +
                    "    LAG(SUM(sales)) OVER (PARTITION BY user ORDER BY year ) AS previous_year,\n" +
                    "    SUM(sales) - LAG(SUM(sales)) OVER (PARTITION BY user ORDER BY year ) AS difference_previous_year\n" +
                    "FROM hibernate_db.tabletask3 GROUP BY user, year;";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("user: " + resultSet.getString("user"));
                System.out.println("year: " + resultSet.getInt("year"));
                System.out.println("sales: " + resultSet.getInt("sales"));
                if (resultSet.getInt("previous_year") != 0) {
                    System.out.println("previous_year: " + resultSet.getInt("previous_year"));
                    System.out.println("difference_previous_year: " + resultSet.getInt("difference_previous_year"));
                } else {
                    System.out.println("previous_year: null");
                    System.out.println("difference_previous_year: null");
                }
            }
        }catch (SQLException ex) {
            System.out.println(ex);
        }
        */

        //TASK 5
        File file1 = new File("src/html1.html");
        File file2 = new File("src/html2.html");
        defineRules(file1, file2);

        //TASK 6
        /*File html1 = new File("src/html1.html");
        File html2 = new File("src/html2.html");
        File rules = new File("src/rules.txt");
        changeHTML(rules, html1, html2);*/
    }
}