
import java.io.File;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application{
    public static void main( final String[] args ) throws Exception {
        // JavaFXのスレッドを起動する
        launch(args);
    }

    @Override
    public void start( final Stage primaryStage ) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        primaryStage.setTitle("Odyssea Command Client"); // ステージのメインタイトルを設定する
        final VBox root = new VBox(); // ルートのコンテナ

        String path = new File(".").getAbsoluteFile().getParent();
        Settings.current_path = path;
        // アイコンの設定
        Image icon = new Image( "file:///" + path + "\\img\\ilias.png" );
        primaryStage.getIcons().add( icon );

        // File URI schemeで指定する
        Image image = new Image( "file:///" + path + "\\img\\normal.png" );
        //imageviewの作成
        ImageView imgView = new ImageView(image);
        root.getChildren().add(imgView);

        final Label label = new Label("要件は何でしょう？");
        root.getChildren().add(label); // コンテナにラベルを貼りつける

        // コマンドの入力するフォームの作成
        final TextField command = new TextField();
        command.setTooltip(new Tooltip("command field"));
        command.setPromptText("please input command");
        root.getChildren().add(command);
        final Label result = new Label("ここに結果が表示されます");
        root.getChildren().add(result);
        

        // Keyがタイプされたイベントを登録するコンビニエンスメソッド
        // command.addEventHandler( KeyEvent.KEY_TYPED , handler )と等価
        final EventHandler<KeyEvent> enterActionFilter = (event) ->
         execEnterAction(event, command, result, imgView);
        command.addEventHandler(KeyEvent.KEY_PRESSED, enterActionFilter);

        final Scene scene = new Scene(root, 600, 500);// シーンの新規生成
        primaryStage.setScene(scene);           // 生成したシーンをステージに貼り付ける
		primaryStage.show();                    // ステージを表示する
    }

    // [Abstract] コマンド入力時のイベントリスナ
    // [Projection] f: (KeyEvent, TextField) -> None
    private void execEnterAction(KeyEvent e, TextField c, Label l, ImageView imgView)
    {
        String cmd = c.getText();
        // [Tips] javaの文字列比較は"=="ではなくequalメソッドを使う必要がある
        //        ("=="比較はオブジェクト自体の比較になるため)
        if(e.getText().equals("\r"))  
        {
            System.out.println(Settings.EXECUTOR_NAME + " got a command: " + cmd + "." );
            Command.execute(cmd,imgView, l);
            c.clear();
        }
    }
}
