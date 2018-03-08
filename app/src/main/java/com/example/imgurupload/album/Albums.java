package com.example.imgurupload.album;

import com.example.imgurupload.BaseResponse;
import com.example.imgurupload.image.Image;

import java.util.List;

/**
 * Created by Jason on 2017/12/20.
 */

public class Albums extends BaseResponse {

    /**
     * data : {"id":"lDRB2","title":"Imgur Office","description":null,"datetime":1357856292,"cover":"24nLu","account_url":"Alan","account_id":4,"privacy":"public","layout":"blog","views":13780,"link":"http://alanbox.imgur.com/a/lDRB2","images_count":11,"images":[{"id":"24nLu","title":null,"description":null,"datetime":1357856352,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":855658,"views":135772,"bandwidth":116174397976,"link":"http://i.imgur.com/24nLu.jpg"},{"id":"Ziz25","title":null,"description":null,"datetime":1357856394,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":919391,"views":135493,"bandwidth":124571044763,"link":"http://i.imgur.com/Ziz25.jpg"},{"id":"9tzW6","title":null,"description":null,"datetime":1357856385,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":655028,"views":135063,"bandwidth":88470046764,"link":"http://i.imgur.com/9tzW6.jpg"},{"id":"dFg5u","title":null,"description":null,"datetime":1357856378,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":812738,"views":134704,"bandwidth":109479059552,"link":"http://i.imgur.com/dFg5u.jpg"},{"id":"oknLx","title":null,"description":null,"datetime":1357856338,"type":"image/jpeg","animated":false,"width":1749,"height":2332,"size":717324,"views":32938,"bandwidth":23627217912,"link":"http://i.imgur.com/oknLx.jpg"},{"id":"OL6tC","title":null,"description":null,"datetime":1357856321,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":1443262,"views":32346,"bandwidth":46683752652,"link":"http://i.imgur.com/OL6tC.jpg"},{"id":"cJ9cm","title":null,"description":null,"datetime":1357856330,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":544702,"views":31829,"bandwidth":17337319958,"link":"http://i.imgur.com/cJ9cm.jpg"},{"id":"7BtPN","title":null,"description":null,"datetime":1357856369,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":844863,"views":31257,"bandwidth":26407882791,"link":"http://i.imgur.com/7BtPN.jpg"},{"id":"42ib8","title":null,"description":null,"datetime":1357856424,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":905073,"views":30945,"bandwidth":28007483985,"link":"http://i.imgur.com/42ib8.jpg"},{"id":"BbwIx","title":null,"description":null,"datetime":1357856360,"type":"image/jpeg","animated":false,"width":1749,"height":2332,"size":662413,"views":30107,"bandwidth":19943268191,"link":"http://i.imgur.com/BbwIx.jpg"},{"id":"x7b91","title":null,"description":null,"datetime":1357856406,"type":"image/jpeg","animated":false,"width":1944,"height":2592,"size":618567,"views":29259,"bandwidth":18098651853,"link":"http://i.imgur.com/x7b91.jpg"}]}
     * success : true
     * status : 200
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
         * id : lDRB2
         * title : Imgur Office
         * description : null
         * datetime : 1357856292
         * cover : 24nLu
         * account_url : Alan
         * account_id : 4
         * privacy : public
         * layout : blog
         * views : 13780
         * link : http://alanbox.imgur.com/a/lDRB2
         * images_count : 11
         * images : [{"id":"24nLu","title":null,"description":null,"datetime":1357856352,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":855658,"views":135772,"bandwidth":116174397976,"link":"http://i.imgur.com/24nLu.jpg"},{"id":"Ziz25","title":null,"description":null,"datetime":1357856394,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":919391,"views":135493,"bandwidth":124571044763,"link":"http://i.imgur.com/Ziz25.jpg"},{"id":"9tzW6","title":null,"description":null,"datetime":1357856385,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":655028,"views":135063,"bandwidth":88470046764,"link":"http://i.imgur.com/9tzW6.jpg"},{"id":"dFg5u","title":null,"description":null,"datetime":1357856378,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":812738,"views":134704,"bandwidth":109479059552,"link":"http://i.imgur.com/dFg5u.jpg"},{"id":"oknLx","title":null,"description":null,"datetime":1357856338,"type":"image/jpeg","animated":false,"width":1749,"height":2332,"size":717324,"views":32938,"bandwidth":23627217912,"link":"http://i.imgur.com/oknLx.jpg"},{"id":"OL6tC","title":null,"description":null,"datetime":1357856321,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":1443262,"views":32346,"bandwidth":46683752652,"link":"http://i.imgur.com/OL6tC.jpg"},{"id":"cJ9cm","title":null,"description":null,"datetime":1357856330,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":544702,"views":31829,"bandwidth":17337319958,"link":"http://i.imgur.com/cJ9cm.jpg"},{"id":"7BtPN","title":null,"description":null,"datetime":1357856369,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":844863,"views":31257,"bandwidth":26407882791,"link":"http://i.imgur.com/7BtPN.jpg"},{"id":"42ib8","title":null,"description":null,"datetime":1357856424,"type":"image/jpeg","animated":false,"width":2592,"height":1944,"size":905073,"views":30945,"bandwidth":28007483985,"link":"http://i.imgur.com/42ib8.jpg"},{"id":"BbwIx","title":null,"description":null,"datetime":1357856360,"type":"image/jpeg","animated":false,"width":1749,"height":2332,"size":662413,"views":30107,"bandwidth":19943268191,"link":"http://i.imgur.com/BbwIx.jpg"},{"id":"x7b91","title":null,"description":null,"datetime":1357856406,"type":"image/jpeg","animated":false,"width":1944,"height":2592,"size":618567,"views":29259,"bandwidth":18098651853,"link":"http://i.imgur.com/x7b91.jpg"}]
         */

        private String id;
        private String title;
        private Object description;
        private int datetime;
        private String cover;
        private String account_url;
        private int account_id;
        private String privacy;
        private String layout;
        private int views;
        private String link;
        private int images_count;
        private List<Image.DataBean> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public int getDatetime() {
            return datetime;
        }

        public void setDatetime(int datetime) {
            this.datetime = datetime;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getAccount_url() {
            return account_url;
        }

        public void setAccount_url(String account_url) {
            this.account_url = account_url;
        }

        public int getAccount_id() {
            return account_id;
        }

        public void setAccount_id(int account_id) {
            this.account_id = account_id;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getImages_count() {
            return images_count;
        }

        public void setImages_count(int images_count) {
            this.images_count = images_count;
        }

        public List<Image.DataBean> getImages() {
            return images;
        }

        public void setImages(List<Image.DataBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * id : 24nLu
             * title : null
             * description : null
             * datetime : 1357856352
             * type : image/jpeg
             * animated : false
             * width : 2592
             * height : 1944
             * size : 855658
             * views : 135772
             * bandwidth : 116174397976
             * link : http://i.imgur.com/24nLu.jpg
             */

            private String id;
            private Object title;
            private Object description;
            private int datetime;
            private String type;
            private boolean animated;
            private int width;
            private int height;
            private int size;
            private int views;
            private long bandwidth;
            private String link;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public int getDatetime() {
                return datetime;
            }

            public void setDatetime(int datetime) {
                this.datetime = datetime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isAnimated() {
                return animated;
            }

            public void setAnimated(boolean animated) {
                this.animated = animated;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getViews() {
                return views;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public long getBandwidth() {
                return bandwidth;
            }

            public void setBandwidth(long bandwidth) {
                this.bandwidth = bandwidth;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }
}
