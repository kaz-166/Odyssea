
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;
import javafx.geometry.Orientation;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Main extends Application{
    public static void main(final String[] args) throws Exception {
        // JavaFXのスレッドを起動する
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        primaryStage.setTitle("CommandLine"); // ステージのメインタイトルを設定する
        final VBox root = new VBox(); // ルートのコンテナ

        final Label label = new Label("Command");
        root.getChildren().add(label); // コンテナにラベルを貼りつける

        // コマンドの入力するフォームの作成
        final TextField command = new TextField();
        command.setTooltip(new Tooltip("command field"));
        command.setPromptText("please input command");
        root.getChildren().add(command);
        // command.textProperty().addListener( ( ov , old , current ) ->
        // System.out.println( "テキストフィールドの値：" + command.getText() ) );
        final Label result = new Label("ここに結果が表示されます");
        root.getChildren().add(result);


        // Keyがタイプされたイベントを登録するコンビニエンスメソッド
        // command.addEventHandler( KeyEvent.KEY_TYPED , handler )と等価
        final EventHandler<KeyEvent> enterActionFilter = (event) -> execEnterAction(event, command, result);
        command.addEventHandler(KeyEvent.KEY_PRESSED, enterActionFilter);

        final Scene scene = new Scene(root, 300, 250);// シーンの新規生成
        primaryStage.setScene(scene);           // 生成したシーンをステージに貼り付ける
		primaryStage.show();                    // ステージを表示する
    }

    // [Abstract] コマンド入力時のイベントリスナ
    // [Projection] f: (KeyEvent, TextField) -> None
    private void execEnterAction(KeyEvent e, TextField c, Label l)
    {
        // [Tips] javaの文字列比較は"=="ではなくequalメソッドを使う必要がある
        //        ("=="比較はオブジェクト自体の比較になるため)
        if(e.getText().equals("\r"))  
        {
            System.out.println( "要求コマンドは" + c.getText() +"ですね。" );
            l.setText("要求コマンドは" + c.getText() +"ですね。" );
            c.clear();
        }
    }
    
}

 /*
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class Main extends Application {
 
    public static void main(String[] args)
    {
        launch( args );
    }
 
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // フォント色がおかしくなることへの対処
        System.setProperty( "prism.lcdtext" , "false" );
         
        // シーングラフを作成
        VBox        root    = new VBox( 2.0 );
         
        // ラベルとツールチップを登録
        Label       label   = new Label( "ラベル。ココにマウスをあわせてください（ツールチップ機能の確認）" );
        label.setTooltip( new Tooltip( "ツールチップとは、オンマウス時に表示されるテキストのことです。" ) );
        root.getChildren().add( label );
 
        // ハイパーリンク
        Hyperlink       link    = new Hyperlink();
        link.setText( "ハイパーリンク" );
        link.setOnAction( e -> System.out.println( "ハイパーテキストをクリック" ) );
        root.getChildren().add( link );
         
        // テキスト・フィールド
        // copy,cut,selectAll,paste関数などが使える
        TextField   text        = new TextField();
        text.setTooltip( new Tooltip( "text field" ) );
        text.setPromptText( "please input your name" );
        root.getChildren().add( text );
        text.textProperty().addListener( ( ov , old , current ) -> System.out.println( "テキストフィールドの値：" + text.getText() ) );
         
        // パスワード・フィールド
        PasswordField   password    = new PasswordField();
        password.setPromptText( "please input password" );
        root.getChildren().add( password );
        password.textProperty().addListener( ( ov , old , current ) -> System.out.println( "パスワードフィールドの値：" + password.getText() ) );
         
        // ボタンを登録
        Button      button  = new Button( "ボタン" );
        root.getChildren().add( button );
        button.addEventHandler( ActionEvent.ACTION , e -> System.out.println( "Button Click" ) );
         
        // トグルボタンを登録
        ToggleButton    toggle  = new ToggleButton( "トグルボタン" );
        root.getChildren().add( toggle );
        toggle.selectedProperty().addListener( ( ov , old , current ) -> System.out.println( "トグルボタンの状態：" + toggle.isSelected() ) );
 
        // セパレータ
        Separator       sep     = new Separator();
        sep.setOrientation( Orientation.HORIZONTAL );
        sep.setMaxWidth( 200 );
        root.getChildren().add( sep );
         
        // ラジオボタンを登録
        ToggleGroup radioGroup  = new ToggleGroup();
        RadioButton radio1      = new RadioButton( "ラジオボタン1" );
        RadioButton radio2      = new RadioButton( "ラジオボタン2" );
        radio1.setToggleGroup( radioGroup );
        radio2.setToggleGroup( radioGroup );
        radio1.setUserData( "ラジオボタン１が選択されました" );
        radio2.setUserData( "ラジオボタン２が選択されました" );
        radio1.setSelected( true );
        root.getChildren().add( radio1 );
        root.getChildren().add( radio2 );
        radioGroup.selectedToggleProperty().addListener( ( ov , old , current ) -> System.out.println( radioGroup.getSelectedToggle().getUserData() ) );
 
        // チェックボックス
        CheckBox    check1      = new CheckBox("チェックボックス１");
        check1.setAllowIndeterminate( true );
        root.getChildren().addAll( check1 );
        check1.indeterminateProperty().addListener( ( ov , old , current ) -> System.out.println( "チェックボックスの状態：indeterminate:" + check1.isIndeterminate() + "/" ) );
        check1.selectedProperty().addListener( ( ov , old , current ) -> System.out.println( "チェックボックスの状態：selected:" + check1.isSelected() ) );
         
        // 選択ボックス
        ChoiceBox   choice      = new ChoiceBox( FXCollections.observableArrayList( "Zero" , new Separator() , "First" , "Second" , "Third" ));
        choice.setTooltip( new Tooltip("select order") );
        root.getChildren().add( choice );
        choice.getSelectionModel().selectedItemProperty().addListener( ( ov , old , current ) -> System.out.println( "選択ボックス：" + choice.getSelectionModel().getSelectedItem() ) );
         
        // コンボボックス
        ComboBox        combo       = new ComboBox();
        combo.setItems( FXCollections.observableArrayList( "その１" , "その２" , "その３" ) );
        combo.setEditable( true );
        Callback<ListView<String>,ListCell<String>> cellFactory = p ->
        {
            // セルを作成
            ListCell<String>    cell    = new ListCell<String>(){
                {
                    super.setPrefWidth(100);
                }
                @Override public void updateItem( String item , boolean empty )
                {
                    // 元関数を呼び出し
                    super.updateItem(item, empty);
                     
                    // セルのプロパティを設定
                    if( item == null ){ return; }
                    setText(item);
                    setTextFill( Color.RED );
                }
            };
             
            return cell;
        };
        combo.setCellFactory( cellFactory );
        root.getChildren().add( combo );
        combo.getSelectionModel().selectedItemProperty().addListener( ( ov , old , current ) -> System.out.println( "コンボボックス：" + combo.getSelectionModel().getSelectedItem() ) );
         
        // リスト･ビュー
        ListView<String>    list    = new ListView<String>();
        list.setItems( FXCollections.observableArrayList( "1st" , "2nd" , "3rd" ) );
        list.setPrefSize( 100 , 60 );
        list.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
        root.getChildren().add( list );
        list.getSelectionModel().selectedItemProperty().addListener( ( ov , old , current ) -> System.out.println( "リストビュー：" + list.getSelectionModel().getSelectedItems() ) );
         
        // 日付ピッカー
        DatePicker  datePicker  = new DatePicker();
        root.getChildren().add( datePicker );
        datePicker.valueProperty().addListener( ( ov , old , current ) -> System.out.println( "日付ピッカー：" + datePicker.getValue() ) );
 
        // カラーピッカー
        ColorPicker colorPicker = new ColorPicker();
        root.getChildren().add( colorPicker );
        colorPicker.valueProperty().addListener( ( ov , old , current ) -> System.out.println( "カラーピッカー：" + colorPicker.getValue() ) );
         
        // スクロールバー
        Rectangle       rect1       = new Rectangle( 30 , 15 , Color.BLACK );
        ScrollBar       scroll      = new ScrollBar();
        scroll.setMin( 0 );
        scroll.setMax( 100 );
        scroll.setValue( 30 );
        scroll.setUnitIncrement( 1 );               // 矢印ボタンを押下した場合の増加量
        scroll.setBlockIncrement( 10 );             // バーを押下した場合の増加量
        root.getChildren().addAll( rect1 , scroll );
        scroll.valueProperty().addListener( ( ov , old , current ) -> rect1.setWidth( scroll.getValue() ) );
         
        // スクロール・ペイン
        ScrollPane      sp          = new ScrollPane();
        sp.setContent( new Label( "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n" ) );
        sp.setPannable( true );
        sp.setPrefSize( 100 , 60 );
        sp.setHbarPolicy( ScrollBarPolicy.NEVER );
        sp.setVbarPolicy( ScrollBarPolicy.ALWAYS );
        root.getChildren().add( sp );
         
        // スライダ
        Rectangle       rect2       = new Rectangle( 50 , 15 , Color.BLACK );
        Slider          slider      = new Slider();
        slider.setMin( 0 );
        slider.setMax( 100 );
        slider.setValue( 50 );
        slider.setShowTickLabels( true );
        slider.setShowTickMarks( true );
        slider.setMajorTickUnit( 20 );
        slider.setMinorTickCount( 5 );
        slider.setBlockIncrement( 5 );
        root.getChildren().addAll( rect2 , slider );
        slider.valueProperty().addListener( ( ov , old , current ) -> rect2.setWidth( slider.getValue() ) );
         
        // 進行状況バー/インジケータ
        ProgressBar         pb  = new ProgressBar( 0.5 );
        ProgressIndicator   pi  = new ProgressIndicator( 0.5 );
        root.getChildren().addAll( pb , pi );
        slider.valueProperty().addListener( ( ov , old , current ) -> { pb.setProgress( slider.getValue() / 100.0 ); pi.setProgress( slider.getValue() / 100.0 ); }  );
                // シーンを作成
                Scene       scene   = new Scene( root , 500 , 600 , Color.web( "9FCC7F" ) );
         
                // ウィンドウ表示
                primaryStage.setScene( scene );
                primaryStage.show();
                 
            }
             
        }
        */