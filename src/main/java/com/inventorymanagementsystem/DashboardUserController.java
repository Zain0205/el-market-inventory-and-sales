package com.inventorymanagementsystem;

import com.inventorymanagementsystem.config.Database;
import com.inventorymanagementsystem.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class DashboardUserController implements Initializable {

    private double x;
    private double y;

    @FXML
    private Button billing_btn;

    @FXML
    private AnchorPane billing_pane;

    @FXML
    private Button customer_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane customer_pane;

    @FXML
    private AnchorPane dasboard_pane;


    @FXML
    private Button purchase_btn;

    @FXML
    private AnchorPane purchase_pane;

    @FXML
    private Button sales_btn;

    @FXML
    private AnchorPane sales_pane;

    @FXML
    private Label user;

    @FXML
    private Label inv_num;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    @FXML
    private Button bill_add;

    @FXML
    private Button bill_clear;

    @FXML
    private DatePicker bill_date;

    @FXML
    private TextField bill_item;

    @FXML
    private TextField bill_name;

    @FXML
    private TextField bill_phone;

    @FXML
    private TextField bill_price;

    @FXML
    private Button bill_print;

    @FXML
    private ComboBox<String> bill_quantity;

    @FXML
    private Button bill_save;

    @FXML
    private TextField bill_total_amount;

    @FXML
    private TableView<Billing> billing_table;

    @FXML
    private TextField billing_table_search;

    @FXML
    private Label final_amount;

    private  String invoiceList[]={"BX123456","ZX123456","AX123456"};

    private String quantityList[]={"1","2","3","4","5","6","7","8","9","10"};

    @FXML
    private TableColumn<?, ?> col_bill_item_num;

    @FXML
    private TableColumn<?, ?> col_bill_price;

    @FXML
    private TableColumn<?, ?> col_bill_quantity;

    @FXML
    private TableColumn<?, ?> col_bill_total_amt;

    @FXML
    private Button cust_btn_add;

    @FXML
    private Button cust_btn_delete;

    @FXML
    private Button cust_btn_edit;

    @FXML
    private TableColumn<?, ?> cust_col_id;

    @FXML
    private TableColumn<?, ?> cust_col_name;

    @FXML
    private TableColumn<?, ?> cust_col_phone;

    @FXML
    private TextField cust_field_name;

    @FXML
    private TextField cust_field_phone;

    @FXML
    private TextField customer_search;

    @FXML
    private TableView<Customer> customer_table;

    @FXML
    private TableColumn<?, ?> sales_col_cust_name;

    @FXML
    private TableColumn<?, ?> sales_col_date_of_sales;

    @FXML
    private TableColumn<?, ?> sales_col_id;

    @FXML
    private TableColumn<?, ?> sales_col_inv_num;

    @FXML
    private TableColumn<?, ?> sales_col_quantity;

    @FXML
    private TableColumn<?, ?> sales_col_total_amount;

    @FXML
    private TableColumn<?, ?> sales_col_price;

    @FXML
    private TableColumn<?, ?> sales_col_item_num;

    @FXML
    private TableView<Sales> sales_table;

    @FXML
    private Label sales_total_amount;

    @FXML
    private Button purchase_btn_add;

    @FXML
    private Button purchase_btn_print;

    @FXML
    private Label purchase_total_amount;

    @FXML
    private TableColumn<?, ?> purchase_col_date_of_purchase;

    @FXML
    private TableColumn<?, ?> purchase_col_id;

    @FXML
    private TableColumn<?, ?> purchase_col_invoice;

    @FXML
    private TableColumn<?, ?> purchase_col_shop_details;

    @FXML
    private TableColumn<?, ?> purchase_col_total_amount;

    @FXML
    private TableColumn<?, ?> purchase_col_total_items;

    @FXML
    private TableView<Purchase> purchase_table;

    @FXML
    private Label dash_total_items_sold_this_month;

    @FXML
    private Label dash_total_purchase;

    @FXML
    private Label dash_total_sales_items_this_month_name;

    @FXML
    private Label dash_total_sales_this_month;

    @FXML
    private Label dash_total_sales_this_month_name;

    @FXML
    private Label dash_total_sold;

    @FXML
    private Label dash_total_stocks;

    @FXML
    private Label dash_low_stock_items;

    @FXML
    private Button inventory_btn;

    @FXML
    private AnchorPane inventory_pane;

    @FXML
    private TableView<Product> inventory_table;

    @FXML
    private TableColumn<Product, Integer> inventory_col_id;

    @FXML
    private TableColumn<Product, String> inventory_col_item_number;

    @FXML
    private TableColumn<Product, String> inventory_col_item_group;

    @FXML
    private TableColumn<Product, Integer> inventory_col_quantity;

    @FXML
    private TableColumn<Product, Double> inventory_col_price;

    @FXML
    private TableColumn<?, String> inventory_col_status;

    @FXML
    private TableColumn<?, String> inventory_col_supplier;

    @FXML
    private TextField inventory_field_item_number;

    @FXML
    private TextField inventory_field_item_group;

    @FXML
    private TextField inventory_field_quantity;

    @FXML
    private TextField inventory_field_price;

    @FXML
    private TextField inventory_field_supplier;

    @FXML
    private Button inventory_add_btn;

    @FXML
    private Button inventory_edit_btn;

    @FXML
    private Button user_btn;
    @FXML
    private Button add_user_btn;
    @FXML
    private Button edit_user_btn;
    @FXML
    private Button delete_user_btn;
    @FXML
    private AnchorPane user_pane;
    @FXML
    private TableView<User> user_table; // Pastikan ini sesuai dengan fx:id di FXML
    @FXML
    private TableColumn<User, Integer> user_col_id;
    @FXML
    private TableColumn<User, String> user_col_username;
    @FXML
    private TableColumn<User, String> user_col_password;

    @FXML
    private Button signout_btn;

    List<Product> productsList;

    public void onExit(){
        System.exit(0);
    }

    public void activateAnchorPane(){
        dashboard_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(true);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            inventory_pane.setVisible(false);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
        });
        billing_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(true);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            inventory_pane.setVisible(false);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
        });
        customer_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(true);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            inventory_pane.setVisible(false);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
        });
        sales_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(true);
            purchase_pane.setVisible(false);
            inventory_pane.setVisible(false);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
        });
        purchase_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(true);
            inventory_pane.setVisible(false);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
        });

        inventory_btn.setOnMouseClicked(mouseEvent -> {
            dasboard_pane.setVisible(false);
            billing_pane.setVisible(false);
            customer_pane.setVisible(false);
            sales_pane.setVisible(false);
            purchase_pane.setVisible(false);
            inventory_pane.setVisible(true);
//            user_pane.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
//            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
            showInventoryData();
        });

//        user_btn.setOnMouseClicked(mouseEvent -> {
//            dasboard_pane.setVisible(false);
//            billing_pane.setVisible(false);
//            customer_pane.setVisible(false);
//            sales_pane.setVisible(false);
//            purchase_pane.setVisible(false);
//            inventory_pane.setVisible(false);
////            user_pane.setVisible(true);
//            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            billing_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            customer_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            sales_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            purchase_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
//            inventory_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(0, 0, 0, 0.8), rgba(193, 193, 193, 0.2))");
////            user_btn.setStyle("-fx-background-color:linear-gradient(to bottom right , rgba(239, 239, 239, 0.7), rgba(101, 101, 101, 0.7))");
//            showUserData();
//        });
    }

    public void setUsername(){
        user.setText(User.name.toUpperCase());
    }

    public void activateDashboard(){
        dasboard_pane.setVisible(true);
        billing_pane.setVisible(false);
        customer_pane.setVisible(false);
        sales_pane.setVisible(false);
        purchase_pane.setVisible(false);
        inventory_pane.setVisible(false);
    }

    public void showUserData() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB(); // Menghubungkan ke database

        String sql = "SELECT * FROM users"; // Asumsikan table nama "users"

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ));
            }

            user_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            user_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            user_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));

            user_table.setItems(userList); // Mengisi tabel dengan data pengguna
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addUser() {
        // Set up a dialog or input form to get new user information
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Add User");
        usernameDialog.setHeaderText("Enter new username:");

        Optional<String> usernameResult = usernameDialog.showAndWait();
        usernameResult.ifPresent(username -> {
            if (username != null && !username.isEmpty()) {
                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Add User");
                passwordDialog.setHeaderText("Enter password for new user:");

                Optional<String> passwordResult = passwordDialog.showAndWait();
                passwordResult.ifPresent(password -> {
                    if (password != null && !password.isEmpty()) {
                        String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
                        try {
                            preparedStatement = connection.prepareStatement(insertSql);
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, password); // Menggunakan password yang diberikan
                            preparedStatement.executeUpdate();
                            showUserData(); // Refresh user data in the table
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @FXML
    private void editUser() {
        User selectedUser = user_table.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to edit.");
            alert.showAndWait();
            return;
        }

        TextInputDialog usernameDialog = new TextInputDialog(selectedUser.getUsername());
        usernameDialog.setTitle("Edit User");
        usernameDialog.setHeaderText("Edit username:");

        Optional<String> usernameResult = usernameDialog.showAndWait();
        usernameResult.ifPresent(newUsername -> {
            if (newUsername != null && !newUsername.isEmpty()) {
                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Edit User");
                passwordDialog.setHeaderText("Enter new password (leave blank to keep current one):");

                Optional<String> passwordResult = passwordDialog.showAndWait();
                String newPassword = passwordResult.orElse(selectedUser.getPassword()); // Jika tidak diisi, gunakan password lama

                String updateSql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
                try {
                    preparedStatement = connection.prepareStatement(updateSql);
                    preparedStatement.setString(1, newUsername);
                    preparedStatement.setString(2, newPassword); // Menggunakan password baru
                    preparedStatement.setInt(3, selectedUser.getId());
                    preparedStatement.executeUpdate();
                    showUserData(); // Refresh user data in the table
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void deleteUser() {
        User selectedUser = user_table.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }

        // Cek jika user ID = 1
        if (selectedUser.getId() == 1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("User with ID 1 cannot be deleted.");
            alert.showAndWait();
            return;
        }

        // Tampilkan konfirmasi sebelum menghapus
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to delete this user?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String deleteSql = "DELETE FROM users WHERE id = ?";
            try {
                preparedStatement = connection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, selectedUser.getId());
                preparedStatement.executeUpdate();
                showUserData(); // Refresh user data in the table
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Product> getItemsList(){
        productsList=new ArrayList<>();
        connection= Database.getInstance().connectDB();
        String sql="SELECT * FROM products";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Product product;
            while (resultSet.next()){
                int quantity = resultSet.getInt("quantity");
                String status;
                if (quantity == 0) {
                    status = "out of stock";
                } else if (quantity < 10) {
                    status = "low stock";
                } else {
                    status = "in stock";
                }
                product = new Product(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("item_number"),
                        resultSet.getString("item_group"),
                        quantity,
                        Double.parseDouble(resultSet.getString("price")),
                        status, // Pass status
                        resultSet.getString("supplier") // Pass supplier
                );
                productsList.add(product);
            }
        }catch (Exception err){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
        return productsList;
    }
    
    public void setInvoiceNum() {
        connection = Database.getInstance().connectDB();
        String sql = "SELECT MAX(CAST(SUBSTRING(inv_num, 5) AS UNSIGNED)) AS inv_num FROM sales"; // Perbaikan di sini

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String result = resultSet.getString("inv_num");
                if (result == null) {
                    Invoice.billingInvoiceNumber = "INV-1";
                } else {
                    int invId = Integer.parseInt(result);
                    invId++;
                    Invoice.billingInvoiceNumber = "INV-" + invId;
                }
                inv_num.setText(Invoice.billingInvoiceNumber); // Update UI
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void setAutoCompleteItemNumber(){
        getItemsList();
        List<String> itemNumberList=productsList.stream().map(Product::getItemNumber).collect(Collectors.toList());
        ObservableList<String> observableItemList=FXCollections.observableArrayList(itemNumberList);
        TextFields.bindAutoCompletion(bill_item,observableItemList);
    }

    public void comboBoxQuantity() {
//        ObservableList<String> comboList = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
//        bill_quantity.setItems(comboList);
        bill_quantity.setEditable(true); // Untuk mengizinkan input manual
        // Listener untuk mendeteksi perubahan input
        bill_quantity.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) { // Memastikan tidak kosong
                checkForPriceandQuantity(); // Panggil untuk menghitung total amount
            }
        });
    }

    @FXML
    private void increaseQuantity() {
        int currentQuantity = 1; // Default quantity, bisa di-update jika item sudah dipilih
        if (bill_quantity.getSelectionModel().getSelectedItem() != null) {
            currentQuantity = Integer.parseInt(bill_quantity.getValue().toString());
        }
        bill_quantity.setValue(String.valueOf(currentQuantity + 1));
        checkForPriceandQuantity(); // Memperbarui total amount jika perlu
    }

    @FXML
    private void decreaseQuantity() {
        int currentQuantity = 1; // Default quantity, bisa di-update jika item sudah dipilih
        if (bill_quantity.getSelectionModel().getSelectedItem() != null) {
            currentQuantity = Integer.parseInt(bill_quantity.getValue().toString());
            if (currentQuantity > 1) { // Mencegah quantity menjadi kurang dari 1
                bill_quantity.setValue(String.valueOf(currentQuantity - 1));
                checkForPriceandQuantity(); // Memperbarui total amount jika perlu
            }
        }
    }

    public void checkForPriceandQuantity(){
        if (!bill_price.getText().isBlank() && bill_quantity.getEditor().getText() != null && !bill_quantity.getEditor().getText().isBlank()) {
            try {
                int price = Integer.parseInt(bill_price.getText());
                int quantity = Integer.parseInt(bill_quantity.getEditor().getText());
                int totalAmount = price * quantity;
                bill_total_amount.setText(String.valueOf(totalAmount));
            } catch (NumberFormatException e) {
                bill_total_amount.setText("0");
            }
        } else {
            bill_total_amount.setText("0");
        }
    }

    public void getPriceOfTheItem(){
        try {
            Product product = productsList.stream().filter(prod -> prod.getItemNumber().equals(bill_item.getText())).findAny().orElse(null);
            if (product != null) {
                double price = product.getPrice(); // Ambil harga produk
                bill_price.setText(String.valueOf((int) price)); // Menampilkan hanya bagian integer
                checkForPriceandQuantity(); // Panggil untuk menghitung total amount
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Item not found.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Exception Item Number: " + err.getMessage());
            alert.showAndWait();
        }
    }

    public void onInputTextChanged() {
        // Hanya menggunakan setOnKeyReleased untuk bill_price
        bill_price.setOnKeyReleased(event -> checkForPriceandQuantity());

        // Listener untuk perubahaan pada property text
        bill_price.textProperty().addListener((observable, oldValue, newValue) -> checkForPriceandQuantity());

        // Hanya satu event handler untuk bill_quantity
        bill_quantity.setOnAction(event -> checkForPriceandQuantity());

        // Untuk bill_item, jika ada ENTER, panggil getPriceOfTheItem
        bill_item.setOnKeyPressed(actionEvent -> {
            if (actionEvent.getCode().equals(KeyCode.ENTER)) {
                getPriceOfTheItem();
            }
        });
    }

    public void addBillingData() {
        if (bill_item.getText().isBlank() ||
                bill_quantity.getEditor().getText().isBlank() ||
                bill_price.getText().isBlank() ||
                bill_total_amount.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the mandatory data such as item number, quantity, and price.");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();

        try {
            // Cek stok sebelum menambahkan ke billing
            String checkStockSql = "SELECT quantity FROM products WHERE item_number = ?";
            preparedStatement = connection.prepareStatement(checkStockSql);
            preparedStatement.setString(1, bill_item.getText());
            ResultSet stockResult = preparedStatement.executeQuery();

            if (stockResult.next()) {
                int availableQuantity = stockResult.getInt("quantity");
                int quantityToAdd = Integer.parseInt(bill_quantity.getEditor().getText()); // Ambil dari editor

                // Periksa apakah stok cukup
                if (availableQuantity < quantityToAdd) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Stok Habis");
                    alert.setHeaderText(null);
                    alert.setContentText("Jumlah yang ingin ditambahkan melebihi stok yang tersedia!");
                    alert.showAndWait();
                    return; // Hentikan proses jika stok tidak cukup
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Item tidak ditemukan.");
                alert.showAndWait();
                return;
            }

            // Jika cukup, lanjutkan untuk menambahkan data ke tabel BILLING
            String sql = "INSERT INTO BILLING(item_number, quantity, price, total_amount) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bill_item.getText());
            preparedStatement.setInt(2, Integer.parseInt(bill_quantity.getEditor().getText())); // Dari editor
            preparedStatement.setString(3, bill_price.getText());
            preparedStatement.setString(4, bill_total_amount.getText());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showBillingData();
                showProductsData();
                billClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add billing data.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    public ObservableList<Billing> listBilligData(){
        ObservableList<Billing> billingList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM BILLING";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);


            Billing billingData;
            while (resultSet.next()){
                billingData=new Billing(resultSet.getString("item_number"),Integer.parseInt(resultSet.getString("quantity")),Double.parseDouble(resultSet.getString("price")),Double.parseDouble(resultSet.getString("total_amount")));
                billingList.addAll(billingData);
            }


        }catch (Exception err){
            err.printStackTrace();
        }
        return billingList;
    }

    public void calculateFinalAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) AS final_amount FROM billing";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            if(resultSet.next()){
                final_amount.setText(resultSet.getString("final_amount"));
            }

        }catch (Exception err){
            err.printStackTrace();
        }

    }

    public void showBillingData(){
        ObservableList<Billing> billingList=listBilligData();
        col_bill_item_num.setCellValueFactory(new PropertyValueFactory<>("item_number"));
        col_bill_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_bill_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_bill_total_amt.setCellValueFactory(new PropertyValueFactory<>("total_amount"));

        billing_table.setItems(billingList);
        LocalDate date=LocalDate.now();
        bill_date.setValue(date);
        if(!billingList.isEmpty()){
            calculateFinalAmount();
        }else{
            final_amount.setText("0.00");
        }

    }

    @FXML
    private TableView<Product> products_table; // TableView untuk produk

    @FXML
    private TableColumn<Product, String> col_product_item_number;

    @FXML
    private TableColumn<Product, Double> col_product_price;

    @FXML
    private TableColumn<Product, Integer> col_product_stock;

    public void showProductsData() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM products"; // Ambil data yang diperlukan
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("item_number"),
                        resultSet.getString("item_group"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),   // Ambil status dari resultSet
                        resultSet.getString("supplier")   // Ambil supplier dari resultSet
                ));
            }

            col_product_item_number.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
            col_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            col_product_stock.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            products_table.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Listener untuk mengisi field ketika row dipilih
    @FXML
    public void selectProductRow() {
        Product selectedProduct = products_table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            bill_item.setText(selectedProduct.getItemNumber());
            bill_price.setText(String.valueOf((int) selectedProduct.getPrice()));
            bill_quantity.setValue("1"); // Set default quantity to 1
            checkForPriceandQuantity();
        } else {
            // Pastikan jika tidak ada produk terpilih
            bill_item.clear();
            bill_price.clear();
            bill_quantity.setValue(null);
        }
    }

    public void billClearCustomerData(){
        bill_name.setText("");
        bill_phone.setText("");
    }

    public void billClearData(){
        bill_item.clear();
        bill_quantity.setValue(null);
        bill_price.setText("");
        bill_total_amount.setText("");
    }

    public void selectBillingTableData(){
        int num=billing_table.getSelectionModel().getSelectedIndex();
        Billing billingData=billing_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }
        bill_item.setText(billingData.getItem_number());
        bill_price.setText(String.valueOf((int)billingData.getPrice()));
        bill_total_amount.setText(String.valueOf((int)billingData.getTotal_amount()));
    }
    public void updateSelectedBillingData() {
        connection = Database.getInstance().connectDB();
        String sql = "UPDATE billing SET quantity=?,price=?,total_amount=? WHERE item_number=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bill_quantity.getValue().toString());
            preparedStatement.setString(2, bill_price.getText());
            preparedStatement.setString(3, bill_total_amount.getText());
            preparedStatement.setString(4, bill_item.getText());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showBillingData();
                billClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as item number, quantity and price .");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void deleteBillingData(){
        connection = Database.getInstance().connectDB();
        String sql;
        try {
            if(billing_table.getSelectionModel().isEmpty()){
                sql = "DELETE FROM BILLING";
                preparedStatement = connection.prepareStatement(sql);
            }else{
                sql="DELETE FROM BILLING WHERE item_number=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,billing_table.getSelectionModel().getSelectedItem().getItem_number());
            }
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showBillingData();
                billClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the billing table..");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    public boolean saveCustomerDetails(){
        if(bill_phone.getText().isBlank() || bill_name.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Kindly Fill Customer Name and Phone number.");
            alert.showAndWait();
            return false;
        }
        connection = Database.getInstance().connectDB();
        String sql="SELECT * FROM CUSTOMERS WHERE phonenumber=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,bill_phone.getText());
            resultSet= preparedStatement.executeQuery();
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Customer Data is already present in customer table. Proceeding further to save invoice.");
                alert.showAndWait();
                return true;
            } else {
                String customerSql="INSERT INTO CUSTOMERS(name,phonenumber) VALUES(?,?)";
                preparedStatement = connection.prepareStatement(customerSql);
                preparedStatement.setString(1,bill_name.getText());
                preparedStatement.setString(2,bill_phone.getText());
                int result= preparedStatement.executeUpdate();
                if(result>0){
                    showCustomerData();
                    return true;
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Customer Data not saved. Please fill name and phone number correctly.");
                    alert.showAndWait();
                    return false;
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return false;
    }
    public void saveInvoiceDetails() {
        connection = Database.getInstance().connectDB();
        String customerSql = "SELECT id FROM CUSTOMERS WHERE PHONENUMBER=?";

        try {
            // Mendapatkan ID pelanggan
            preparedStatement = connection.prepareStatement(customerSql);
            preparedStatement.setString(1, bill_phone.getText());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String custId = resultSet.getString("id");

                // Mendapatkan detail billing
                String billingSql = "SELECT * FROM BILLING";
                preparedStatement = connection.prepareStatement(billingSql);
                resultSet = preparedStatement.executeQuery();

                int count = 0;

                while (resultSet.next()) {
                    String itemNumber = resultSet.getString("item_number");
                    int quantityToSell = Integer.parseInt(resultSet.getString("quantity"));

                    // Cek stok produk sebelum melakukan penyimpanan
                    String checkStockSql = "SELECT quantity FROM products WHERE item_number = ?";
                    preparedStatement = connection.prepareStatement(checkStockSql);
                    preparedStatement.setString(1, itemNumber);
                    ResultSet stockResult = preparedStatement.executeQuery();

                    if (stockResult.next()) {
                        int availableQuantity = stockResult.getInt("quantity");

                        if (availableQuantity < quantityToSell) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Stok Habis");
                            alert.setHeaderText(null);
                            alert.setContentText("Stok untuk item " + itemNumber + " tidak cukup!");
                            alert.showAndWait();
                            return; // Hentikan eksekusi jika stok tidak mencukupi
                        }
                    }

                    // Menyimpan detail penjualan
                    String salesDetailsSql = "INSERT INTO sales(inv_num, item_number, cust_id, price, quantity, total_amount, date) VALUES(?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(salesDetailsSql);
                    preparedStatement.setString(1, inv_num.getText());
                    preparedStatement.setString(2, itemNumber);
                    preparedStatement.setString(3, custId);
                    preparedStatement.setString(4, resultSet.getString("price"));
                    preparedStatement.setInt(5, quantityToSell);
                    preparedStatement.setDouble(6, Double.parseDouble(resultSet.getString("total_amount")));
                    preparedStatement.setString(7, bill_date.getValue().toString());
                    preparedStatement.executeUpdate();

                    // Kurangi stok dari tabel products
                    String updateStockSql = "UPDATE products SET quantity = quantity - ? WHERE item_number = ?";
                    preparedStatement = connection.prepareStatement(updateStockSql);
                    preparedStatement.setInt(1, quantityToSell); // jumlah yang dijual
                    preparedStatement.setString(2, itemNumber);
                    preparedStatement.executeUpdate();

                    // Periksa status dan perbarui jika perlu
                    String checkStatusSql = "UPDATE products SET status = ? WHERE item_number = ? AND quantity <= 0";
                    preparedStatement = connection.prepareStatement(checkStatusSql);
                    preparedStatement.setString(1, "Habis"); // Status baru
                    preparedStatement.setString(2, itemNumber);
                    preparedStatement.executeUpdate();

                    count++;
                }

                if (count > 0) {
                    updateStatusManually();
                    billClearCustomerData();
                    deleteBillingData();
                    showSalesData();
                    showProductsData();
                    updateLowStockCount();
                    setInvoiceNum();
                    showDashboardData();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Data is successfully saved in the sales table.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No data saved in the sales table.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in Customer Details such as Name and Phone Number correctly.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while saving invoice details: " + err.getMessage());
            alert.showAndWait();
        }
    }

    public void billSave(){
        // Save Customer Details
        if(!saveCustomerDetails()) {
            return;
        }
        //Save Invoice Details in Sales Table and Reference Customer
        saveInvoiceDetails();

    }

    public void printBill(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM `sales` s INNER JOIN customers c ON s.cust_id=c.id and s.inv_num=(SELECT MAX(inv_num) as inv_num FROM `sales`)";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/Invoice.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void searchForBills(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("bills.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void customerClearData(){
        cust_field_name.setText("");
        cust_field_phone.setText("");
    }
    public ObservableList<Customer> listCustomerData(){
        ObservableList<Customer> customersList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM Customers";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);


            Customer customer;
            while (resultSet.next()){
                customer=new Customer(Integer.parseInt(resultSet.getString("id")),resultSet.getString("name"),resultSet.getString("phonenumber"));
                customersList.addAll(customer);
            }


        }catch (Exception err){
            err.printStackTrace();
        }
        return customersList;
    }
    public void showCustomerData(){
        ObservableList<Customer> customerList=listCustomerData();
        cust_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cust_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cust_col_phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_table.setItems(customerList);
    }
    public boolean checkForCustomerAvailability(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM CUSTOMERS WHERE phoneNumber=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_phone.getText());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Customer already present in the customer table.");
                alert.showAndWait();
                return false;
            }else {
                return true;
            }
        }catch (Exception err){
            err.printStackTrace();
        }

        return false;
    }
    public void addCustomerData(){
        if(!checkForCustomerAvailability()){
            return;
        }
        connection=Database.getInstance().connectDB();
        String sql="INSERT INTO CUSTOMERS(name,phonenumber)VALUES(?,?)";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_name.getText());
            preparedStatement.setString(2,cust_field_phone.getText());
            int result=preparedStatement.executeUpdate();
            if(result>0){
                showCustomerData();
                customerClearData();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as name and phone number.");
                alert.showAndWait();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void selectCustomerTableData(){
        int num=customer_table.getSelectionModel().getSelectedIndex();
        Customer customerData=customer_table.getSelectionModel().getSelectedItem();
        if(num-1 < -1){
            return;
        }

        cust_field_name.setText(customerData.getName());
        cust_field_phone.setText(customerData.getPhoneNumber());
    }

    public void updateCustomerData(){
        if(cust_field_phone.getText().isBlank() || cust_field_name.getText().isBlank() ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill the mandatory data such as name, phone number .");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql = "UPDATE CUSTOMERS SET name=? WHERE phonenumber=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cust_field_name.getText());
            preparedStatement.setString(2, cust_field_phone.getText());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showCustomerData();
                customerClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill the mandatory data such as name, phone number .");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void deleteCustomerData(){
        if(customer_table.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select customer for deletion.");
            alert.showAndWait();
            return;
        }
        connection = Database.getInstance().connectDB();
        String sql="DELETE FROM CUSTOMERS WHERE phonenumber=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,customer_table.getSelectionModel().getSelectedItem().getPhoneNumber());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                showCustomerData();
                customerClearData();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("No data present in the customer table.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void printCustomersDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM customers";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/customers.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void getTotalSalesAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_sale_amount FROM sales";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_sale_amount");
                if (result == null) {
                    sales_total_amount.setText("0.00");
                }else{
                    sales_total_amount.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }
    public ObservableList<Sales> listSalesData(){
        ObservableList<Sales> salesList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM SALES s INNER JOIN CUSTOMERS c ON s.cust_id=c.id";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Sales sale;
            while (resultSet.next()){
                sale=new Sales(Integer.parseInt(resultSet.getString("id")),resultSet.getString("inv_num"),Integer.parseInt(resultSet.getString("cust_id")),resultSet.getString("name"),Double.parseDouble(resultSet.getString("price")),Integer.parseInt(resultSet.getString("quantity")),Double.parseDouble(resultSet.getString("total_amount")),resultSet.getString("date"),resultSet.getString("item_number"));
                salesList.addAll(sale);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return salesList;
    }
    public void showSalesData(){
        ObservableList<Sales> salesList=listSalesData();
        sales_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sales_col_inv_num.setCellValueFactory(new PropertyValueFactory<>("inv_num"));
        sales_col_cust_name.setCellValueFactory(new PropertyValueFactory<>("custName"));
        sales_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        sales_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sales_col_total_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        sales_col_date_of_sales.setCellValueFactory(new PropertyValueFactory<>("date"));
        sales_col_item_num.setCellValueFactory(new PropertyValueFactory<>("item_num"));
        sales_table.setItems(salesList);

        getTotalSalesAmount();
    }
    public void printSalesDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM sales s INNER JOIN customers c ON s.cust_id=c.id";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/sales_report.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public void getTotalPurchaseAmount(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_purchase_amount FROM purchase";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_purchase_amount");
                if (result == null) {
                    purchase_total_amount.setText("0.00");
                }else{
                    purchase_total_amount.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }
    public void printPurchaseDetails(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM purchase";
        try{
            JasperDesign jasperDesign= JRXmlLoader.load(this.getClass().getClassLoader().getResourceAsStream("jasper-reports/purchase_report.jrxml"));
            JRDesignQuery updateQuery=new JRDesignQuery();
            updateQuery.setText(sql);
            jasperDesign.setQuery(updateQuery);
            JasperReport jasperReport= JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,connection);
            JasperViewer.viewReport(jasperPrint ,false);
        }catch (Exception err){
            err.printStackTrace();
        }
    }
    public ObservableList<Purchase> listPurchaseData(){
        ObservableList<Purchase> purchaseList=FXCollections.observableArrayList();
        connection=Database.getInstance().connectDB();
        String sql="SELECT * FROM Purchase";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            Purchase purchase;
            while (resultSet.next()){
                purchase=new Purchase(Integer.parseInt(resultSet.getString("id")),resultSet.getString("invoice"),resultSet.getString("shop and address"),Integer.parseInt(resultSet.getString("total_items")),Double.parseDouble(resultSet.getString("total_amount")),resultSet.getString("date_of_purchase"));
                purchaseList.addAll(purchase);
            }
        }catch (Exception err){
            err.printStackTrace();
        }
        return purchaseList;
    }
    public void showPurchaseData(){
        ObservableList<Purchase> purchaseList=listPurchaseData();
        purchase_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchase_col_invoice.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        purchase_col_shop_details.setCellValueFactory(new PropertyValueFactory<>("shopDetails"));
        purchase_col_total_items.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        purchase_col_total_amount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        purchase_col_date_of_purchase.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));
        purchase_table.setItems(purchaseList);
        getTotalPurchaseAmount();
    }

    public void getTotalPurchase(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_items) as total_purchase FROM PURCHASE";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_purchase");
                if (result == null) {
                    dash_total_purchase.setText("0");
                }else{
                    dash_total_purchase.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    public void getTotalSales(){
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(quantity) as total_sale FROM sales";
        try{
            statement=connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String result=resultSet.getString("total_sale");
                if (result == null) {
                    dash_total_sold.setText("0");
                }else{
                    dash_total_sold.setText(result);
                }
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    public void getTotalStocks(){
        connection = Database.getInstance().connectDB();
        String sql = "SELECT SUM(quantity) AS total_quantity FROM products"; // Ganti query
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String result = resultSet.getString("total_quantity");
                if (result == null) {
                    dash_total_stocks.setText("0");
                } else {
                    dash_total_stocks.setText(result);
                }
            }
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void updateLowStockCount() {
        connection = Database.getInstance().connectDB(); // Menghubungkan ke database
        String sql = "SELECT COUNT(*) AS low_stock_count FROM products WHERE status = 'low stock'"; // Query untuk menghitung low stock

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int lowStockCount = resultSet.getInt("low_stock_count");
                dash_low_stock_items.setText(String.valueOf(lowStockCount)); // Memperbarui label dengan jumlah low stock items
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSalesDetailsOfThisMonth(){
        LocalDate date=LocalDate.now();
        String monthName=date.getMonth().toString();
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(total_amount) as total_sales_this_month FROM SALES WHERE MONTHNAME(DATE)=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,monthName);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String result=resultSet.getString("total_sales_this_month");
                if (result == null) {
                    dash_total_sales_this_month.setText("0.00");
                }else{
                    dash_total_sales_this_month.setText(result);
                }
                dash_total_sales_this_month_name.setText(monthName);
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
    public void getItemSoldThisMonth(){
        LocalDate date=LocalDate.now();
        String monthName=date.getMonth().toString();
        connection=Database.getInstance().connectDB();
        String sql="SELECT SUM(quantity) as total_items_sold_this_month FROM SALES WHERE MONTHNAME(DATE)=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,monthName);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String result=resultSet.getString("total_items_sold_this_month");
                if (result == null) {
                    dash_total_items_sold_this_month.setText("0");
                }else{
                    dash_total_items_sold_this_month.setText(result);
                }
                dash_total_sales_items_this_month_name.setText(monthName);
            }
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    public void showInventoryData() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        connection = Database.getInstance().connectDB();
        String sql = "SELECT * FROM products";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("item_number"),
                        resultSet.getString("item_group"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("status"),   // Ambil status dari resultSet
                        resultSet.getString("supplier")   // Ambil supplier dari resultSet
                ));
            }
            inventory_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            inventory_col_item_number.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
            inventory_col_item_group.setCellValueFactory(new PropertyValueFactory<>("itemGroup"));
            inventory_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status")); // Kolom Status
            inventory_col_supplier.setCellValueFactory(new PropertyValueFactory<>("supplier")); // Kolom Supplier

            inventory_table.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addInventoryItem() {
        if (inventory_field_item_number.getText().isBlank() ||
                inventory_field_item_group.getText().isBlank() ||
                inventory_field_quantity.getText().isBlank() ||
                inventory_field_price.getText().isBlank() ||
                inventory_field_supplier.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();
        String sql = "INSERT INTO products (item_number, item_group, quantity, price, supplier, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventory_field_item_number.getText());
            preparedStatement.setString(2, inventory_field_item_group.getText());
            preparedStatement.setInt(3, Integer.parseInt(inventory_field_quantity.getText()));
            preparedStatement.setDouble(4, Double.parseDouble(inventory_field_price.getText()));
            preparedStatement.setString(5, inventory_field_supplier.getText()); // Ambil nilai supplier

            // Tentukan status berdasarkan quantity
            String status;
            int quantity = Integer.parseInt(inventory_field_quantity.getText());
            if (quantity == 0) {
                status = "out of stock";
            } else if (quantity < 10) {
                status = "low stock";
            } else {
                status = "in stock";
            }
            preparedStatement.setString(6, status); // Pass status

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                updateStatusManually();
                updateLowStockCount();
                showInventoryData();
                showProductsData();
                clearInventoryFields();
                getTotalStocks();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Item added successfully!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editInventoryItem() {
        if (inventory_table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to edit!");
            alert.showAndWait();
            return;
        }

        if (inventory_field_item_number.getText().isBlank() ||
                inventory_field_item_group.getText().isBlank() ||
                inventory_field_quantity.getText().isBlank() ||
                inventory_field_price.getText().isBlank() ||
                inventory_field_supplier.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.showAndWait();
            return;
        }

        connection = Database.getInstance().connectDB();
        String sql = "UPDATE products SET item_number = ?, item_group = ?, quantity = ?, price = ?, supplier = ? WHERE id = ?";
        try {
            Product selectedProduct = inventory_table.getSelectionModel().getSelectedItem();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, inventory_field_item_number.getText());
            preparedStatement.setString(2, inventory_field_item_group.getText());
            preparedStatement.setInt(3, Integer.parseInt(inventory_field_quantity.getText()));
            preparedStatement.setDouble(4, Double.parseDouble(inventory_field_price.getText()));
            preparedStatement.setString(5, inventory_field_supplier.getText()); // Pastikan ini benar

            int quantity = Integer.parseInt(inventory_field_quantity.getText());

            // Tentukan status berdasarkan quantity
            String status;
            if (quantity == 0) {
                status = "out of stock";
            } else if (quantity < 5) {
                status = "low stock";
            } else {
                status = "in stock";
            }

//            preparedStatement.setString(6, status); // Memperbarui status
            preparedStatement.setInt(6, selectedProduct.getId()); // ID item yang diupdate

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                updateStatusManually();
                updateLowStockCount();
                showInventoryData();
                showProductsData();
                clearInventoryFields();
                getTotalStocks();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Item updated successfully!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button inventory_delete_btn; // Tambahkan Button untuk hapus di FXML

    public void deleteInventoryItem() {
        Product selectedProduct = inventory_table.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informasi");
            alert.setHeaderText(null);
            alert.setContentText("Silakan pilih item untuk dihapus.");
            alert.showAndWait();
            return;
        }

        // Menampilkan konfirmasi
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Konfirmasi Hapus");
        alertConfirm.setHeaderText(null);
        alertConfirm.setContentText("Apakah anda yakin untuk menghapus item ini dari inventory?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Jika pilihan "Ya": Hapus item dari database
            connection = Database.getInstance().connectDB();
            String sql = "DELETE FROM products WHERE id = ?";

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, selectedProduct.getId()); // ID item yang dihapus

                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukses");
                    alert.setHeaderText(null);
                    alert.setContentText("Item berhasil dihapus dari inventory.");
                    alert.showAndWait();
                    showInventoryData();
                    updateLowStockCount();
                    showProductsData();// Memperbarui tabel
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Gagal menghapus item. Silahkan coba lagi.");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Terjadi kesalahan saat menghapus item: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void updateStatusManually() {
        String sql = "UPDATE products SET status = CASE " +
                "WHEN quantity = 0 THEN 'out of stock' " +
                "WHEN quantity < 10 THEN 'low stock' " +
                "ELSE 'in stock' END";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectInventoryTableData() {
        Product selectedProduct = inventory_table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            inventory_field_item_number.setText(selectedProduct.getItemNumber());
            inventory_field_item_group.setText(selectedProduct.getItemGroup());
            inventory_field_quantity.setText(String.valueOf(selectedProduct.getQuantity()));
            inventory_field_price.setText(String.valueOf(selectedProduct.getPrice()));
            inventory_field_supplier.setText(selectedProduct.getSupplier());
        }
    }


    private void clearInventoryFields() {
        inventory_field_item_number.clear();
        inventory_field_item_group.clear();
        inventory_field_quantity.clear();
        inventory_field_price.clear();
    }


    public void showDashboardData(){
        updateLowStockCount();
        getTotalSales();
        getTotalStocks();
        getSalesDetailsOfThisMonth();
        getItemSoldThisMonth();
    }
    public void signOut(){
        signout_btn.getScene().getWindow().hide();
        try{
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }catch (Exception err){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Exports all modules to other modules
        Modules.exportAllToAll();

        setUsername();
//        showUserData();
        activateDashboard();
        updateLowStockCount();

//      DASHBOARD PANE
        showDashboardData();

//      BILLING PANE
        setAutoCompleteItemNumber();
        comboBoxQuantity();
        setInvoiceNum();
        showBillingData();
        showProductsData();

//      CUSTOMER PANE
        showCustomerData();

//      SALES PANE
        showSalesData();

//      Purchase Pane
        showPurchaseData();

//      INVENTORY PANE
//        showInventoryData();
    }
}
