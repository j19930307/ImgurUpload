package com.example.imgurupload.login;

/**
 * Created by Jason on 2017/12/11.
 */

public class Account {

    /**
     * data : {"id":384077,"url":"joshTest","bio":"A real hoopy frood who really knows where his towel is at.","reputation":15303.84,"created":1376951504,"pro_expiration":false}
     * status : 200
     * success : true
     */

    private DataBean data;
    private int status;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * id : 384077
         * url : joshTest
         * bio : A real hoopy frood who really knows where his towel is at.
         * reputation : 15303.84
         * created : 1376951504
         * pro_expiration : false
         */

        private int id;
        private String url;
        private String bio;
        private double reputation;
        private int created;
        private boolean pro_expiration;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public double getReputation() {
            return reputation;
        }

        public void setReputation(double reputation) {
            this.reputation = reputation;
        }

        public int getCreated() {
            return created;
        }

        public void setCreated(int created) {
            this.created = created;
        }

        public boolean isPro_expiration() {
            return pro_expiration;
        }

        public void setPro_expiration(boolean pro_expiration) {
            this.pro_expiration = pro_expiration;
        }
    }
}
