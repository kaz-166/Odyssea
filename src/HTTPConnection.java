import java.net.URL;
import java.net.URI;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.io.IOException;
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
}

