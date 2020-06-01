package com.home.ecommerce.Domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {
    @NotBlank(message="Order IDs cannot be null")
    private String orderIdsString;
    private String[] orderIdsStringArray = new String[10];
    @NotBlank(message = "Quantities cannot be null3")
    private String quantitiesString;
    private String[] quantitiesStringArray = new String[10];
    private List<Integer> orderIdList = new ArrayList<>();
    private List<Integer> quantityList = new ArrayList<>();

    public String getOrderIdsString() {
        return orderIdsString;
    }

    public void setOrderIdsString(String orderIdsString) {
        this.orderIdsString = orderIdsString;
    }

    public String[] getOrderIdsStringArray() {
        return orderIdsString.split(",");
    }

    public void setOrderIdsStringArray(String[] orderIdsStringArray) {
        this.orderIdsStringArray = orderIdsStringArray;
    }

    public String getQuantitiesString() {
        return quantitiesString;
    }

    public void setQuantitiesString(String quantitiesString) {
        this.quantitiesString = quantitiesString;
    }

    public String[] getQuantitiesStringArray() {
        return quantitiesStringArray;
    }

    public void setQuantitiesStringArray(String[] quantitiesStringArray) {
        this.quantitiesStringArray = quantitiesStringArray;
    }






    public List<Integer> getOrderIdList() {
        orderIdsStringArray = orderIdsString.split(",");
        for(int i=0;i<orderIdsStringArray.length;i++){
            orderIdList.add(Integer.parseInt(orderIdsStringArray[i]));
        }
        return  orderIdList;
    }

    public void setOrderIdList(List<Integer> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public List<Integer> getQuantityList() {
        quantitiesStringArray = quantitiesString.split(",");
        for(int i=0;i<quantitiesStringArray.length;i++){
            quantityList.add(Integer.parseInt(quantitiesStringArray[i]));
        }
        return  quantityList;
    }

    public void setQuantityList(List<Integer> quantityList) {
        this.quantityList = quantityList;
    }
}
