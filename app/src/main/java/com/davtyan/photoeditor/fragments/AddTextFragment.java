package com.davtyan.photoeditor.fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.RecyclerItemClickListener;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.adapter.ColorPickerAdapter;
import com.davtyan.photoeditor.utils.BitmapUtil;
import com.davtyan.photoeditor.view.MySurfaceView;

import java.util.ArrayList;
import java.util.List;


public class AddTextFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = AddTextFragment.class.getSimpleName();
    private InputMethodManager mInputMethodManager;
    private EditText editText;
    private TextView addText;
    private String text;
    private int textColor;
    private List<Integer> colorPickerColors;

    public AddTextFragment() {
        // Required empty public constructor
    }

    public static AddTextFragment show(@NonNull AppCompatActivity appCompatActivity) {
        AddTextFragment fragment = new AddTextFragment();
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT ;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_text_dialog, container, false);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        RecyclerView addTextColors = view.findViewById(R.id.add_text_color_picker_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        addTextColors.setLayoutManager(layoutManager);
        addTextColors.setHasFixedSize(true);
        getDefaultColors(getContext());
        final ColorPickerAdapter colorPickerAdapter = new ColorPickerAdapter(getActivity(), colorPickerColors);
        addTextColors.setAdapter(colorPickerAdapter);


        editText = view.findViewById(R.id.edit_text);
        addText = view.findViewById(R.id.add_text);
        addText.setOnClickListener(this);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        addTextColors.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        textColor = colorPickerColors.get(position);
                        editText.setTextColor(colorPickerColors.get(position));
                    }
                })

        );
        return view;
    }

    @Override
    public void onClick(View v) {
        text = editText.getText().toString();
        mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        dismiss();
        if (text != null && text.length() != 0) {
            ((MySurfaceView) getActivity().findViewById(R.id.my_dragView))
                    .setBitmapSticker(BitmapUtil.createTextBitmap(text, textColor));
            EditorActivity.EXTRA = "draw_sticker";

        }
    }

    public List<Integer> getDefaultColors(Context context) {
        colorPickerColors = new ArrayList<>();
        colorPickerColors.add(ContextCompat.getColor(context, R.color.blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.brown_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.green_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.black));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.red_orange_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.sky_blue_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.violet_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.white));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_color_picker));
        colorPickerColors.add(ContextCompat.getColor(context, R.color.yellow_green_color_picker));
        return colorPickerColors;
    }
}
