/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author abenezer
 */
public class aboutUsDesignController implements Initializable{

    
    @FXML
    private Button aboutBack;

    @FXML
    private Hyperlink facebook;

    @FXML
    private Hyperlink instagram;

    @FXML
    private Hyperlink twitter;
    
    @FXML
    private AnchorPane main_form;

    

   @FXML
    void aboutBack(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("userLoginDesign.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    @FXML
    void openlink(ActionEvent event)throws IOException, URISyntaxException{
        Desktop.getDesktop().browse(new URI("http://www.Facebook.com"));
        
    }
    @FXML
    void openlinkinstagram(ActionEvent event)throws IOException, URISyntaxException{
        Desktop.getDesktop().browse(new URI("http://www.Instagram.com"));
        
    }
    @FXML
    void openlinktwitter(ActionEvent event)throws IOException, URISyntaxException{
        Desktop.getDesktop().browse(new URI("http://www.Twitter.com"));
        
    }
    
 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       

    }
}
    

