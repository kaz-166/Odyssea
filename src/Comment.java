import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public  class Comment {
    public static String getComment( String cmd )
    {
        if( cmd.equals("ls") )
        {
            return ("要求コマンドは" + cmd +"ですね。\nすみません、この機能は現在実装中ですので暫くお待ちください" );
        }
        else if(cmd.equals("pwd"))
        {
            String path = new File(".").getAbsoluteFile().getParent();
            return ("現在のディレクトリですね。\n" + Settings.OWNER_NAME + "さんは現在「"+ path +"」の階層に居ますよ。");
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
        else if(cmd.equals("pwd"))
        {
            Image next_img = new Image( "file:///" + path + "\\img\\normal.png" );
            imgView.setImage(next_img);
        }
        else
        {
           Image next_img = new Image( "file:///" + path + "\\img\\angry.png" );
            imgView.setImage(next_img);
        }
    }
}