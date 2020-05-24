import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;

public  class Comment {

    private static final String chats[][] = 
    {
        {"そんなコマンド打つ暇があったらコーディングしてくださいよ？","angry"},
        {"本日も頑張って行きましょう！","happy"},
    };

    private static final Random rand = new Random();


    public static String getComment( String cmd )
    {
        if( cmd.equals("ls") )
        {
            return ("要求コマンドは" + cmd +"ですね。\nすみません、この機能は現在実装中ですので暫くお待ちください" );
        }
        if( cmd.equals("cd") )
        {
            return ("要求コマンドは" + cmd +"ですね。\nすみません、この機能は現在実装中ですので暫くお待ちください" );
        }
        else if(cmd.equals("pwd"))
        {
            return ("現在のディレクトリですね。\n" + Settings.OWNER_NAME + "さんは現在「"+ Settings.current_path +"」の階層に居ますよ。");
        }
        else if(cmd.equals("chat"))
        {
            return chats[rand.nextInt(chats.length)][0];
        }
        else if(cmd.equals("weather"))
        {
            String html = HTTPConnection.scraping(Settings.WEATHER_URL);
            html  = html.split("comment:")[1];
            html  = html.split("title:")[0];
           // html  = html.split("<p>")[2];
            html = html.replaceAll("[0-~]|\"|}|!|,| ","");
            html = html.replaceAll("。","。\n");
            return ("関東地方の天気情報ですね。ええと...\n" + html);
        }
        else
        {
            return ("そんなコマンドありませんよ？");
        }
    }

    public static void renderExpression(String cmd, ImageView imgView)
    {
        String path = new File(".").getAbsoluteFile().getParent();
        if( cmd.equals("ls") )
        {
            Image next_img = new Image( "file:///" + path + "\\img\\confused.png" );
            imgView.setImage(next_img);
        }
        else if(cmd.equals("cd"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\confused.png" );
            imgView.setImage(next_img);
        }
        else if(cmd.equals("pwd"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
            imgView.setImage(next_img);
        }
        else if(cmd.equals("chat"))
        {
            if( chats[rand.nextInt(chats.length)][1].equals("normal") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
                imgView.setImage(next_img);
            }
            else if( chats[rand.nextInt(chats.length)][1].equals("happy") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
                imgView.setImage(next_img);
            }
            else if( chats[rand.nextInt(chats.length)][1].equals("angry") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\angry.png" );
                imgView.setImage(next_img);
            }
            else
            {
            }
            System.out.println(chats[rand.nextInt(chats.length)][1]);
        }
        else if(cmd.equals("weather"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
            imgView.setImage(next_img);
        }
        else
        {
           Image next_img = new Image( "file:///" + path + "\\img\\angry.png" );
            imgView.setImage(next_img);
        }
    }

}