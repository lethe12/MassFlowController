package com.grean.massflowcontroller.devices;

/**
 * Created by weifeng on 2018/12/14.
 */

public interface KoflocDfSeriesListener {
    void onCompleteMessage(int id,String command,String code,int data);
    void onCompleteCommand(String command);
}
