import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

public class Main extends Application{
    public static void main(String[] args) throws Exception {
        // JavaFXのスレッドを起動する
        launch(args);
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CommandLine");   // ステージのメインタイトルを設定する
        StackPane root = new StackPane();       // ルートのコンテナ
        Scene scene = new Scene(root, 300, 250);// シーンの新規生成
        primaryStage.setScene(scene);           // 生成したシーンをステージに貼り付ける
        Label label = new Label("Hello, World!");
        root.getChildren().add(label);          // コンテナにラベルを貼りつける

		primaryStage.show();                    // ステージを表示する
	}
}
