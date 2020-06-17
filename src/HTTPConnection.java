import java.net.URL;
import java.net.URI;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.awt.Desktop;
import java.util.Vector;



public class HTTPConnection{
    private String type; 
    private String html;
    private Vector<String> csv;

    // コンストラクタ
    public HTTPConnection(String type)
    {
        this.type = type;
        csv = new Vector<>();
    }

    // [Abstract] 指定されたURLにGetリクエストを発行し、情報を取得するメソッド
    // [Detail] 第二引数に指定可能な文字列は以下
    //          "Shift-JIS": SHIFT-JISでファイルを読み込みます
    //          なお、nullを指定した場合はデフォルトの文字コードで読み込まれます.
    public void scraping( String url , String encoding)
    {
        try{
          URL  url_obj = new URL(url);
          try{
            System.out.println(Settings.EXECUTOR_NAME + " send a GET request to \"" + url + "\"" );
            HttpURLConnection http = (HttpURLConnection)url_obj.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // サーバーからのレスポンスを標準出力へ出す
            BufferedReader reader;
            if( encoding.equals("Shift-JIS") )
            {
                reader = new BufferedReader(new InputStreamReader(http.getInputStream(), "Shift-JIS"));
            }
            else
            {
                reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            }

            if( this.type.equals("html")  )
            {
                String xml = "", line = "";
                while((line = reader.readLine()) != null)  xml += line;
                this.html = xml;
            }
            else if( this.type.equals("csv") )
            {
                Vector<String> xml = new Vector<>();
                String  line = "";
                while((line = reader.readLine()) != null) xml.add(line);
                this.csv = xml;
            }
            else
            {
                System.err.println("Constructor Invalid Type Exception");
            }
            System.out.println("HTTP Connection was succeeded.");
            reader.close(); 
            return; 
          }catch(IOException e){
            System.err.println("IO Exception");
          }
        
        }catch(MalformedURLException e){
            System.err.println("[ERROR] Malformed URL");
        }
        return;
    }

    public void fillHttpResultInto(String[] str)
    {
        str[0] = this.html;
    }

    public Vector<String> getHttpResult()
    {
        return this.csv;
    }

    public static void browsing( String URL )
    {
        Desktop desktop = Desktop.getDesktop();
        System.out.println(desktop.toString());
        String uriString = URL;
        try {
            URI uri = new URI(uriString);
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            System.err.println("[ERROR] Malformed URL");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("[ERROR] Malformed URL");
            e.printStackTrace();
        }
    }

    public static String requesting_post(String json)
    {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        
        try {

            // [refactor!] URL名は適切なデータ定義モジュールに移動すること
            URL url = new URL("http://localhost:3000/odyssea");
            con = (HttpURLConnection) url.openConnection();
            // HTTPリクエストコード
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept-Language", "jp");
            // データがJSONであること、エンコードを指定する
            con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
            // POSTデータの長さを設定
            con.setRequestProperty("Content-Length", String.valueOf(json.length()));
            // リクエストのbodyにJSON文字列を書き込む
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(json);
            out.flush();
            con.connect();

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // テキストを取得する
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                // 通信が失敗した場合のレスポンスコードを表示
                System.out.println(status);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                // コネクションを切断
                con.disconnect();
            }
        }

        // Convert Json to Message
        String res_message = result.toString();
        res_message = res_message.split(",")[1];
        res_message = res_message.split(":")[1];
        res_message = res_message.replaceAll("\"", "");
        res_message = res_message.replaceAll("}", "");

        return res_message;
    } 
}

