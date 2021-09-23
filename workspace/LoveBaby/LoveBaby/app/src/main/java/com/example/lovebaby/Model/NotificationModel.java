package com.example.lovebaby.Model;


public class NotificationModel {

    public String to;
    public Notification notification = new Notification();
    public Data data = new Data();

    public static class Notification{
        public String title;
        public String body;
        public String click_action;
        public String sourceuid;
    }

    public static class Data{
        public String title;
        public String body;
        public String click_action;
        public String sourceuid;
    }
}
