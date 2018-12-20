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
    private EditText etChannelOneFlowRate,etChannelTwoFlowRate,etChannelThreeFlowRate;
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
        etChannelOneFlowRate = findViewById(R.id.etChannelOneFlowRate);
        etChannelTwoFlowRate = findViewById(R.id.etChannelTwoFlowRate);
        etChannelThreeFlowRate = findViewById(R.id.etChannelThreeFlowRate);
        findViewById(R.id.btnAcquisitionController).setOnClickListener(this);
        findViewById(R.id.btnChannelOneSetFlowRate).setOnClickListener(this);
        findViewById(R.id.btnChannelTwoSetFlowRate).setOnClickListener(this);
        findViewById(R.id.btnChannelThreeSetFlowRate).setOnClickListener(this);
        tvFlowRateInfo = findViewById(R.id.tvMassFlowControllerRealTimeInfo);
    }

    @Override
    public void onClick(View v) {
        int rate;
        switch (v.getId()){
            case R.id.btnAcquisitionController:
                model.getFlowRate();
                break;
            case R.id.btnChannelOneSetFlowRate:
                if(etChannelOneFlowRate.getText().toString()!=null) {
                    rate = Integer.valueOf(etChannelOneFlowRate.getText().toString());
                    model.setFlowRate(1,rate);
                }

                break;
            case R.id.btnChannelTwoSetFlowRate:
                if(etChannelOneFlowRate.getText().toString()!=null) {
                    rate = Integer.valueOf(etChannelTwoFlowRate.getText().toString());
                    model.setFlowRate(2, rate);
                }
                break;
            case R.id.btnChannelThreeSetFlowRate:
                if(etChannelOneFlowRate.getText().toString()!=null) {
                    rate = Integer.valueOf(etChannelOneFlowRate.getText().toString());
                    model.setFlowRate(3, rate);
                }
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
