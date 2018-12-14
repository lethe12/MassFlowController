package com.grean.massflowcontroller.devices;

/**
 * Created by weifeng on 2018/12/14.
 */

public class KoflocDfSeriesData {
    int id;
    int realTimeFlowRate;

    public KoflocDfSeriesData(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public int getRealTimeFlowRate() {
        return realTimeFlowRate;
    }

    public void setRealTimeFlowRate(int realTimeFlowRate) {
        this.realTimeFlowRate = realTimeFlowRate;
    }
}
