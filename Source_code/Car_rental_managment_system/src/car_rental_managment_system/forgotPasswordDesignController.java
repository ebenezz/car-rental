/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author abenezer
 */
public class forgotPasswordDesignController implements Initializable{
    
    @FXML
    private Button bakbtn;

    @FXML
    private Button closebtn;

    @FXML
    private PasswordField conifirmpassword;

    @FXML
    private Button continueForeget;

    @FXML
    private Label errorAnswer;

    @FXML
    private Label errorLb;

    @FXML
    private Button forgot;

    @FXML
    private TextField forgotAnswer;

    @FXML
    private Button forgotBack;

    @FXML
    private Button forgotClose;

    @FXML
    private Button forgotMinimize;

    @FXML
    private ComboBox<?> forgotQuestion;

    @FXML
    private TextField forgotUsername;

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane main_form_forgot;

    @FXML
    private Button minimizebtn;

    @FXML
    private PasswordField nwpassword;

    
    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
      
     public void forgotPassword(){
         Alert alert;
         if(forgotUsername.getText().isEmpty()||forgotQuestion.getSelectionModel().getSelectedItem() == null
                 ||forgotAnswer.getText().isEmpty()){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields!");
                alert.showAndWait();
         }else{
             String checkData="SELECT username, question,answer FROM user "
                     +"WHERE username = ? AND question = ? AND answer = ?";
             connect=Database.connectDb();
             try{
                 
                 prepare = connect.prepareStatement(checkData);
                 prepare.setString(1,forgotUsername.getText());
                 prepare.setString(2,(String)forgotQuestion.getSelectionModel().getSelectedItem());
                 prepare.setString(3,forgotAnswer.getText());
                 
                 result = prepare.executeQuery();
                 
                 if(result.next()){
                    main_form.setVisible(false);
                    main_form_forgot.setVisible(true);
                    
                     
                 }else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information!");
                    alert.showAndWait();
                 }
                 
             }catch(Exception e){e.printStackTrace();}
         }
     }
     private String[] listQuestion={"What is your name?", "What is your birth place?", "What is your most favorite color?"};
    
    public void QuestionList(){
        List<String>listG = new ArrayList<>();
        
        for(String data: listQuestion){
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        
        forgotQuestion.setItems(listData);
    }
    
    
    public void switchForm(ActionEvent event){
        if(event.getSource() == continueForeget ){
            main_form.setVisible(false);
            main_form_forgot.setVisible(true);
            
            
        }
            
    }
     
    @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("userLoginDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    
    @FXML
    void back(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("forgotPasswordDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
                
     
    }
     
    public void forgot(){
        Alert alert;
    
        if(nwpassword.getText().isEmpty()||conifirmpassword.getText().isEmpty()){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("please fill all blank fields!");
                alert.showAndWait();
                
           }else if(!nwpassword.getText().equals(conifirmpassword.getText())){
               alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Pasword does not match!");
                alert.showAndWait();
           } else if(nwpassword.getText().length()<8){
               alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("invalid Pasword atleast 8 charachters needed!");
                alert.showAndWait();
             }else{
               String updateData="UPDATE user SET password = ?"+"WHERE username = '" + forgotUsername.getText()+"'";
               
               
               connect=Database.connectDb();
             try{
                 prepare = connect.prepareStatement(updateData);
                 prepare.setString(1, EncriptPassword(nwpassword.getText()));
                 
                 prepare.executeUpdate();
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information message");
                    alert.setHeaderText(null);
                    alert.setContentText("your password Successfully changed!");
                    alert.showAndWait();
                    main_form_forgot.setVisible(false);
                    main_form.setVisible(true);
                    
                    nwpassword.setText("");
                    conifirmpassword.setText("");
                    
             }catch(Exception e){e.printStackTrace();}
                 
           }     
    }
    
    private String EncriptPassword(String password){
        try{
            
            MessageDigest msg = MessageDigest.getInstance("MD5");
            
            msg.update(password.getBytes());
            
            byte[] bytes = msg.digest();
            
            StringBuilder stb = new StringBuilder();
            
            for(byte b : bytes){
                
                stb.append(Integer.toString((b & 0xff) + 0x100,16).substring(1));
                
            }
            return stb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
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

        QuestionList();
        
    }
    
}
