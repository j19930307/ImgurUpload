package com.example.imgurupload;

/**
 * Created by Jason on 2017/12/20.
 */

public class BaseResponse {
    private boolean success;
    private int status;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
