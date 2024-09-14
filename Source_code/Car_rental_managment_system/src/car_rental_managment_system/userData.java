/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

/**
 *
 * @author abenezer
 */
public class userData {
    private String username;
    private String password;
    private String question;
    private String answer;
    
    public userData(String username, String password, String question, String answer){
        
        this.username=username;
        this.password=password;
        this.question=question;
        this.answer=answer;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getQuestion(){
        return question;
    }
    public String getAnswer(){
        return answer;
    }
}
