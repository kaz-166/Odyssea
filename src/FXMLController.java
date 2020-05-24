import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;


// [Abstract] FXMLのコントローラ
public class FXMLController implements Initializable {
    @FXML
    private Button button1;

    @FXML
    private TextField input_command1;

    @FXML
    private TextField input_command2;

    @FXML
    private HBox changeBox;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // 初期化開始メッセージ
        System.out.println("initialise FXMLController.");
    }

    /**
     * Button1を押した場合のイベントハンドラ
     * （バインディングの利用方法確認）
     * @param e
     */
    @FXML
    public void onPushButton1( ActionEvent e )
    {
        // 標準出力
        System.out.println( String.format( "button is pushed!" ));
    }
}