import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

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
            return Chat.chats[chat_id][0];
        }
        else if( cmdset[0].equals("weather") )
        {
            HTTPConnection htp = new HTTPConnection("csv"); 
            htp.scraping(Settings.WEATHER_URL, "Shift-JIS");
            Vector<String> csv_v;
            csv_v = htp.getHttpResult();
            String[] csv_a = new String[csv_v.size()];
            csv_v.copyInto(csv_a);
            Vector<String> weather = new Vector<String>();
            for(int i = 0; i < csv_a.length; i++)
            {
                if(csv_a[i].contains("千葉県"))
                {
                    weather.add(csv_a[i]);
                }
            }
            String[] result = new String[weather.size()];
            weather.copyInto(result);

            String output = "";
            for(int i = 0; i < result.length; i++)
            {
                String[] line = result[i].split(",");
                output += line[1] + line[2] + "の最高気温は" + line[9] + "[℃]、前日比は" + line[15]+ "[℃]です。" +  "\n";
            }

            return ("本日の千葉県の天気情報ですね。ええと...\n" + output);
        }
        // COVID-19の情報取得するコマンド
        else if( cmdset[0].equals("covid19") )
        {
            // 東京都の感染症対策公式サイトにGETコマンドを発行し情報を取得
            HTTPConnection htp = new HTTPConnection("html"); 
            htp.scraping(Settings.COVID19_TOKYO_URL, "");
            
            String[] html = new String[1]; // fillHttpResultIntoメソッドで参照渡しをするために要素数1の配列として定義
            htp.fillHttpResultInto(html);
            String[] scr  = html[0].split("新規患者に関する報告件数の推移");
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
        else if( cmdset[0].equals("train") )
        {
            HTTPConnection htp = new HTTPConnection("html"); 
            htp.scraping(Settings.TRAIN_URL, "");
            
            String[] html = new String[1]; // fillHttpResultIntoメソッドで参照渡しをするために要素数1の配列として定義
            htp.fillHttpResultInto(html);
           
            html[0] = html[0].split("現在運行情報のある路線")[1];
            html[0] = html[0].split("関東の主要鉄道")[0];
            html[0] = html[0].replaceAll("\n|\r","");
            String[] tr = html[0].split("<tr>|</tr>|<td>|</td>");

            String result = "以下の路線で遅延情報が出ているらしいです。\n";
            for(int i = 0; i < tr.length; i++)
            {
                System.out.print(i);
                System.out.print(":   ");
                System.out.println(tr[i]);
                if( ( !tr[i].contains("<") ) && ( !tr[i].equals("") ) )
                {
                    result += " - " + tr[i].split("は")[0] + "\n";
                }
            }

            return result;
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
        else if( cmd.equals("weather") )
        {
            Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
            imgView.setImage(next_img);
        }
        else if( cmd.equals("covid19") )
        {
            Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
            imgView.setImage(next_img);
        }
        else if( cmd.equals("think") )
        {
            Image next_img = new Image( "file:///" + path + "\\img\\happy.png" );
            imgView.setImage(next_img);
        }
        else if( cmd.equals("train") )
        {
            Image next_img = new Image( "file:///" + path + "\\img\\surprised.png" );
            imgView.setImage(next_img);
        }
        else
        {
           Image next_img = new Image( "file:///" + path + "\\img\\confused.png" );
            imgView.setImage(next_img);
        }
    }

}