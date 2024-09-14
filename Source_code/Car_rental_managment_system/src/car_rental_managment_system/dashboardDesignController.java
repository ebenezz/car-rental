/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.Node;
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
public class dashboardDesignController implements Initializable{
    @FXML
    private Button AddAdmin;

    @FXML
    private Button DeleteAdmin;

    @FXML
    private TextField admin_id;
    
     @FXML
    private Button manageAdmin;
     
    @FXML
    private TextField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private TextField availableCar_brand;

    @FXML
    private Button availableCar_btn;

    @FXML
    private TextField availableCar_carId;

    @FXML
    private Button availableCar_clearBtn;

    @FXML
    private TableColumn<carData,String> availableCar_col_brand;

    @FXML
    private TableColumn<carData,String> availableCar_col_carId;

    @FXML
    private TableColumn<carData,String> availableCar_col_model;

    @FXML
    private TableColumn<carData,String> availableCar_col_price;

    @FXML
    private TableColumn<carData,String> availableCar_col_status;

    @FXML
    private Button availableCar_deleteBtn;

    @FXML
    private AnchorPane availableCar_form;

    @FXML
    private ImageView availableCar_imageView;

    @FXML
    private Button availableCar_importBtn;

    @FXML
    private Button availableCar_insertBtn;

    @FXML
    private TextField availableCar_model;

    @FXML
    private TextField availableCar_price;

    @FXML
    private TextField availableCar_search;

    @FXML
    private ComboBox<?> availableCar_status;

    @FXML
    private Button availableCar_updateBtn;

    @FXML
    private TableView<carData> available_tableView;

    @FXML
    private Button close;

    @FXML
    private Label home_availableCars;

    @FXML
    private Button home_btn;

    @FXML
    private LineChart<?, ?> home_customerChart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private BarChart<?, ?> home_incomeChart;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private AnchorPane main_form;

    @FXML
    private FontAwesomeIcon minimize;

    @FXML
    private FontAwesomeIcon signInBtn;

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
    
    
    public void HomeAvailableCars(){
        
        String sql = "SELECT COUNT(id) FROM car WHERE status = 'Available'";
        
        connect = Database.connectDb();
        int countAC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countAC = result.getInt("COUNT(id)");
            }
           
            home_availableCars.setText(String.valueOf(countAC));
            
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeTotalIncome(){
        
        String sql = "SELECT SUM(total) FROM customer";
        
        double sumIncome = 0;
        
        connect = Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                sumIncome = result.getDouble("SUM(total)");
            }
            home_totalIncome.setText("ETB" + String.valueOf(sumIncome));
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void homeTotalCustomers(){
        
        String sql = "SELECT COUNT(id) FROM customer";
        
        int countTC = 0;
        
        connect = Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }
            home_totalCustomers.setText(String.valueOf(countTC));
        }catch(Exception e){e.printStackTrace();}
        
    }

    public void homeIncomeChart(){
        
        home_incomeChart.getData().clear();
        
        String sql = "SELECT date_rented, SUM(total) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 6";
        
        connect = Database.connectDb();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_incomeChart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
    }
//    }THAT IS IT FOR THIS VIDEO, THANKS FOR WATCHING! 
//     DONT FORGET TO SUBSCRIBE OUR CHANNEL FOR MORE UNIQUE TUTORIALS : ) 
//     THANKS FOR THE SUPPORT GUYS!
    
    
    public void homeCustomerChart(){
        home_customerChart.getData().clear();
        
        String sql = "SELECT date_rented, COUNT(id) FROM customer GROUP BY date_rented ORDER BY TIMESTAMP(date_rented) ASC LIMIT 4";
        
        connect = Database.connectDb();
        
        try{
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
            home_customerChart.getData().add(chart);
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
   
    public void availableCarAdd() throws ClassNotFoundException{
  
        try{
        String sql = "INSERT INTO car (car_id, brand, model, price, status, image, date) "
                + "VALUES(?,?,?,?,?,?,?)";
        
        connect=Database.connectDb();
        
        
            Alert alert;
            
            if(availableCar_carId.getText().isEmpty()
                    || availableCar_brand.getText().isEmpty()
                    || availableCar_model.getText().isEmpty()
                    || availableCar_status.getSelectionModel().getSelectedItem() ==null
                    || availableCar_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert=new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields!");
                alert.showAndWait();
            }else{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableCar_carId.getText());
                prepare.setString(2, availableCar_brand.getText());
                prepare.setString(3, availableCar_model.getText());
                prepare.setString(4, availableCar_price.getText());
                prepare.setString(5, (String)availableCar_status.getSelectionModel().getSelectedItem());
                 
                String uri=getData.path;
                uri=uri.replace("\\" , "\\\\");
                
                prepare.setString(6, uri);
                Date date=new Date();
                
                java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                
                prepare.setString(7, String.valueOf(sqlDate));
                
                prepare.executeUpdate();
                
                alert=new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();
                
                availableCarShowListData();
                availableCarClear();
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    @FXML
    void GoToAdmin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("manageAdminDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    
    
    
    
    
    public void availableCarUpdate(){
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE car SET brand = '" + availableCar_brand.getText() + "', model = '"
                + availableCar_model.getText() + "', status ='"
                + availableCar_status.getSelectionModel().getSelectedItem() + "', price = '"
                + availableCar_price.getText() + "', image = '" + uri
                + "' WHERE car_id = '" + availableCar_carId.getText() + "'";

        connect = Database.connectDb();

        try {
            Alert alert;

            if (availableCar_carId.getText().isEmpty()
                    || availableCar_brand.getText().isEmpty()
                    || availableCar_model.getText().isEmpty()
                    || availableCar_status.getSelectionModel().getSelectedItem() == null
                    || availableCar_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Car ID: " + availableCar_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    
                    availableCarShowListData();
                    availableCarClear();
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void availableCarDelete(){
        String sql = "DELETE FROM car WHERE car_id = '" + availableCar_carId.getText() + "'";

        connect = Database.connectDb();

        try {
            Alert alert;
            if (availableCar_carId.getText().isEmpty()
                    || availableCar_brand.getText().isEmpty()
                    || availableCar_model.getText().isEmpty()
                    || availableCar_status.getSelectionModel().getSelectedItem() == null
                    || availableCar_price.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Car ID: " + availableCar_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    availableCarShowListData();
                    availableCarClear();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void availableCarClear(){
        availableCar_carId.setText("");
        availableCar_brand.setText("");
        availableCar_model.setText("");
        availableCar_price.setText("");
        availableCar_status.getSelectionModel().clearSelection();
        
        getData.path="";
        availableCar_imageView.setImage(null);
        
        
    }
    
    private String[] listStatus={"Available", "Not Available"};
    public void availableCarStatusList(){
        
        List<String> listS=new ArrayList<>();
        
        for(String data:listStatus){
            listS.add(data);
        }
        
        ObservableList listData=FXCollections.observableArrayList(listS);
        availableCar_status.setItems(listData);
    }
    
    public void availableCarImportImage(){
        FileChooser open=new FileChooser();
        open.setTitle("Open an image file");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));
        
        
        File file=open.showOpenDialog(main_form.getScene().getWindow());
        if(file !=null){
            
            getData.path=file.getAbsolutePath();
            
            image=new Image(file.toURI().toString(), 204, 194, false, true);
            availableCar_imageView.setImage(image);
        }
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
    
    
    
    public ObservableList<carData> availableCarListData(){
        ObservableList<carData> listData=FXCollections.observableArrayList();
        String sql="SELECT*FROM car";
        
        connect=Database.connectDb();
        
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
    private ObservableList<carData> availableCarList;
    public void availableCarShowListData(){
        availableCarList=availableCarListData();
        
        availableCar_col_carId.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        availableCar_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        availableCar_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        availableCar_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableCar_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        available_tableView.setItems(availableCarList);
        
        
    }
    
     public void availableCarSearch(){
         
         FilteredList<carData> filter=new FilteredList<>(availableCarList, e -> true);
         
         availableCar_search.textProperty().addListener((Observable,oldValue,newValue) ->{
             filter.setPredicate(predicateCarData ->{
                
                 if(newValue==null||newValue.isEmpty()){
                     return true;
                 }
                 String searchkey=newValue.toLowerCase();
                 
                 if(predicateCarData.getCar_id().toString().contains(searchkey)){
                     
                     return true;
                     }else if(predicateCarData.getBrand().toLowerCase().contains(searchkey)){
                     return true;
                 /*}else if(predicateCarData.getModel().toLowerCase().contains(searchkey)){
                     return true;
                 }else if(predicateCarData.getPrice().toString().contains(searchkey)){
                     return true;
                 }else if(predicateCarData.getStatus().toLowerCase().contains(searchkey)){
                     return true;
                 */
                 }else return false;
             });
         });
         SortedList<carData> sortList = new SortedList<>(filter);
         
         sortList.comparatorProperty().bind(available_tableView.comparatorProperty());
         available_tableView.setItems(sortList);
         
     }
    
    public void availableCarSelect(){
        carData carD=available_tableView.getSelectionModel().getSelectedItem();
        int num=available_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < - 1){return;}
        
        availableCar_carId.setText(String.valueOf(carD.getCar_id()));
        availableCar_brand.setText(carD.getBrand());
        availableCar_model.setText(carD.getModel());
        availableCar_price.setText(String.valueOf(carD.getPrice()));
        
        getData.path=carD.getImage();
        
        String uri="file:"+carD.getImage();
        image=new Image(uri, 153, 194, false, true );
        availableCar_imageView.setImage(image);
        
    }
    
    public ObservableList<carData> rentCarListData(){
        ObservableList<carData> listData = FXCollections.observableArrayList();
        
        String sql="SELECT * FROM car";
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
                 Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
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
    
    
    
    public void switchForm(ActionEvent event){
        if(event.getSource()==home_btn){
            home_form.setVisible(true);
            availableCar_form.setVisible(false);
            
            
            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #d0dd15, #26617c);");
            availableCar_btn.setStyle("-fx-background-color:transparent");
            
            
            HomeAvailableCars();
            homeTotalIncome();
            homeTotalCustomers();
            homeCustomerChart();
            homeIncomeChart();
            
        }else if(event.getSource()==availableCar_btn){
            home_form.setVisible(false);
            availableCar_form.setVisible(true);
            
             
            availableCar_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #d0dd15, #26617c);");
            home_btn.setStyle("-fx-background-color:transparent");
            
            
            //TO UPDATE YOUR TABLE VIEW ONCE YOU CLICK AVAILABLE CAR NAVIGATION BUTTON
            availableCarShowListData();
            availableCarStatusList();
            availableCarSearch();

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
        
        
        HomeAvailableCars();
        homeTotalIncome();
        homeTotalCustomers();
        homeCustomerChart();
        homeIncomeChart();
        
        //TO DISPLAY DATA ON THE TABLE VIEW
        availableCarShowListData();
        availableCarStatusList();
        availableCarSearch();
        
        
        
    }

    
    
}
