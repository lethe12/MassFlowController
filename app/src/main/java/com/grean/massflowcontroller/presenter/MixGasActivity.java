package com.grean.massflowcontroller.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.grean.massflowcontroller.R;
import com.grean.massflowcontroller.model.MixGasListener;
import com.grean.massflowcontroller.model.MixGasModel;

/**
 * Created by weifeng on 2018/12/13.
 */

public class MixGasActivity extends Activity implements View.OnClickListener,MixGasListener{
    private EditText etChannelOneFlowRate;
    private TextView tvFlowRateInfo;
    private String flowRateInfo;
    private MixGasModel model;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(flowRateInfo!=null){
                tvFlowRateInfo.setText(flowRateInfo);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_gas);
        model = new MixGasModel(this);
        initView();


    }

    private void initView(){
        etChannelOneFlowRate = findViewById(R.id.etChannleOneFlowRate);
        findViewById(R.id.btnAcquisitionController).setOnClickListener(this);
        findViewById(R.id.btnSetFlowRate).setOnClickListener(this);
        tvFlowRateInfo = findViewById(R.id.tvMassFlowControllerRealTimeInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAcquisitionController:
                model.getFlowRate(1);
                break;
            case R.id.btnSetFlowRate:
                int rate = Integer.valueOf(etChannelOneFlowRate.getText().toString());
                model.setFlowRate(1,rate);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFlowRateInfo(String info) {
        flowRateInfo = info;
        handler.sendEmptyMessage(0);
    }
}
