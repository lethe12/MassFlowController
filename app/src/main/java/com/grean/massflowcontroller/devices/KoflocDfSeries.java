package com.grean.massflowcontroller.devices;

import android.util.Log;

import com.SerialCommunicationListener;
import com.tools;

/**
 * Created by weifeng on 2018/12/13.
 */

public class KoflocDfSeries implements SerialCommunicationListener{
    public static final int STATE_ACQUISITION = 1,STATE_SETTING =2,STATE_READ_SETTING = 3;
    private static final String tag = "KoflocDfSeries";
    private KoflocDfSeriesListener listener;
    public KoflocDfSeries(KoflocDfSeriesListener listener){
        this.listener = listener;
    }

    private static byte[] getCmd(int id,String command,String data){
        String idString = String.format("%03d",id);
        byte [] cmd = new byte[7+command.length()+data.length()];
        cmd[0] = '@';
        System.arraycopy(idString.getBytes(),0,cmd,1,3);
        int index = 4;
        System.arraycopy(command.getBytes(),0,cmd,index,command.length());
        index+=command.length();
        System.arraycopy(data.getBytes(),0,cmd,index,data.length());
        index+=data.length();
        tools.insertCheckSum(cmd,index);
        index+=2;
        cmd[index] = 0x0d;

        Log.d(tag,new String(cmd)+"="+tools.bytesToHexString(cmd,cmd.length));
        return cmd;
    }

    private static byte[] getAcquisitionCmd(int id,String command){
        String idString = String.format("%03d",id);
        byte [] cmd = new byte[7+command.length()];
        cmd[0] = '@';
        System.arraycopy(idString.getBytes(),0,cmd,1,3);
        int index = 4;
        System.arraycopy(command.getBytes(),0,cmd,index,command.length());
        index+=command.length();
        tools.insertCheckSum(cmd,index);
        index+=2;
        cmd[index] = 0x0d;
        Log.d(tag,new String(cmd)+"="+tools.bytesToHexString(cmd,cmd.length));
        return cmd;
    }

    public static byte[] getFlowRateAcquisition(int id){
        return getAcquisitionCmd(id,"RSFD");
    }

    public static byte[] getSetting(int id,String command){
        return getAcquisitionCmd(id,command);
    }

    public static byte[] getSettingCommand(int id,String cmd ,int data){
        String dataString = String.format("%01d", data);
        return getCmd(id,cmd,dataString);
    }

    public static byte[] getFlowRateCmd(int id,int rate){
        String rateString = String.format("%04d", rate);
        return getCmd(id,"WSFD",rateString);
    }

    @Override
    public void onAsyncCommunicationProtocol(byte[] rec, int size) {

    }

    @Override
    public void onCommunicationProtocol(byte[] rec, int size, int state) {
        switch (state){
            case STATE_ACQUISITION:
                listener.onCompleteMessage(Integer.valueOf(new String(rec,1,3)),new String(rec,4,4),
                        new String(rec,8,2), Integer.valueOf(new String(rec,10,4)));
                break;
            case STATE_SETTING:
                listener.onCompleteMessage(Integer.valueOf(new String(rec,1,3)),new String(rec,4,4),
                        new String(rec,8,2),0);
                break;
            case STATE_READ_SETTING:
                listener.onCompleteMessage(Integer.valueOf(new String(rec,1,3)),new String(rec,4,4),
                        new String(rec,8,2), Integer.valueOf(new String(rec,10,1)));
                break;
            default:

                break;


        }

    }
}
