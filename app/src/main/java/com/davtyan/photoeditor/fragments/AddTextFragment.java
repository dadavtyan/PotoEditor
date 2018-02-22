package com.davtyan.photoeditor.fragments;


import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.davtyan.photoeditor.R;
import com.davtyan.photoeditor.activitys.EditorActivity;
import com.davtyan.photoeditor.view.MySurfaceView;


public class AddTextFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = AddTextFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private InputMethodManager mInputMethodManager;
    private EditText editText;
    private TextView addText;
    private String text;

    public AddTextFragment() {
        // Required empty public constructor
    }

    public static AddTextFragment show(@NonNull AppCompatActivity appCompatActivity) {
        AddTextFragment fragment = new AddTextFragment();
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    public static AddTextFragment show(@NonNull AppCompatActivity appCompatActivity,
                                                @NonNull String inputText,
                                                @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        AddTextFragment fragment = new AddTextFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_text_dialog, container, false);
        editText = view.findViewById(R.id.edit_text);
        addText = view.findViewById(R.id.add_text);
        addText.setOnClickListener(this);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_text:
                text = editText.getText().toString();
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                dismiss();
                if (text != null){
                    ((MySurfaceView)getActivity().findViewById(R.id.my_dragView)).setText(text);
                    EditorActivity.EXTRA = "draw_text";
                }
                break;
        }
    }
}
