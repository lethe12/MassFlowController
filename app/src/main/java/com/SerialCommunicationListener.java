package com;

/**
 * Created by weifeng on 2018/12/14.
 */

public interface SerialCommunicationListener {
    void onAsyncCommunicationProtocol(byte[] rec, int size);
    void onCommunicationProtocol(byte[] rec, int size, int state);
}
