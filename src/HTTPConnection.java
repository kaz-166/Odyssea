import java.net.URL;
import java.net.URI;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.io.IOException;
import java.awt.Desktop;


public class HTTPConnection {
    
    public static String scraping( String url )
    {
        try{
          URL  url_obj = new URL(url);
          try{
            HttpURLConnection http = (HttpURLConnection)url_obj.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // サーバーからのレスポンスを標準出力へ出す
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String xml = "", line = "";
            while((line = reader.readLine()) != null)
            {
                xml += line;
            }
            reader.close(); 
            return xml; 
          }catch(IOException e){
            System.err.println("IO Exception");
          }
        
        }catch(MalformedURLException e){
            System.err.println("Malformed URL");

        }
        return "";
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
            System.err.println("Malformed URL");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Malformed URL");
            e.printStackTrace();
        }
    }
}

