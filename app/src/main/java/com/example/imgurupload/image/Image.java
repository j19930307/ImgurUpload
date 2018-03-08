package com.example.imgurupload.image;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.imgurupload.BaseResponse;

import java.util.List;

public class Image extends BaseResponse {

    /**
     * data : [{"id":"AAzZ3fF","title":null,"description":null,"datetime":1512828958,"type":"image/jpeg","animated":false,"width":1439,"height":2158,"size":730287,"views":102,"bandwidth":74489274,"vote":null,"favorite":false,"nsfw":null,"section":null,"account_url":"j19930307","account_id":873777,"is_ad":false,"in_most_viral":false,"has_sound":false,"tags":[],"ad_type":0,"ad_url":"","in_gallery":false,"deletehash":"rPqp89411PbvCRp","name":"38893590582_da9b6d3b8f_o","link":"https://i.imgur.com/AAzZ3fF.jpg"},{"id":"JxzRVqz","title":null,"description":null,"datetime":1512828923,"type":"image/jpeg","animated":false,"width":1478,"height":2216,"size":1019192,"views":101,"bandwidth":102938392,"vote":null,"favorite":false,"nsfw":null,"section":null,"account_url":"j19930307","account_id":873777,"is_ad":false,"in_most_viral":false,"has_sound":false,"tags":[],"ad_type":0,"ad_url":"","in_gallery":false,"deletehash":"NPDuchO3zpOiUik","name":"38893589682_a8f61e43d6_o","link":"https://i.imgur.com/JxzRVqz.jpg"}]
     * success : true
     * status : 200
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : I5RToyG
         * title : null
         * description : null
         * datetime : 1512911117
         * type : image/jpeg
         * animated : false
         * width : 1200
         * height : 1800
         * size : 309675
         * views : 24
         * bandwidth : 7432200
         * vote : null
         * favorite : false
         * nsfw : null
         * section : null
         * account_url : j19930307
         * account_id : 873777
         * is_ad : false
         * in_most_viral : false
         * has_sound : false
         * tags : []
         * ad_type : 0
         * ad_url :
         * in_gallery : false
         * deletehash : NDZaTx5anmtWTyJ
         * name : DQnsCiHVQAA4iGP
         * link : https://i.imgur.com/I5RToyG.jpg
         */

        private String id;
        private String title;
        private String description;
        private int datetime;
        private String type;
        private boolean animated;
        private int width;
        private int height;
        private int size;
        private int views;
        private long bandwidth;
        private String vote;
        private boolean favorite;
        private boolean nsfw;
        private String section;
        private String account_url;
        private int account_id;
        private boolean is_ad;
        private boolean in_most_viral;
        private boolean has_sound;
        private int ad_type;
        private String ad_url;
        private boolean in_gallery;
        private String deletehash;
        private String name;
        private String link;
        private List<String> tags;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
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

        public boolean isAimated() {
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

        public String getVote() {
            return vote;
        }

        public void setVote(String vote) {
            this.vote = vote;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }

        public boolean getNsfw() {
            return nsfw;
        }

        public void setNsfw(boolean nsfw) {
            this.nsfw = nsfw;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
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

        public boolean isIs_ad() {
            return is_ad;
        }

        public void setIs_ad(boolean is_ad) {
            this.is_ad = is_ad;
        }

        public boolean isIn_most_viral() {
            return in_most_viral;
        }

        public void setIn_most_viral(boolean in_most_viral) {
            this.in_most_viral = in_most_viral;
        }

        public boolean isHas_sound() {
            return has_sound;
        }

        public void setHas_sound(boolean has_sound) {
            this.has_sound = has_sound;
        }

        public int getAd_type() {
            return ad_type;
        }

        public void setAd_type(int ad_type) {
            this.ad_type = ad_type;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public boolean isIn_gallery() {
            return in_gallery;
        }

        public void setIn_gallery(boolean in_gallery) {
            this.in_gallery = in_gallery;
        }

        public String getDeletehash() {
            return deletehash;
        }

        public void setDeletehash(String deletehash) {
            this.deletehash = deletehash;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeInt(this.datetime);
            dest.writeString(this.type);
            dest.writeByte(this.animated ? (byte) 1 : (byte) 0);
            dest.writeInt(this.width);
            dest.writeInt(this.height);
            dest.writeInt(this.size);
            dest.writeInt(this.views);
            dest.writeLong(this.bandwidth);
            dest.writeString(this.vote);
            dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
            dest.writeByte(this.nsfw ? (byte) 1 : (byte) 0);
            dest.writeString(this.section);
            dest.writeString(this.account_url);
            dest.writeInt(this.account_id);
            dest.writeByte(this.is_ad ? (byte) 1 : (byte) 0);
            dest.writeByte(this.in_most_viral ? (byte) 1 : (byte) 0);
            dest.writeByte(this.has_sound ? (byte) 1 : (byte) 0);
            dest.writeInt(this.ad_type);
            dest.writeString(this.ad_url);
            dest.writeByte(this.in_gallery ? (byte) 1 : (byte) 0);
            dest.writeString(this.deletehash);
            dest.writeString(this.name);
            dest.writeString(this.link);
            dest.writeStringList(this.tags);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readString();
            this.title = in.readString();
            this.description = in.readString();
            this.datetime = in.readInt();
            this.type = in.readString();
            this.animated = in.readByte() != 0;
            this.width = in.readInt();
            this.height = in.readInt();
            this.size = in.readInt();
            this.views = in.readInt();
            this.bandwidth = in.readInt();
            this.vote = in.readString();
            this.favorite = in.readByte() != 0;
            this.nsfw = in.readByte() != 0;
            this.section = in.readString();
            this.account_url = in.readString();
            this.account_id = in.readInt();
            this.is_ad = in.readByte() != 0;
            this.in_most_viral = in.readByte() != 0;
            this.has_sound = in.readByte() != 0;
            this.ad_type = in.readInt();
            this.ad_url = in.readString();
            this.in_gallery = in.readByte() != 0;
            this.deletehash = in.readString();
            this.name = in.readString();
            this.link = in.readString();
            this.tags = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }


}
