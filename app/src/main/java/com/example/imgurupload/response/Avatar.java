package com.example.imgurupload.response;

import com.example.imgurupload.BaseResponse;

/**
 * Created by Jason on 2018/3/11.
 */

public class Avatar extends BaseResponse {

    /**
     * data : {"avatar":"https://imgur.com/user/j19930307/avatar?maxwidth=155","avatar_name":"default/J"}
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
         * avatar : https://imgur.com/user/j19930307/avatar?maxwidth=155
         * avatar_name : default/J
         */

        private String avatar;
        private String avatar_name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar_name() {
            return avatar_name;
        }

        public void setAvatar_name(String avatar_name) {
            this.avatar_name = avatar_name;
        }
    }
}
