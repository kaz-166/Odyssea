import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;

public  class Command {

    private static final Random rand = new Random();
    static int chat_id = 0;

    // [Abstract] コマンドを解析し、処理を行い結果を返すメソッド
    public static String getComment( String cmd )
    {
        String[] cmdset = cmd.split(" +");
        if( cmdset[0].equals("ls") )
        {
            return ("要求コマンドは" + cmd +"ですね。\nすみません、この機能は現在実装中ですので暫くお待ちください" );
        }
        if( cmdset[0].equals("cd") )
        {
            return ("要求コマンドは" + cmd +"ですね。\nすみません、この機能は現在実装中ですので暫くお待ちください" );
        }
        else if( cmdset[0].equals("pwd") )
        {
            return ("現在のディレクトリですね。\n" + Settings.OWNER_NAME + "さんは現在「"+ Settings.current_path +"」の階層に居ますよ。");
        }
        else if( cmdset[0].equals("chat") )
        {
            chat_id = rand.nextInt(Chat.chats.length);
            System.out.println(Chat.chats[chat_id][1]);
            System.out.println(Chat.chats[chat_id][1]);
            System.out.println(Chat.chats[chat_id][1]);
            System.out.println(Chat.chats[chat_id][1]);
            return Chat.chats[chat_id][0];
        }
        else if( cmdset[0].equals("weather") )
        {
            String html = HTTPConnection.scraping(Settings.WEATHER_URL);
            html  = html.split("comment:")[1];
            html  = html.split("title:")[0];
           // html  = html.split("<p>")[2];
            html = html.replaceAll("[0-~]|\"|}|!|,| ","");
            html = html.replaceAll("。","。\n");
            return ("関東地方の天気情報ですね。ええと...\n" + html);
        }
        // COVID-19の情報取得するコマンド
        else if( cmdset[0].equals("covid19") )
        {
            // 東京都の感染症対策公式サイトにGETコマンドを発行し情報を取得
            String html = HTTPConnection.scraping(Settings.COVID19_TOKYO_URL);
            String[] scr  = html.split("新規患者に関する報告件数の推移");
            String[] info = scr[1].split("日別値");
            String new_patients = info[0];
            new_patients  = new_patients.split("<small")[0];
            new_patients  = new_patients.split("<span class=\"DataView-DataInfo-summary\">|<small class=\"DataView-DataInfo-date\">")[1];
            new_patients  = new_patients.replaceAll("　| ", "");
            // 日時の取得
            String date = info[0].split("<span class=\"DataView-DataInfo-summary\">|<small class=\"DataView-DataInfo-date\">")[2];
            date = date.replaceAll(" ","");
            // 累計感染者数の取得
            String accm = scr[0];
            accm = accm.split("陽性者数")[1];
            accm = accm.split("<strong>")[1];
            accm = accm.split("</strong>")[0];
            // 感染者数の増減の取得
            String rate = info[1].split("</small>")[0];
            rate = rate.replaceAll("（", "(");
            rate = rate.replaceAll("）", ")");
            

            String comment = "新型コロナウイルス(COVID-19)の情報ですね。\n";
            comment += "直近(";
            comment +=  date;
            comment += ")の東京都の新規感染者数は" + new_patients + "人"+ rate +"です。\n";
            comment += "これにより東京都内の累計感染者数は" + accm + "人となりました。\n";
            comment += "外出後は手洗いうがいをして体調管理には気をつけましょうね。";
            return comment;
        }
        else if( cmdset[0].equals("think") )
        {
            HTTPConnection.browsing(Settings.THINK_TANK_URL);
            return "開発メモ(Think-Tnak-Case)を開きますね";
        }
        else
        {
            return ("そんなコマンドありませんよ？");
        }
    }

    // [Abstract] コマンド実行時の表情を変更するメソッド
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
        else if( cmd.equals("chat") )
        {
            if( Chat.chats[chat_id][1].equals("normal") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
                imgView.setImage(next_img);
            }
            else if( Chat.chats[chat_id][1].equals("happy") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
                imgView.setImage(next_img);
            }
            else if( Chat.chats[chat_id][1].equals("angry") )
            {
                Image next_img = new Image( "file:///" + path + "\\img\\angry.png" );
                imgView.setImage(next_img);
            }
            else
            {
            }
            System.out.print("expression: ");
            System.out.println(Chat.chats[chat_id][1]);
        }
        else if(cmd.equals("weather"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
            imgView.setImage(next_img);
        }
        else if(cmd.equals("covid19"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
            imgView.setImage(next_img);
        }
        else if(cmd.equals("think"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
            imgView.setImage(next_img);
        }
        else
        {
           Image next_img = new Image( "file:///" + path + "\\img\\confused.png" );
            imgView.setImage(next_img);
        }
    }

}