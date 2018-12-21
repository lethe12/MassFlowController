package com.grean.massflowcontroller.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grean.massflowcontroller.R;
import com.grean.massflowcontroller.model.MixGasListener;
import com.grean.massflowcontroller.model.MixGasModel;

/**
 * Created by weifeng on 2018/12/13.
 */

public class MixGasActivity extends Activity implements View.OnClickListener,MixGasListener,AdapterView.OnItemSelectedListener{
    private static final String tag = "MixGasActivity";
    private EditText etChannelOneFlowRate,etChannelTwoFlowRate,etChannelThreeFlowRate,
            etSettingId,etSettingCommand,etSettingData;
    private TextView tvFlowRateInfo,tvCommandInfo;
    private Spinner spChannelOneState,spChannelTwoState,spChannelThreeState;
    private String flowRateInfo,commandInfo;
    private MixGasModel model;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(flowRateInfo!=null){
                tvFlowRateInfo.setText(flowRateInfo);
            }
        }
    };

    private Handler handlerCommand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(commandInfo!=null){
                tvCommandInfo.setText(commandInfo);
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
        etSettingId = findViewById(R.id.etSettingId);
        etSettingCommand = findViewById(R.id.etSettingCommand);
        etSettingData = findViewById(R.id.etSettingData);
        findViewById(R.id.btnAcquisitionController).setOnClickListener(this);
        findViewById(R.id.btnChannelOneSetFlowRate).setOnClickListener(this);
        findViewById(R.id.btnChannelTwoSetFlowRate).setOnClickListener(this);
        findViewById(R.id.btnChannelThreeSetFlowRate).setOnClickListener(this);
        findViewById(R.id.btnSettingController).setOnClickListener(this);
        findViewById(R.id.btnSettingParameter).setOnClickListener(this);
        findViewById(R.id.btnGetSetting).setOnClickListener(this);
        tvFlowRateInfo = findViewById(R.id.tvMassFlowControllerRealTimeInfo);
        tvCommandInfo = findViewById(R.id.tvCommandInfo);
        spChannelOneState = findViewById(R.id.spChannelOneValueState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,model.getControllerStates());
        spChannelOneState.setAdapter(adapter);
        spChannelOneState.setOnItemSelectedListener(this);
        spChannelOneState.setSelection(2,false);
        spChannelTwoState = findViewById(R.id.spChannelTwoState);
         spChannelTwoState.setAdapter(adapter);
        spChannelTwoState.setOnItemSelectedListener(this);
        spChannelTwoState.setSelection(2,false);
        spChannelThreeState = findViewById(R.id.spChannelThreeState);
        spChannelThreeState.setAdapter(adapter);
        spChannelThreeState.setOnItemSelectedListener(this);
        spChannelThreeState.setSelection(2,false);

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
                if(etChannelTwoFlowRate.getText().toString()!=null) {
                    rate = Integer.valueOf(etChannelTwoFlowRate.getText().toString());
                    model.setFlowRate(2, rate);
                }
                break;
            case R.id.btnChannelThreeSetFlowRate:
                if(etChannelThreeFlowRate.getText().toString()!=null) {
                    rate = Integer.valueOf(etChannelThreeFlowRate.getText().toString());
                    model.setFlowRate(3, rate);
                }
                break;
            case R.id.btnSettingController:
                model.setControllerCommand(Integer.valueOf(etSettingId.getText().toString()),
                        etSettingCommand.getText().toString(),
                        Integer.valueOf(etSettingData.getText().toString()));
                break;
            case R.id.btnSettingParameter:
                model.setParameterCommand(Integer.valueOf(etSettingId.getText().toString()),
                        etSettingCommand.getText().toString(),
                        Integer.valueOf(etSettingData.getText().toString()));
                break;
            case R.id.btnGetSetting:
                model.getSetting(Integer.valueOf(etSettingId.getText().toString()),
                        etSettingCommand.getText().toString());
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

    @Override
    public void onCompleteCommand(String command) {
        commandInfo = command;
        handlerCommand.sendEmptyMessage(0);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(tag,"change status");
        switch (parent.getId()){
            case R.id.spChannelOneValueState:
                model.setControllerState(1,position);
                Toast.makeText(this,"设置成功！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.spChannelTwoState:
                model.setControllerState(2,position);
                break;
            case R.id.spChannelThreeState:
                model.setControllerState(3,position);
                break;
            default:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
