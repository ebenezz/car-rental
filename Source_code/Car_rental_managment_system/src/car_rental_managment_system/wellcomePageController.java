/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abenezer
 */
public class wellcomePageController implements Initializable {
    @FXML
    private Button wellcomeClose;

    @FXML
    private AnchorPane wellcomeForm;

    @FXML
    private Button wellcomeLoginAdmin;

    @FXML
    private Button wellcomeLoginUser;

    @FXML
    private Button wellcomeMinimaize;
    
    
    public void close(){
        System.exit(0);
    }
    
    public void minimize(){
        Stage stage=(Stage)wellcomeForm.getScene().getWindow();
        stage.setIconified(true);
    }
    
    private double x=0;
    private double y=0;
    public void goToLogin(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                wellcomeLoginAdmin.getScene().getWindow().hide();
                
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
        
        }catch(Exception e){e.printStackTrace();}
    
    }
    
    public void GoTouserLoginDesign(){
      
        try{
            
                //HIDE OUR DASHBOARD FORM
                wellcomeLoginUser.getScene().getWindow().hide();
                
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
        
        }catch(Exception e){e.printStackTrace();}
    
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
