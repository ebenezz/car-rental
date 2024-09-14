/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private FontAwesomeIcon close;
    public void close(){
        System.exit(0);
    }
      @FXML
    private Button adminBackbtn;

    @FXML
    private CheckBox checkBoxShowPassword;

    @FXML
    private FontAwesomeIcon close1;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_fom;

    @FXML
    private PasswordField password;

    @FXML
    private TextField showPassword;

    @FXML
    private TextField username;
   
    //Database Tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x=0;
    private double y=0;
    public void LoginAdmin(){
        
        String sql="SELECT*FROM admin WHERE username=? and password=?";
        connect=Database.connectDb();
        
        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, EncriptPassword(password.getText()));
            
            result = prepare.executeQuery();
            Alert alert;
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert=new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
                
            }else{
                if(result.next()){
                    
                    getData.username=username.getText();
                    
                    alert=new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                    //HIDE YOUR LOGIN FORM
                    loginBtn.getScene().getWindow().hide();
                    //LINK YOUR DASHBORED FORM
                    Parent root = FXMLLoader.load(getClass().getResource("dashboardDesign.fxml"));
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
                    alert=new Alert(AlertType.ERROR);
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
    
    
    public void BackToStartPage(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                adminBackbtn.getScene().getWindow().hide();
                
                //LINK OUR LOGIN FORM
                 Parent root = FXMLLoader.load(getClass().getResource("wellcomePage.fxml"));
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
    
    
    public void minimize(){
        Stage stage=(Stage)main_fom.getScene().getWindow();
        stage.setIconified(true);
    }
    public void handelShowPassword(){
        if(checkBoxShowPassword.isSelected()){
            showPassword.setText(password.getText());
            showPassword.setVisible(true);
            password.setVisible(false);
            //show the password
        }else{
            password.setText(showPassword.getText());
            showPassword.setVisible(false);
            password.setVisible(true);
            //hide the password
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
