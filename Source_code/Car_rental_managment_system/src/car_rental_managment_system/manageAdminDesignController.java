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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author abenezer
 */
public class manageAdminDesignController implements Initializable {
   @FXML
    private TextField deleteAdminIDTextField;

    @FXML
    private PasswordField addAdminPasswordField;

    @FXML
    private TextField addAdminTextField;

    @FXML
    private Button addAdminbtn;
    
    @FXML
    private AnchorPane main_form;


    @FXML
    private PasswordField addAdmincoPasswordField;

    @FXML
    private Button bakbtn;

    @FXML
    private Button closebtn;

    @FXML
    private Button deletAdminbtn;

    @FXML
    private Button minimizebtn;
    
     //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    
    public void AddAdmin() throws ClassNotFoundException{
    
        Alert alert;
          if(addAdminTextField.getText().isEmpty()||addAdminPasswordField.getText().isEmpty()){
              
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("all fields are necessarilly field!");
                    alert.showAndWait();
          }else if(!addAdminPasswordField.getText().equals(addAdmincoPasswordField.getText())){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("password does not match!");
                    alert.showAndWait();
              
          }else if(addAdminPasswordField.getText().length()<8){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, at least 8 charachter needed!");
                    alert.showAndWait(); 
            }else{
                String checkAdminUsername ="SELECT * FROM admin WHERE username = '"
                       + addAdminTextField.getText() + "' ";
                connect=Database.connectDb();
              try{
                  statement = connect.createStatement();
                  result = statement.executeQuery(checkAdminUsername);
                  if(result.next()){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(addAdminTextField.getText()+ " is already taken!");
                    alert.showAndWait(); 
                    
                  }else{
                      
                                String sql = "INSERT INTO admin (username, password) "
                       + "VALUES(?,?)";

                         connect=Database.connectDb();

                       prepare = connect.prepareStatement(sql);
                       prepare.setString(1, addAdminTextField.getText());
                       prepare.setString(2, EncriptPassword(addAdminPasswordField.getText()));

                       prepare.executeUpdate();

                       alert=new Alert(Alert.AlertType.INFORMATION);
                       alert.setTitle("Information Message");
                       alert.setHeaderText(null);
                       alert.setContentText("you Successfully Added a new Admin!");
                       alert.showAndWait();
                       clearAdminFields();
                    }
              }catch(Exception e){e.printStackTrace();}
            }
    }
    
    
    
    
    public void clearAdminFields(){
        addAdminPasswordField.setText("");
        addAdminTextField.setText("");
        deleteAdminIDTextField.setText("");
        addAdmincoPasswordField.setText("");
    }
    
    public void DeleteAdmin() throws ClassNotFoundException{
    
            Alert alert;
              if(deleteAdminIDTextField.getText().isEmpty()){
              
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("fields Can not Empty!");
                    alert.showAndWait();
                }else{
                    String checkAdminID =" SELECT * FROM admin WHERE id = '"
                           + deleteAdminIDTextField.getText() + "' ";
                    connect=Database.connectDb();
                   try{
                      statement = connect.createStatement();
                      result = statement.executeQuery(checkAdminID);
                      if(!(result.next())){
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(deleteAdminIDTextField.getText()+ " the ID you entered does not match!");
                        alert.showAndWait(); 

                       }else{
                          try{
                          String sql = " DELETE FROM admin WHERE id = '" + deleteAdminIDTextField.getText() + "' ";

                            connect = Database.connectDb();

                            alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to DELETE admin id: " + deleteAdminIDTextField.getText() + " ? ");
                            Optional<ButtonType> option = alert.showAndWait();

                            if (option.get().equals(ButtonType.OK)) {
                                statement = connect.createStatement();
                                statement.executeUpdate(sql);

                                alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information Message");
                                alert.setHeaderText(null);
                                alert.setContentText("Successfully Deleted admin!");
                                alert.showAndWait();
                            
                   
                             }    
                           }catch(Exception e){e.printStackTrace();}
                        }
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
    
    
    @FXML
    void back(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("dashboardDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
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

    }
    
}
