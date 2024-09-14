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
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jfree.chart.plot.dial.DialPointer.Pin;

/**
 *
 * @author abenezer
 */
public class registerFormDesginController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private TextField registerAnswer;

    @FXML
    private Button registerBack;

    @FXML
    private Button registerBack1;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private ComboBox<?> registerQuestion;

    @FXML
    private Button registerRegister;

    @FXML
    private TextField registerUsername;
    
     //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage=(Stage)registerForm.getScene().getWindow();
        stage.setIconified(true);
    }
    
    
     @FXML
    void backToUserLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("userLoginDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    
    public void Register()throws ClassNotFoundException{
             Alert alert;
          if(registerUsername.getText().isEmpty()||registerPassword.getText().isEmpty()
                  || registerQuestion.getSelectionModel().getSelectedItem()==null
                  || registerAnswer.getText().isEmpty()){
              
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("all fields are necessarilly field!");
                    alert.showAndWait();
          }else if(registerPassword.getText().length()<8){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, at least 8 charachter needed!");
                    alert.showAndWait(); 
            }else{
                String checkUsername ="SELECT * FROM user WHERE username = '"
                       + registerUsername.getText() + "' ";
                connect=Database.connectDb();
              try{
                  statement = connect.createStatement();
                  result = statement.executeQuery(checkUsername);
                  if(result.next()){
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(registerUsername.getText()+ " is already taken!");
                    alert.showAndWait(); 
                    
                  }else{
                      
                        String sql = "INSERT INTO user (username, password, question, answer) "
                                + "VALUES(?,?,?,?)";

                        connect=Database.connectDb();
        
                        prepare = connect.prepareStatement(sql);
                        prepare.setString(1, registerUsername.getText());
                        prepare.setString(2, EncriptPassword(registerPassword.getText()));
                        prepare.setString(3, (String)registerQuestion.getSelectionModel().getSelectedItem());
                        prepare.setString(4, registerAnswer.getText());

                        prepare.executeUpdate();

                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully registered!");
                        alert.showAndWait();
                        registerClear();

                
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
    
   
    private String[] listQuestion={"What is your name?", "What is your birth place?", "What is your most favorite color?"};
    
    public void registerQuestionList(){
        List<String>listG = new ArrayList<>();
        
        for(String data: listQuestion){
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        
        registerQuestion.setItems(listData);
    }
    
    public void registerClear(){
                registerUsername.setText("");
                registerPassword.setText("");
                registerQuestion.getSelectionModel().clearSelection();
        
               getData.path="";
                registerAnswer.setText("");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        
        registerQuestionList();
        
    }
    
}
