package com.tochycomputerservices.jobportal.RichEditor.styles.toolitems.styles;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tochycomputerservices.jobportal.RichEditor.AREditText;
import com.tochycomputerservices.jobportal.RichEditor.Util;
import com.tochycomputerservices.jobportal.RichEditor.colorpicker.ColorPickerListener;
import com.tochycomputerservices.jobportal.RichEditor.spans.AreForegroundColorSpan;
import com.tochycomputerservices.jobportal.RichEditor.styles.ARE_ABS_Dynamic_Style;
import com.tochycomputerservices.jobportal.RichEditor.styles.windows.ColorPickerWindow;

public class ARE_Style_FontColor extends ARE_ABS_Dynamic_Style<AreForegroundColorSpan> implements ColorPickerListener {

    private ImageView mFontColorImageView;

    private AREditText mEditText;

    private ColorPickerWindow mColorPickerWindow;

    private int mColor;

    private boolean mIsChecked;

    /**
     * @param fontSizeImage
     */
    public ARE_Style_FontColor(AREditText editText, ImageView fontSizeImage) {
        super(editText.getContext());
        this.mEditText = editText;
        this.mFontColorImageView = fontSizeImage;
        setListenerForImageView(this.mFontColorImageView);
    }


    /**
     * @param editText
     */
    public void setEditText(AREditText editText) {
        this.mEditText = editText;
    }

    @Override
    public EditText getEditText() {
        return mEditText;
    }

    @Override
    public void setListenerForImageView(final ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontColorPickerWindow();
            }
        });
    }

    private void showFontColorPickerWindow() {
        if (mColorPickerWindow == null) {
            mColorPickerWindow = new ColorPickerWindow(mContext, this);
        }
        int yOff = Util.getPixelByDp(mContext, -5);
        mColorPickerWindow.showAsDropDown(mFontColorImageView, 0, yOff);
    }

    @Override
    public AreForegroundColorSpan newSpan() {
        return new AreForegroundColorSpan(mColor);
    }

    @Override
    public ImageView getImageView() {
        return this.mFontColorImageView;
    }

    @Override
    public void setChecked(boolean isChecked) {
        // Do nothing.
    }

    @Override
    public boolean getIsChecked() {
        return mIsChecked;
    }

    @Override
    protected void featureChangedHook(int lastSpanFontColor) {
        mColor = lastSpanFontColor;
        setColorChecked(lastSpanFontColor);
    }

    public void setColorChecked(int color) {
        if (mColorPickerWindow != null) {
            mColorPickerWindow.setColor(color);
        }
    }

    @Override
    protected AreForegroundColorSpan newSpan(int color) {
        return new AreForegroundColorSpan(color);
    }

    @Override
    public void onPickColor(int color) {
        mIsChecked = true;
        mColor = color;
        if (null != mEditText) {
            Editable editable = mEditText.getEditableText();
            int start = mEditText.getSelectionStart();
            int end = mEditText.getSelectionEnd();

            if (end >= start) {
                applyNewStyle(editable, start, end, color);
            }
        }
    }
}
