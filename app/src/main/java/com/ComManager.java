package com;

import android.util.Log;

/**
 * Created by weifeng on 2018/12/13.
 */

public class ComManager {
    private static final String tag = "ComManager";
    private Com[] coms = new Com[4];
    private static ComManager instance = new ComManager();
    private SerialCommunicationListener[] listeners =new SerialCommunicationListener[4];

    private ComManager(){
        coms[0] = new Com(0,9600);
        coms[1] = new Com(1,9600);
        coms[2] = new Com(2,9600);
        coms[3] = new Com(3,9600);
    }

    public void setListener (int comNumber , SerialCommunicationListener listener){
        if((comNumber>=0)&&(comNumber<4)){
            listeners[comNumber] = listener;
        }
    }

    public void sendFrame(int comNumber,byte [] buff,int state){
        if((comNumber>=0)&&(comNumber<4)){
            coms[comNumber].addSendBuff(buff,state);
        }
    }

    public static ComManager getInstance(){
        return instance;
    }

    private class Com extends SerialCommunication{
        private int num;

        public Com(int num,int baudRate){
            super(num,baudRate,0);
            this.num = num;
        }

        protected void sendFrame(byte [] buff,int state){
            byte[] cmd = new byte[buff.length];
            System.arraycopy(buff,0,cmd,0,buff.length);
            Log.d(tag,tools.bytesToHexString(cmd,cmd.length));
            addSendBuff(cmd,state);
        }

        @Override
        protected boolean checkRecBuff() {
            return true;
        }

        @Override
        protected void communicationProtocol(byte[] rec, int size, int state) {
            Log.d(tag,"sync"+tools.bytesToHexString(rec,size));
            if(listeners[num]!=null){
                listeners[num].onCommunicationProtocol(rec,size,state);
            }
        }

        @Override
        protected void asyncCommunicationProtocol(byte[] rec, int size) {
            Log.d(tag,"async"+tools.bytesToHexString(rec,size));
            if(listeners[num]!=null){
                listeners[num].onAsyncCommunicationProtocol(rec,size);
            }
        }
    }

}
