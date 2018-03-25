package com.example.imgurupload.album;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.imgurupload.AlertDialogFragment;
import com.example.imgurupload.BaseResponse;
import com.example.imgurupload.R;
import com.example.imgurupload.api.ImgurApiService;
import com.example.imgurupload.api.RetrofitService;
import com.example.imgurupload.fragment.BaseFragment;
import com.example.imgurupload.image.Image;
import com.example.imgurupload.image.ImageFragment;
import com.example.imgurupload.image.ImgurListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class AlbumFragment extends BaseFragment implements Callback<Album>, ImgurListener, AlertDialogFragment.AlertDialogListener {

    private List<Album.DataBean> albumArrayList;
    private RecyclerView recyclerView;
    private AlbumAdapter albumAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int selected = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);


        albumArrayList = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.frag_album_rv);
        albumAdapter = new AlbumAdapter(albumArrayList, this);
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        AlbumManager.getInstance(getActivity()).getAlbums(0, this);

        return rootView;
    }

    @Override
    public void onResponse(Call<Album> call, Response<Album> response) {
        Log.d("albums", response.toString());
        if (response.isSuccessful()) {
            albumArrayList = response.body().getData();
            albumAdapter = new AlbumAdapter(albumArrayList, this);
            recyclerView.setAdapter(albumAdapter);
        }
    }

    @Override
    public void onFailure(Call<Album> call, Throwable t) {

    }

    @Override
    public void onPhotoClick(int position) {
        final String albumId = albumArrayList.get(position).getId();
        Intent intent = AlbumImagesActivity.createIntent(getActivity(), albumId);
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
        copyLink(albumArrayList.get(selected).getLink());
    }

    @Override
    public void onDialogDeleteClick() {
        deleteAlbum(albumArrayList.get(selected).getId());
    }

    @Override
    public void onDialogShareClick() {
        shareLink(albumArrayList.get(selected).getLink());
    }

    private void deleteAlbum(String deleteHash) {
        RetrofitService.getInstance(getActivity()).createApi(ImgurApiService.class).deleteAlbum(deleteHash)
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
        albumAdapter.remove(selected);
    }
}
