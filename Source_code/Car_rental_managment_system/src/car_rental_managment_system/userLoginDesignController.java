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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abenezer
 */
public class userLoginDesignController implements Initializable {
      @FXML
    private Button BackbtnToRegister;

    @FXML
    private Button GoToAboutUs;

    @FXML
    private CheckBox checkBoxShowPassword;

    @FXML
    private Button registerBack;

    @FXML
    private TextField showPassword;

    @FXML
    private Button userForgotbtn;

    @FXML
    private Button userLoginClose;

    @FXML
    private AnchorPane userLoginForm;

    @FXML
    private Button userLoginMinimize;

    @FXML
    private PasswordField userLoginPasswordFeild;

    @FXML
    private TextField userLoginTextField;

    @FXML
    private Button userLoginbtn;


    @FXML
    void GoToAboutUs(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("aboutUsDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
   @FXML
    void GoTowellcomePage(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("wellcomePage.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage=(Stage)userLoginForm.getScene().getWindow();
        stage.setIconified(true);
    }
    //Database Tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x=0;
    private double y=0;
    public void LoginUser(){
        
        String sql="SELECT*FROM user WHERE username=? and password=?";
        connect=Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, userLoginTextField.getText());
            prepare.setString(2, EncriptPassword(userLoginPasswordFeild.getText()));
            
            result = prepare.executeQuery();
            Alert alert;
            if(userLoginTextField.getText().isEmpty() || userLoginPasswordFeild.getText().isEmpty()){
                alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
                
            }else{
                if(result.next()){
                    
                    getData.username=userLoginTextField.getText();
                    
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                    //HIDE YOUR LOGIN FORM
                    userLoginbtn.getScene().getWindow().hide();
                    //LINK YOUR DASHBORED FORM
                    Parent root = FXMLLoader.load(getClass().getResource("userAreaDesign.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                      x=event.getSceneX();
                      y=event.getSceneY();
                    });
                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() -x);
                        stage.setY(event.getScreenY() -y);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                }else{
                    alert=new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password");
                    alert.showAndWait();
                }
                
            }
            
                
                
        }catch(Exception e){e.printStackTrace();}
        
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
    
    public void GoToRegister(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                registerBack.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("registerDesignForm.fxml"));
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
    
    public void GoToForgotPassword(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                userForgotbtn.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("forgotPasswordDesign.fxml"));
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
    
    public void handelShowPw(){
        if(checkBoxShowPassword.isSelected()){
            showPassword.setText(userLoginPasswordFeild.getText());
            showPassword.setVisible(true);
            userLoginPasswordFeild.setVisible(false);
            //show the password
        }else{
            userLoginPasswordFeild.setText(showPassword.getText());
            showPassword.setVisible(false);
            userLoginPasswordFeild.setVisible(true);
            //hide the password
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
handelShowPw();

    }
    
}
