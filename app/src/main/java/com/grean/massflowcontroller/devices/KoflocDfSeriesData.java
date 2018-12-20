package com.grean.massflowcontroller.devices;

import java.util.ArrayList;

/**
 * Created by weifeng on 2018/12/14.
 */

public class KoflocDfSeriesData {
    private ArrayList<Controller> list = new ArrayList<>();

    public KoflocDfSeriesData(){
        list.add(new Controller(1));
        list.add(new Controller(2));
        list.add(new Controller(3));
    }

    public Controller getController(int id){
        if((id>0)&&(id<4)){
            return list.get(id-1);
        }else{
            return list.get(0);
        }
    }

    public void setInfo(int id,String command,String code,int data){
        if((id>0)&&(id<4)){
            list.get(id-1).setInfo(command,code,data);
        }
    }


    public class Controller {
        private int id;
        private String command,code;
        private int data;
        public Controller(int id){
            this.id = id;
        }

        protected void setInfo(String command,String code,int data){
            this.command = command;
            this.code = code;
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public String getCommand() {
            return command;
        }

        public String getCode() {
            return code;
        }

        public int getData() {
            return data;
        }
    }
}
