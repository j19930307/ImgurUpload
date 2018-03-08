package com.example.imgurupload.image;

import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imgurupload.*;
import com.example.imgurupload.album.Album;
import com.example.imgurupload.album.Albums;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.fragment.BaseFragment;
import com.example.imgurupload.login.Account;
import com.example.imgurupload.login.AccountManager;
import com.facebook.stetho.common.StringUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;


public class ImageFragment extends BaseFragment implements Callback<Image>, ImgurListener, AlertDialogFragment.AlertDialogListener {

    private static ImageFragment imageFragment;
    private String albumHash;
    private ImageAdapter imageAdapter;
    GridLayoutManager layoutManager;
    private ArrayList<Image.DataBean> imageArrayList;
    RecyclerView recyclerView;

    private int selected = -1;

    int page = 0;

    ProgressBar progressBar;

    public static ImageFragment newInstance(String albumHash) {
        imageFragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("albumHash", albumHash);
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.image_recycler_view);
        imageArrayList = new ArrayList<>();

        imageAdapter = new ImageAdapter(imageArrayList, this);
        recyclerView.setAdapter(imageAdapter);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        if (getArguments() != null) {
            albumHash = getArguments().getString("albumHash");
        }

        if (TextUtils.isEmpty(albumHash)) {

            //ImageManager.getInstance(getActivity()).getImages(page, this);
            loadPhotos();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    int visibleCount = layoutManager.getChildCount();
                    int totalCount = layoutManager.getItemCount();
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

                    if (firstVisiblePosition + visibleCount >= totalCount) {
                        page++;
                        loadPhotos();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        } else {
            loadAlbumPhotos();
        }

        return rootView;
    }

    public void loadAlbumPhotos() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitService.getInstance(getActivity()).createApi(ImgurApiService.class).getAlbum("me", albumHash)
                .enqueue(new Callback<Albums>() {
                    @Override
                    public void onResponse(Call<Albums> call, Response<Albums> response) {
                        progressBar.setVisibility(View.GONE);
                        Albums album = response.body();
                        imageAdapter.addItems(new ArrayList(album.getData().getImages()));
                    }

                    @Override
                    public void onFailure(Call<Albums> call, Throwable t) {
                        Log.d("error", t.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void loadPhotos() {
        progressBar.setVisibility(View.VISIBLE);
        ImageManager.getInstance(getActivity()).getImages(page, this);
    }

    @Override
    public void onResponse(Call<Image> call, Response<Image> response) {
        if (response.isSuccessful()) {
            progressBar.setVisibility(View.GONE);
            Image image = response.body();
            imageAdapter.addItems(new ArrayList(image.getData()));
            //recyclerView.setAdapter(imageAdapter);
        }
    }

    @Override
    public void onFailure(Call<Image> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPhotoClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("image", imageArrayList.get(position));
        Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPhotoLongClick(int position) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        selected = position;
        AlertDialogFragment fragment = AlertDialogFragment.newInstance();
        fragment.setTargetFragment(this, 0);
        fragment.show(ft, "alert_dialog");
    }


    @Override
    public void onDialogCopyClick() {
        copyLink(imageArrayList.get(selected).getLink());
    }

    @Override
    public void onDialogDeleteClick() {
        deletePhoto(imageArrayList.get(selected).getDeletehash());
    }

    @Override
    public void onDialogShareClick() {
        shareLink(imageArrayList.get(selected).getLink());
    }

    private void deletePhoto(String deleteHash) {
        RetrofitService.getInstance(getActivity()).createApi(ImgurApiService.class).deleteImage(deleteHash)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                        
                        boolean success = response.isSuccessful();
                        Log.d("response", String.valueOf(success));
                        
                        if(success) { 
                            refresh();
                        } else {
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
    }

    private void refresh() {
        imageAdapter.remove(selected);
    }
}

