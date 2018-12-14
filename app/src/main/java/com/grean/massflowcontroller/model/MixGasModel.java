package com.grean.massflowcontroller.model;

import android.util.Log;

import com.ComManager;
import com.grean.massflowcontroller.devices.KoflocDfSeries;
import com.grean.massflowcontroller.devices.KoflocDfSeriesData;
import com.grean.massflowcontroller.devices.KoflocDfSeriesListener;

/**
 * COM0 接质量流量计，id分别是 1,2,3
 * Created by weifeng on 2018/12/14.
 */

public class MixGasModel implements KoflocDfSeriesListener{
    private static final String tag = "MixGasModel";
    private MixGasListener listener;

    private KoflocDfSeries  device;

    public MixGasModel(MixGasListener listener){
        device = new KoflocDfSeries(this);
        this.listener = listener;
        ComManager.getInstance().setListener(0,device);
    }

    public boolean getFlowRate(int id){
        if((id>0)&&(id<=3)){
            ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateAcquisition(id),KoflocDfSeries.STATE_ACQUISITION);
            return true;
        }else{
            return false;
        }
    }

    public boolean setFlowRate(int id,int rate){
        if((id>0)&&(id<=3)){
            Log.d(tag,"start to set");
            ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateCmd(id,rate),KoflocDfSeries.STATE_SETTING);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void onCompleteMessage(int id, String command, String code, int data) {
        String string = command+code+String.valueOf(data);
        listener.onFlowRateInfo(string);
    }
}
