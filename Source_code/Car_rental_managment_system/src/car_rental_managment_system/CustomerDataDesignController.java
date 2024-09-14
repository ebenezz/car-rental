/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abenezer
 */
public class CustomerDataDesignController implements Initializable {

    @FXML
    private Button customerDataTable_backBtn;

    @FXML
    private Button customerDataTable_closeBtn;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_brand;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_car_id;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_cust_id;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_date_rented;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_date_return;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_firstName;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_lastName;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_model;

    @FXML
    private TableColumn<customerData, String> customerDataTable_col_total;

    @FXML
    private TableColumn<customerData, String> customerDataTable_gender;

    @FXML
    private FontAwesomeIcon customerDataTable_minimizeBtn;

    @FXML
    private TextField customerDataTable_search;
    @FXML
    private AnchorPane customerDataTable_mainForm;

    @FXML
    private TableView<customerData> customerDataTable_tableView;
    
    
     //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    public ObservableList<customerData>customersListData(){
        ObservableList<customerData> listData=FXCollections.observableArrayList();
        String sql="SELECT*FROM customer";
        
        connect=Database.connectDb();
        
        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            
            customerData customerD;
            
                while(result.next()){
                    customerD=new customerData(result.getInt("customer_id")
                            ,result.getString("firstName")
                            ,result.getString("lastName")
                            ,result.getString("gender")
                            ,result.getInt("car_id")
                            ,result.getString("brand")
                            ,result.getString("model")
                            ,result.getDouble("total")
                            ,result.getDate("date_rented")
                            ,result.getDate("date_return"));
                    listData.add(customerD);
                }
            
        }catch(Exception e){e.printStackTrace();}
        return listData;
    }   
    
    
    private ObservableList<customerData> customerslist;
    public void customerShowListData(){
        customerslist = customersListData();
        
        customerDataTable_col_cust_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerDataTable_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customerDataTable_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customerDataTable_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        customerDataTable_col_car_id.setCellValueFactory(new PropertyValueFactory<>("car_id"));
        customerDataTable_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        customerDataTable_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        customerDataTable_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customerDataTable_col_date_rented.setCellValueFactory(new PropertyValueFactory<>("date_rented"));
        customerDataTable_col_date_return.setCellValueFactory(new PropertyValueFactory<>("date_return"));
        
        
        customerDataTable_tableView.setItems(customerslist);
    }
    
    
    public void customerDataSearch(){
         
         FilteredList<customerData> filter=new FilteredList<>(customerslist, e -> true);
         
         customerDataTable_search.textProperty().addListener((Observable,oldValue,newValue) ->{
             filter.setPredicate(predicateCustomerData ->{
                
                 if(newValue==null||newValue.isEmpty()){
                     return true;
                 }
                 String searchkey=newValue.toLowerCase();
                 
                 if(predicateCustomerData.getCustomer_id().toString().contains(searchkey)){
                     
                     return true;
                }else if(predicateCustomerData.getBrand().toLowerCase().contains(searchkey)){
                return true;
                }else if(predicateCustomerData.getCar_id().toString().contains(searchkey)){
                   return true;
               }else return false;
             });
         });
         SortedList<customerData> sortList = new SortedList<>(filter);
         
         sortList.comparatorProperty().bind(customerDataTable_tableView.comparatorProperty());
         customerDataTable_tableView.setItems(sortList);
         
     }
    
    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage=(Stage)customerDataTable_mainForm.getScene().getWindow();
        stage.setIconified(true);
    }
    
    private double x=0;
    private double y=0;
    public void backToHome(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                customerDataTable_backBtn.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("dashboardDesign.fxml"));
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //TO DISPLAY ON THE TABLE
        customerShowListData();
        customerDataSearch();
        
    }
    
}
