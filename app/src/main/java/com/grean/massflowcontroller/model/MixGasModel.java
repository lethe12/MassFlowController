package com.grean.massflowcontroller.model;

import android.util.Log;

import com.ComManager;
import com.grean.massflowcontroller.devices.KoflocDfSeries;
import com.grean.massflowcontroller.devices.KoflocDfSeriesData;
import com.grean.massflowcontroller.devices.KoflocDfSeriesListener;

/**
 * COM0 接质量流量计，id分别是 1,2,3
 * 需要手动设置 WFSM 写 0 配置为Digital；WVSS 写 1 配置为Controlled；使用RCVS 读取为 1
 * Created by weifeng on 2018/12/14.
 */

public class MixGasModel implements KoflocDfSeriesListener{
    private static final String tag = "MixGasModel";
    private MixGasListener listener;
    private KoflocDfSeriesData seriesData = new KoflocDfSeriesData();

    private KoflocDfSeries  device;

    public MixGasModel(MixGasListener listener){
        device = new KoflocDfSeries(this);
        this.listener = listener;
        ComManager.getInstance().setListener(0,device);
    }

    public void setControllerState(int id,int state){
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getSettingValueStatus(id,state),KoflocDfSeries.STATE_ACQUISITION_OTHER);
    }

    public void getFlowRate(){
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateAcquisition(1),KoflocDfSeries.STATE_ACQUISITION_SIGNED);
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateAcquisition(2),KoflocDfSeries.STATE_ACQUISITION_SIGNED);
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateAcquisition(3),KoflocDfSeries.STATE_ACQUISITION_SIGNED);
    }

    public void setControllerCommand(int id,String command,int data){
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getSettingShortCommand(id,command,data),KoflocDfSeries.STATE_ACQUISITION_OTHER);
    }

    public void setParameterCommand(int id,String command,int data){
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getSettingLongCommand(id,command,data),KoflocDfSeries.STATE_ACQUISITION_OTHER);
    }

    public void getSetting(int id,String command){
        ComManager.getInstance().sendFrame(0,KoflocDfSeries.getSetting(id,command),KoflocDfSeries.STATE_ACQUISITION_OTHER);
    }

    public boolean setFlowRate(int id,int rate){
        if((id>0)&&(id<=3)){
            Log.d(tag,"start to set");
            ComManager.getInstance().sendFrame(0,KoflocDfSeries.getFlowRateCmd(id,rate),KoflocDfSeries.STATE_ACQUISITION_OTHER);
            return true;
        }else{
            return false;
        }
    }

    public String[] getControllerStates(){
        return KoflocDfSeries.controllerStates;
    }


    @Override
    public void onCompleteMessage(int id, String command, String code, int data) {
        seriesData.setInfo(id,command,code,data);
        KoflocDfSeriesData.Controller controller = seriesData.getController(1);
        String string = "ID = "+String.valueOf(controller.getId())+"; Command = "+controller.getCommand()+";  ExitCode = "+controller.getCode()+"; Data = "+String.valueOf(controller.getData())+"\n";
        controller = seriesData.getController(2);
        string += "ID = "+String.valueOf(controller.getId())+"; Command = "+controller.getCommand()+";  ExitCode = "+controller.getCode()+"; Data = "+String.valueOf(controller.getData())+"\n";
        controller = seriesData.getController(3);
        string += "ID = "+String.valueOf(controller.getId())+"; Command = "+controller.getCommand()+";  ExitCode = "+controller.getCode()+"; Data = "+String.valueOf(controller.getData());
        listener.onFlowRateInfo(string);
    }

    @Override
    public void onCompleteCommand(String command) {
        listener.onCompleteCommand(command);
    }
}
