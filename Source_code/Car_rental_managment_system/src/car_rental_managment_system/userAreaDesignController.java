/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abenezer
 */
public class userAreaDesignController implements Initializable{
   
    @FXML
    private ImageView userImageView;
    
    @FXML
    private Button close;

    @FXML
    private AnchorPane main_form;

    @FXML
    private FontAwesomeIcon minimize;

    @FXML
    private Button rentCar_btn;

    @FXML
    private TextField rent_amount;

    @FXML
    private Label rent_balance;

    @FXML
    private ComboBox<?> rent_brand;

    @FXML
    private ComboBox<?> rent_carId;

    @FXML
    private TableColumn<carData, String> rent_col_brand;

    @FXML
    private TableColumn<carData, String> rent_col_carId;

    @FXML
    private TableColumn<carData, String> rent_col_model;

    @FXML
    private TableColumn<carData, String> rent_col_price;

    @FXML
    private TableColumn<carData, String> rent_col_status;

    @FXML
    private DatePicker rent_dateRented;

    @FXML
    private DatePicker rent_dateReturn;

    @FXML
    private TextField rent_firstName;

    @FXML
    private AnchorPane rent_form;

    @FXML
    private ComboBox<?> rent_gender;

    @FXML
    private TextField rent_lastName;

    @FXML
    private ComboBox<?> rent_model;

    @FXML
    private TableView<carData> rent_tableView;

    @FXML
    private Label rent_total;

    @FXML
    private Button rentbtn;

    @FXML
    private FontAwesomeIcon signOut_btn;

    @FXML
    private Label username;

    
     //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    private Image image;
    
    
   

    public void rentPay(){
        rentCustomerId();
        String sql = "INSERT INTO customer"
                + "(customer_id, firstName, lastName, gender, car_id, brand"
                + ", model, total, date_rented, date_return) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        connect=Database.connectDb();
        try{
            Alert alert;
            
            if(rent_firstName.getText().isEmpty()
                    || rent_lastName.getText().isEmpty()
                    || rent_gender.getSelectionModel().getSelectedItem() == null
                    || rent_carId.getSelectionModel().getSelectedItem() == null
                    || rent_brand.getSelectionModel().getSelectedItem() == null
                    || rent_model.getSelectionModel().getSelectedItem() == null
                    || totalP == 0 || rent_amount.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something wrong!");
                alert.showAndWait();
            }else{
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, rent_firstName.getText());
                    prepare.setString(3, rent_lastName.getText());
                    prepare.setString(4, (String)rent_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, (String)rent_carId.getSelectionModel().getSelectedItem());
                    prepare.setString(6, (String)rent_brand.getSelectionModel().getSelectedItem());
                    prepare.setString(7, (String)rent_model.getSelectionModel().getSelectedItem());
                    prepare.setString(8, String.valueOf(totalP));
                    prepare.setString(9, String.valueOf(rent_dateRented.getValue()));
                    prepare.setString(10, String.valueOf(rent_dateReturn.getValue()));

                    prepare.executeUpdate();
                    
                    // SET THE  STATUS OF CAR TO NOT AVAILABLE 
                    String updateCar = "UPDATE car SET status = 'Not Available' WHERE car_id = '"
                            +rent_carId.getSelectionModel().getSelectedItem()+"'";
                    
                    statement = connect.createStatement();
                    statement.executeUpdate(updateCar);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                    
                    rentCarShowListData();
                    
                    rentClear();
                } // NOW LETS PROCEED TO DASHBOARD FORM : ) 
            }
        }catch(Exception e){e.printStackTrace();}
 
        
    }
    
    public void rentClear(){
        totalP = 0;
        rent_firstName.setText("");
        rent_lastName.setText("");
        rent_gender.getSelectionModel().clearSelection();
        amount = 0;
        balance = 0;
        rent_balance.setText("ETB0.0");
        rent_total.setText("ETB0.0");
        rent_amount.setText("");
        rent_carId.getSelectionModel().clearSelection();
        rent_brand.getSelectionModel().clearSelection();
        rent_model.getSelectionModel().clearSelection();
    }
    private int customerId;
    public void rentCustomerId(){
        String sql = "SELECT id FROM customer";
        
        connect=Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                // GET THE LAST id and add + 1
                customerId = result.getInt("id") + 1;
            }
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    private double amount;
    private double balance;
    public void rentAmount(){
        Alert alert;
        if(totalP == 0){
            alert=new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid!");
            alert.showAndWait();
            
            rent_amount.setText("");
        }else{
            amount = Double.parseDouble(rent_amount.getText());
            
            if(amount >= totalP){
                balance = (amount - totalP);
                rent_balance.setText("ETB" + String.valueOf(balance));
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid!");
                alert.showAndWait();
                
                rent_amount.setText("");
            }
        }
    }
    
    
    
    
    public ObservableList<carData> rentCarListData(){
        ObservableList<carData> listData = FXCollections.observableArrayList();
        
        String sql="SELECT * FROM car WHERE status = 'Available'";
        connect = Database.connectDb();
        
        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            
            carData carD;
            while(result.next()){
                carD=new carData(result.getInt("car_id")
                        ,result.getString("brand")
                        ,result.getString("model")
                        ,result.getDouble("price")
                        ,result.getString("status")
                        ,result.getString("image")
                        ,result.getDate("date"));
                
                listData.add(carD);
            }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }
    
    private int countDate;
    public void rentDate(){
        Alert alert;
        if(rent_carId.getSelectionModel().getSelectedItem() == null
                || rent_brand.getSelectionModel().getSelectedItem() == null
                || rent_model.getSelectionModel().getSelectedItem() == null){
            alert=new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Something was wrong!");
            alert.showAndWait(); 
            
            rent_dateRented.setValue(null);
            rent_dateReturn.setValue(null);
            
        }else{
            if(rent_dateReturn.getValue().isAfter(rent_dateRented.getValue())){
                //COUNT THE DAY
            countDate = rent_dateReturn.getValue().compareTo(rent_dateRented.getValue());
            
            }else{
                alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something was wrong!");
                alert.showAndWait();
                
                //INCREASE OF 1 DAY ONCE THE USER CLICKED THE SAME DAY
                rent_dateReturn.setValue(rent_dateRented.getValue().plusDays(1));
                
            }
        }
    }
    private double totalP;
    public void rentDisplayTotal(){
        rentDate();
        double price = 0;
        String sql = "SELECT price,model FROM car WHERE model = '"
                +rent_model.getSelectionModel().getSelectedItem()+"'";
        
        connect = Database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if(result.next()){
                price = result.getDouble("price");
            }
            //PRICE * THE COUNT DAY YOU WANT TO USE THE CAR
            totalP = (price * countDate);
            //DISPLAY TOTAL BIRR
            rent_total.setText("ETB" + String.valueOf(totalP));
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    
    private String[] genderList={"Male","Female"};
    
    public void rentCarGender(){
        List<String>listG = new ArrayList<>();
        
        for(String data: genderList){
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        
        rent_gender.setItems(listData);
    }
    
    
    public void rentCarCarId(){
        
        String sql="SELECT * FROM car WHERE status='Available'";
        
        connect = Database.connectDb();
        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();
            
            while(result.next()){
                listData.add(result.getString("car_id"));
                
            }
            
            rent_carId.setItems(listData);
            
            rentCarCarBrand();
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    public void rentCarCarBrand(){
        
        String sql = "SELECT * FROM car WHERE car_id = '"
                + rent_carId.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("brand"));
            }

            rent_brand.setItems(listData);

            rentCarCarModel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void rentCarCarModel(){
        
        String sql = "SELECT * FROM car WHERE brand = '"
                + rent_brand.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("model"));
            }

            rent_model.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private ObservableList<carData> rentCarList;  
    public void rentCarShowListData(){
        
        rentCarList=rentCarListData();
        
        rent_col_carId.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        rent_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        rent_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        rent_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        rent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        rent_tableView.setItems(rentCarList);
        
    } 
    
    
    
   public void displayUsername(){
       String user=getData.username;
       //TO SET THE FIRST LETTER TO BIG LETTER
       username.setText(user.substring(0, 1).toUpperCase()+user.substring(1));
       
   }
    private double x=0;
    private double y=0;
    public void logout(){
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to signout?");
        Optional<ButtonType> option=alert.showAndWait();
        try{
            if(option.get().equals(ButtonType.OK)){
                //HIDE OUR DASHBOARD FORM
                signOut_btn.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("userLoginDesign.fxml"));
                 Stage stage=new Stage();
                 Scene scene = new Scene(root);
        
            root.setOnMousePressed((MouseEvent event) ->{
                x=event.getSceneX();
                y=event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.7);
                });
                root.setOnMouseReleased((MouseEvent event) ->{
                     stage.setOpacity(1);
            
               });
                stage.initStyle(StageStyle.TRANSPARENT);
        
                stage.setScene(scene);
                stage.show();
        }
        }catch(Exception e){e.printStackTrace();}
    }
    
    
    
    public void signin(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                signOut_btn.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("CustomerDataDesign.fxml"));
                 Stage stage=new Stage();
                 Scene scene = new Scene(root);
        
            root.setOnMousePressed((MouseEvent event) ->{
                x=event.getSceneX();
                y=event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.7);
                });
                root.setOnMouseReleased((MouseEvent event) ->{
                     stage.setOpacity(1);
            
               });
                stage.initStyle(StageStyle.TRANSPARENT);
        
                stage.setScene(scene);
                stage.show();
        
        }catch(Exception e){e.printStackTrace();}
    }
    
    
    public void rentCarSelect(){
        carData carD=rent_tableView.getSelectionModel().getSelectedItem();
        int num=rent_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < - 1){return;}
        
        
        getData.path=carD.getImage();
        
        String uri="file:"+carD.getImage();
        image=new Image(uri, 153, 194, false, true );
        userImageView.setImage(image);
        
    }
    
    
    public void switchForm(ActionEvent event){
        

        if(event.getSource()==rentCar_btn){
            
            rent_form.setVisible(true);
            
            rentCar_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #d0dd15, #26617c);");
           
            
            rentCarShowListData();
            rentCarCarId();
            rentCarCarBrand();
            rentCarCarModel();
            rentCarGender();
        }
    }
    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage=(Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        
        
        
        
        //TO DISPLAY DATA ON THE TABLE VIEW
        
        rentCarShowListData();
        rentCarCarId();
        rentCarCarBrand();
        rentCarCarModel();
        rentCarGender();
        
        
    }

    
    
}
