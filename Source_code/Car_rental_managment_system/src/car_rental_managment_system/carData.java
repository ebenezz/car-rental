/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental_managment_system;

import java.sql.Date;

/**
 *
 * @author abenezer
 */
public class carData {
    
    private Integer car_id;
    private String brand;
    private Double price;
    private String model;
    private Date date;
    private String status;
    private String image;
    
    public carData(Integer car_id,String brand,String model
            ,Double price,String status,String image,Date date){
        this.car_id=car_id;
        this.brand=brand;
        this.model=model;
        this.price=price;                                                           
        this.status=status;
        this.image=image;
        this.date=date;
        
    }

    carData(int aInt, String string, String string0, double aDouble, String string1, Date date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Integer getCar_id(){
        return car_id;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public Double getPrice(){
        return price;
    }
    public String getStatus(){
        return status;
    }
    public String getImage(){
        return image;
    }
    public Date getDate(){
        return date;
    }
    
}
