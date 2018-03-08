package com.example.imgurupload;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.imgurupload.album.Album;
import com.example.imgurupload.album.AlbumAdapter;
import com.example.imgurupload.image.ImageAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

import static com.example.imgurupload.ImgurAPI.CLIENT_ID;
import static com.example.imgurupload.ImgurAPI.ALBUM_ROUTE;
import static com.example.imgurupload.ImgurAPI.IMAGE_ROUTE;

//Use Android Asynchronous Http Client to connect Imgur API

public class UploadService {

    AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    private int index = 0;
    private ImageDBHelper imagedb;
    private String album_deletehash = null;
    private int count = 0;
    ProgressDialog progress;

    public UploadService(Context context) {
        this.mContext = context;
        client.addHeader("Authorization", "Client-ID " + CLIENT_ID);
        client.setConnectTimeout(10 * 1000);
        client.setThreadPool(Executors.newSingleThreadExecutor());
        //client.setThreadPool(Executors.newFixedThreadPool(3));
    }

    //Upload a new image and store some information by sqlite
    public void post(final String[] images) {

        if (isNetworkConnected()) {
            progress = new ProgressDialog(mContext);
            progress.setProgress(0);
            progress.setMessage("上傳中");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setCancelable(false);
            progress.setIndeterminate(false);
            progress.setMax(images.length);
            progress.show();

            RequestParams params = new RequestParams();
            for (int i = 0; i < images.length; i++) {
                params.put("image", images[i]);
                params.put("album", album_deletehash);

                client.post(IMAGE_ROUTE, params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject response = new JSONObject(new String(responseBody));
                            JSONObject data = response.getJSONObject("data");
                            String link = data.getString("link");
                            String title = data.getString("title");
                            int size = data.getInt("size");
                            String deletehash = data.getString("deletehash");
                            Log.v("Success", link + " " + title + " " + String.valueOf(size) + " " + deletehash);
                            imagedb = new ImageDBHelper(mContext);
                            index++;
                            imagedb.insertImageDetail(link, title, size, deletehash, album_deletehash);
                            imagedb.close();
                            synchronized (this) {
                                if (index == images.length) {
                                    progress.dismiss();
                                    Intent intent = new Intent(mContext, UploadHistoryActivity.class);
                                    if(album_deletehash!=null)
                                        intent.putExtra("position", "album");
                                    mContext.startActivity(intent);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        progress.setProgress(index);
                        super.onProgress(bytesWritten, totalSize);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        super.onRetry(retryNo);
                    }
                });
            }
        } else {
            Toast.makeText(mContext, "網路未連接", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    //Delete an image on Imgur and local database data
    public void deleteImage(final ImageAdapter imagesAdapter, final ArrayList<Image> imageArrayList) {
        if (isNetworkConnected()) {
            progress = new ProgressDialog(mContext);
            progress.setMessage("刪除中");
            progress.show();

            final ArrayList<Image> images = new ArrayList<>();
            for (int i = 0; i < imagesAdapter.getItemCount(); i++) {
                if (imagesAdapter.getSelectedItemIds().get(i)) {
                    images.add(imageArrayList.get(i));
                }
            }
            for (final Image image : images) {
                client.delete(IMAGE_ROUTE + "/" + image.getDeletehash(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.v("Delete Image", response.toString());
                        count++;
                        imagedb = new ImageDBHelper(mContext);
                        imagedb.deleteImage(image.getDeletehash());
                        imageArrayList.remove(image);
                        synchronized (this) {
                            if (count == images.size()) {
                                imagesAdapter.notifyDataSetChanged();
                                progress.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        } else {
            Toast.makeText(mContext, "網路未連接", Toast.LENGTH_SHORT).show();
        }
    }*/

    //Post an album to Imgur
    public void albumPost(final String title, final String[] base64Images) {
        if (isNetworkConnected()) {
            RequestParams params = new RequestParams();
            params.put("title", title);
            client.post(ALBUM_ROUTE, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject data = response.getJSONObject("data");
                        String id = data.getString("id");
                        String deletehash = data.getString("deletehash");
                        album_deletehash = deletehash;
                        imagedb = new ImageDBHelper(mContext);
                        imagedb.insertAlbumDetail(id, deletehash, title);
                        imagedb.close();
                        post(base64Images);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            Toast.makeText(mContext, "網路未連接", Toast.LENGTH_SHORT).show();
        }
    }

    //delete selected album
    /*public void deleteAlbum(final AlbumAdapter albumAdapter, final ArrayList<Album> albumArrayList) {
        if (isNetworkConnected()) {
            progress = new ProgressDialog(mContext);
            progress.setMessage("刪除中");
            progress.show();
            final ArrayList<Album> albums = new ArrayList<>();
            for (int i = 0; i < albumAdapter.getItemCount(); i++) {
                if (albumAdapter.getSelectedItemIds().get(i)) {
                    albums.add(albumArrayList.get(i));
                }
            }
            for (final Album album : albums) {
                client.delete(ALBUM_ROUTE + "/" + album.getDeletehash(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.v("Delete Album", response.toString());
                        count++;
                        imagedb = new ImageDBHelper(mContext);
                        imagedb.deleteAlbum(album.getDeletehash());
                        imagedb.close();
                        albumArrayList.remove(album);
                        synchronized (this) {
                            if (count == albums.size()) {
                                albumAdapter.notifyDataSetChanged();
                                progress.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        } else {
            Toast.makeText(mContext, "網路未連接", Toast.LENGTH_SHORT).show();
        }
    }*/

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
