package com.example.imgurupload.api;

import com.example.imgurupload.BaseResponse;

/**
 * Created by Jason on 2018/1/5.
 */

public class Response extends BaseResponse {


    /**
     * data : {"id":"YdZSq","deletehash":"dqtGzjjlCQRIh9y"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : YdZSq
         * deletehash : dqtGzjjlCQRIh9y
         */

        private String id;
        private String deletehash;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeletehash() {
            return deletehash;
        }

        public void setDeletehash(String deletehash) {
            this.deletehash = deletehash;
        }
    }
}
