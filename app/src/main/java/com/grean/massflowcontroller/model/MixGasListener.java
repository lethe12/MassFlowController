package com.grean.massflowcontroller.model;
/**

 * Created by weifeng on 2018/12/14.
 */

public interface MixGasListener {
    void onFlowRateInfo(String info);
    void onCompleteCommand(String command);
}
