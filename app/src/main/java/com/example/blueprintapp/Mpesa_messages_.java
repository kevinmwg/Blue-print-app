package com.dev.bilo.myapplication;

public class Mpesa_messages_ {
    String phone,payment_status,payment_code,date_time;
    public Mpesa_messages_(String phone, String payment_status, String payment_code, String date_time) {
        this.phone = phone;
        this.payment_status = payment_status;
        this.payment_code = payment_code;
        this.date_time = date_time;
    }

    public String getPhone(){
        return phone;
    }
    public String getPayment_status(){
        return payment_status;
    }

    public String getPayment_code(){
        return payment_code;
    }

    public String getDate(){
        return date_time;
    }



}
