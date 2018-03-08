package com.example.imgurupload.api;

import com.example.imgurupload.BaseResponse;
import com.example.imgurupload.album.Album;
import com.example.imgurupload.album.Albums;
import com.example.imgurupload.image.Image;
import com.example.imgurupload.login.Account;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ImgurApiService {

    @GET("account/{username}")
    Call<Account> getAccountBase(@Path("username") String username);

    //https://api.imgur.com/3/account/{{username}}/images/{{page}}
    @GET("account/{username}/images/{page}")
    Call<Image> getImages(@Path("username") String username, @Path("page") int page);

    //https://api.imgur.com/3/account/{{username}}/albums/{{page}}
    @GET("account/{username}/albums/{page}")
    Call<Album> getAlbums(@Path("username") String username, @Path("page") int page);

    //https://api.imgur.com/3/account/{{username}}/album/{{albumHash}}
    @GET("account/{username}/album/{albumHash}")
    Call<Albums> getAlbum(@Path("username") String username, @Path("albumHash") String albumHash);

    //https://api.imgur.com/3/image
    @POST("image")
    Call<Image.DataBean> uploadImage(@Body RequestBody requestBody);

    @POST("image")
    Call<Image.DataBean> uploadImage(@Body MultipartBody file);

    //https://api.imgur.com/3/album
    @POST("album")
    Call<Response> createAlbum(@Body RequestBody requestBody);

    @DELETE("image/{imageHash}")
    Call<BaseResponse> deleteImage(@Path("imageHash") String imageHash);

    @DELETE("album/{albumHash}")
    Call<BaseResponse> deleteAlbum(@Path("albumHash") String albumHash);

}
