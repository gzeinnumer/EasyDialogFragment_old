package com.gzeinnumer.easydialogfragment_old;

import android.app.Dialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomMyLibDialog extends DialogFragment {

    public static final String TAG = "asdasasaffa";

    public CustomMyLibDialog() {
    }

    public static CustomMyLibDialog newInstance() {
        return new CustomMyLibDialog();
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        getDialog().setCancelable(false);
//        getDialog().setCanceledOnTouchOutside(false);

        setFullScreen(true);
        setCanvasWidth(0.3);
        enableBackButton(true);
        setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout _dialogCanvas = view.findViewById(R.id.dialog_canvas);

        _dialogCanvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private boolean isFullScreen = false;

    public void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    private double canvasWidth = 0.9;

    public void setCanvasWidth(double canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    private boolean enableBackButton = false;

    public void enableBackButton(boolean enableBackButton) {
        this.enableBackButton = enableBackButton;
    }

    private int gravity = Gravity.CENTER;

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (enableBackButton) {
                    getDialog().dismiss();
                }
            }
        };
    }

    public void onResume() {
        super.onResume();

        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width;
        int height;

        if (isFullScreen) {
            width = WindowManager.LayoutParams.MATCH_PARENT;
            height = size.y - getToolBarHeight();

            window.setLayout(width, height);

            window.setGravity(Gravity.BOTTOM);
        } else {
            width = size.x;
            height = WindowManager.LayoutParams.WRAP_CONTENT;

            if (canvasWidth >= 0.1 && canvasWidth <= 1.0) {
                window.setLayout((int) (width * canvasWidth), height);
            } else {
                window.setLayout((int) (width * 0.9), height);
            }
            window.setGravity(gravity);
        }
    }

    public int getToolBarHeight() {
        int[] attrs = new int[]{R.attr.actionBarSize};
        TypedArray ta = getContext().obtainStyledAttributes(attrs);
        int toolBarHeight = ta.getDimensionPixelSize(0, -1);
        ta.recycle();
        return toolBarHeight;
    }
}
