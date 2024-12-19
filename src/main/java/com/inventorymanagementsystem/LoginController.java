package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.User;
import com.inventorymanagementsystem.config.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label s_logo;

    @FXML
    private Label e_logo;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane login_form;


    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    private double x;
    private double y;


    public void textfieldDesign(){
        if(username.isFocused()){
            username.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");
            password.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
        }else if(password.isFocused()){
            username.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
            password.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");

        }
    }

    public void dropShowAffect(){
        DropShadow original=new DropShadow(20, Color.valueOf("99a23d"));
        e_logo.setEffect(original);
        s_logo.setEffect(original);
    }

    public void onExit(){
        System.exit(0);
    }

    public void login() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM users WHERE username=? and password=?";
        try {
            // Prepare statement untuk query login
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());
            resultSet = preparedStatement.executeQuery();

            // Cek hasil query
            if (resultSet.next()) {
                User.name = username.getText();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Login Successful !");
                alert.showAndWait();

                // Tutup window login
                login_btn.getScene().getWindow().hide();

                // Cek apakah username mengandung kata "admin"
                String dashboardPath;
                if (username.getText().toLowerCase().contains("admin")) {
                    dashboardPath = "dashboard.fxml"; // Redirect ke admin dashboard
                } else {
                    dashboardPath = "dashboard_kasir.fxml"; // Redirect ke kasir dashboard
                }

                // Load FXML sesuai hasil pengecekan
                Parent root = FXMLLoader.load(getClass().getResource(dashboardPath));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                // Drag event (opsional untuk membuat window dapat di-drag)
                root.setOnMousePressed((event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged((event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT); // Set style transparan
                stage.setScene(scene);
                stage.show();

            } else {
                // Jika login gagal
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username/Password");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }


    // buat method untuk redirect ke halaman register.fxml
    public void register() throws Exception {
        login_form.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }


    public void loginView() throws Exception {
        login_form.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void registerUser() {
        connection = Database.getInstance().connectDB();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, password.getText());
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Register Successful !");
            alert.showAndWait();
        } catch (Exception err) {
            err.printStackTrace();
    }

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dropShowAffect();
    }
}