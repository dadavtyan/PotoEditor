package com.davtyan.photoeditor.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.RecyclerItemClickListener;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.StickerAdapter;
import com.davtyan.photoeditor.view.MySurfaceView;

public class StickerFragment extends BaseEditFragment {
    private RecyclerView recyclerIcon;
    private StickerAdapter iconAdapter;
    private MySurfaceView mySurfaceView;

    private int[] images = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
    };

    public StickerFragment() {
        // Required empty public constructor
    }

    public static StickerFragment newInstance() {
        return new StickerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);
        mySurfaceView = getActivity().findViewById(R.id.my_dragView);
        LinearLayoutManager horizontalLayoutManage = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerIcon = view.findViewById(R.id.stickers_type_list);
        recyclerIcon.setLayoutManager(horizontalLayoutManage);
        iconAdapter = new StickerAdapter(getActivity(), images);
        recyclerIcon.setAdapter(iconAdapter);

        recyclerIcon.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Bitmap bitmapItem = BitmapFactory.decodeResource(getActivity().getResources(), images[position]);
                        mySurfaceView.setBitmapSticker(bitmapItem);
                        EditorActivity.EXTRA = "draw_sticker";
                    }
                })

        );
        return view;
    }

    @Override
    public void onShow() {
        activity.mode = MainMenuFragment.INDEX_STICKER;
//        activity.mStickerFragment.getmStickerView().setVisibility(
//                View.VISIBLE);
        // activity.bannerFlipper.showNext();
    }

    @Override
    public void backToMain() {
        activity.mode = 0;
        activity.bottomGallery.setCurrentItem(0);
        activity.flipper.showPrevious();
        EditorActivity.EXTRA = "select_image";
    }

    public void applyStickers() {
        mySurfaceView.addBitmapSticker();
        backToMain();

    }
}
