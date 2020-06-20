import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import javafx.scene.control.*;


public  class Command {
    private static final Random rand = new Random();
    /* chatコマンド連続使用時にイリアス君を怒らせる用のメンバ変数 */
    private static int chat_cnt = 0; 
    private static String before_command = "";

/**********************************************************************
 * Public Method
 **********************************************************************/
    // [Abstract] コマンドを解析し、処理を行い結果を返すメソッド
    public static void execute( String cmd, ImageView imgView, Label comment )
    {
        String[] cmdset = cmd.split(" +");
        HashMap<String, String> result;
        final String NO_COMMAND = "そんなコマンドありませんよ？\n";
        final int CHAT_YELLOWCARD_LIMIT = 5;
        final int CHAT_REDCARD_LIMIT = 8;

        switch( cmdset[0] )
        {
            case "greeting":
                result = HTTPConnection.requesting_post("{\"id\": \"1\"}");
                renderIliasExpression( result.get("expression"), imgView );
                break;
            // case "manual":
            //    result = exec_command_manual();
            //    renderIliasExpression( "happy", imgView );
            //    break;
            // case "chat": 
            /*   
                if( cmdset[0].equals( before_command ) )
                {
                    chat_cnt++;
                }
                else
                {
                    chat_cnt = 0;
                }
                System.out.println(chat_cnt);
                if( chat_cnt < CHAT_YELLOWCARD_LIMIT )
                {
                    int chat_id = 0;
                    chat_id = rand.nextInt(Chat.chats.length);
                    result= exec_command_chat( chat_id );
                    renderIliasExpression( Chat.chats[chat_id][1], imgView );
                }
                else if( chat_cnt < CHAT_REDCARD_LIMIT )
                {
                    result = Chat.alert[0];
                    renderIliasExpression( Chat.alert[1], imgView );
                }
                else
                {
                    result = Chat.finish[0];
                    renderIliasExpression( Chat.finish[1], imgView );
                }
            */
            // break;
            case "weather": 
                result = exec_command_weather( cmdset );
                renderIliasExpression( result.get("expression"), imgView );
                break;
            // case "covid19":
            //    result = exec_command_covid19();
            //    renderIliasExpression( "normal", imgView );
            // break;
            // case "think":
            //     result = exec_command_think();  
            //    renderIliasExpression( "happy", imgView );
            // break;
            // case "train":
            //     result = exec_command_train();
            //     renderIliasExpression( "confused", imgView );
            // break;
            // case "rain":
            //     result = exec_command_rain();
            //     renderIliasExpression( "confused", imgView );
            // break;
            // case "yotei":
            //     result = exec_command_yotei();
            //     renderIliasExpression( "happy", imgView );
            // break;
            default:
                result = new HashMap<String, String>();
                result.put("message", NO_COMMAND);
                renderIliasExpression( "confused", imgView );
                break;
        }
        before_command = cmdset[0];
        comment.setText( result.get("message") );
    }

/**********************************************************************
 * Private Method
 **********************************************************************/
    private static void renderIliasExpression( String exp, ImageView imgView )
    {
        String path = new File(".").getAbsoluteFile().getParent();
        Image next_img = new Image( "file:///" + path + "\\img\\" + exp + ".png" );
        imgView.setImage(next_img);
    }

    private static String exec_command_think()
    {
        HTTPConnection.browsing(Settings.THINK_TANK_URL);
        return "開発メモ(Think-Tnak-Case)を開きますね";
    }

    private static String exec_command_manual()
    {
        HTTPConnection.browsing(Settings.MANUAL_URL);
        return "ユーザマニュアルを開きますね";
    }

    private static String exec_command_chat( int chat_id )
    {
        return Chat.chats[chat_id][0];
    }

    private static String exec_command_covid19()
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

    private static String exec_command_train()
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
            if( ( !tr[i].contains("<") ) && ( !tr[i].equals("") ) )
            {
                result += " - " + tr[i].split("は")[0] + "\n";
            }
        }
        return result;
    }

    private static String exec_command_rain()
    {
        HTTPConnection htp = new HTTPConnection("html"); 
        htp.scraping(Settings.RAIN_URL, "");
            
        String[] html = new String[1]; // fillHttpResultIntoメソッドで参照渡しをするために要素数1の配列として定義
        htp.fillHttpResultInto(html);
        html[0] = html[0].replaceAll("\n|\r","");
        String[] item = html[0].split("<div class=\"weather-day__item\">");
        
        String result = "降水情報ですね。予報によりますと...\n";
        for(int i = 1; i < item.length-1; i++)
        {
            item[i] = item[i].replaceAll("</div>|<br>|</p>| ","");
            item[i] = item[i].replace("<pclass=\"weather-day__time\">","<>");
            item[i] = item[i].replace("<pclass=\"weather-day__t\">","<>");
            item[i] = item[i].replace("<pclass=\"weather-day__w\">","<>");
            item[i] = item[i].replace("<pclass=\"weather-day__r\">","<>");
            item[i] = item[i].replace("<pclass=\"weather-day__icon\">","<>");
            String[] elem = item[i].split("<>");
            result += elem[1] + "の降水量は" + elem[3] + "、気温は" + elem[4] + "、\n";
        }
        result += "とのことです。";
 
        return result;
    }

    private static HashMap<String, String> exec_command_weather( String[] cmdset )
    {
        HashMap<String, String> result;
        if( cmdset.length == 1 )
        {
            result = HTTPConnection.requesting_post("{\"id\": \"3\", \"location\": \"tokyo\", \"hour\": \"0\"}");
        }
        else if ( cmdset.length == 2 )
        {
            // 第一引数がlocationかhourのどちらか判定する
            String regex = "[0-9]+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(cmdset[1]);
            if( m.find() )
            {
                // 第一引数がhourと判定された場合
                result = HTTPConnection.requesting_post("{\"id\": \"3\", \"location\": \"tokyo\", \"hour\": \"" + cmdset[1] + "\"}");
            }
            else
            {
                // 第一引数がlocationと判定された場合
                result = HTTPConnection.requesting_post("{\"id\": \"3\", \"location\": \"" + cmdset[1] + "\", \"hour\": \"0\"}");
            }
        }
        else
        {
            result = HTTPConnection.requesting_post("{\"id\": \"3\", \"location\": \"" + cmdset[1] + "\", \"hour\": \"" + cmdset[2] + "\"}");
        }

        return result;
    }

    private static String exec_command_yotei()
    {
        HTTPConnection.browsing(Settings.YOTEI_URL);
        return "予定管理アプリ(yotei-app)を開きますね。";
    }
}