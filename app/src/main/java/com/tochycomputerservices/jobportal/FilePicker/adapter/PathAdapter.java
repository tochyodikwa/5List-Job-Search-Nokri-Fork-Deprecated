package com.tochycomputerservices.jobportal.FilePicker.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tochycomputerservices.jobportal.FilePicker.utils.Constant;
import com.tochycomputerservices.jobportal.FilePicker.utils.FileUtils;
import com.tochycomputerservices.jobportal.R;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.PathViewHolder> {
    public interface OnItemClickListener {
        void click(int position);
    }

    public interface OnCancelChoosedListener {
        void cancelChoosed(CheckBox checkBox);
    }

    private final String TAG = "FilePickerLeon";
    private List<File> mListData;
    private Context mContext;
    public OnItemClickListener onItemClickListener;
    private FileFilter mFileFilter;
    private boolean[] mCheckedFlags;
    private boolean mMutilyMode;
    private int mIconStyle;
    private boolean mIsGreater;
    private long mFileSize;

    public PathAdapter(List<File> mListData, Context mContext, FileFilter mFileFilter, boolean mMutilyMode, boolean mIsGreater, long mFileSize) {
        this.mListData = mListData;
        this.mContext = mContext;
        this.mFileFilter = mFileFilter;
        this.mMutilyMode = mMutilyMode;
        this.mIsGreater = mIsGreater;
        this.mFileSize = mFileSize;
        mCheckedFlags = new boolean[mListData.size()];
    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.lfile_listitem, null);
        PathViewHolder pathViewHolder = new PathViewHolder(view);
        return pathViewHolder;
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    @Override
    public void onBindViewHolder(final PathViewHolder holder, final int position) {
        final File file = mListData.get(position);
        if (file.isFile()) {
            updateFileIconStyle(holder.ivType);
            holder.tvName.setText(file.getName());
            holder.tvDetail.setText(mContext.getString(R.string.lfile_FileSize) + " " + FileUtils.getReadableFileSize(file.length()));
            holder.cbChoose.setVisibility(View.VISIBLE);
        } else {
            updateFloaderIconStyle(holder.ivType);
            holder.tvName.setText(file.getName());
            //??????????????????
            List files = FileUtils.getFileList(file.getAbsolutePath(), mFileFilter, mIsGreater, mFileSize);
            if (files == null) {
                holder.tvDetail.setText("0 " + mContext.getString(R.string.lfile_LItem));
            } else {
                holder.tvDetail.setText(files.size() + " " + mContext.getString(R.string.lfile_LItem));
            }
            holder.cbChoose.setVisibility(View.GONE);
        }
        if (!mMutilyMode) {
            holder.cbChoose.setVisibility(View.GONE);
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.isFile()) {
                    holder.cbChoose.setChecked(!holder.cbChoose.isChecked());
                }
                onItemClickListener.click(position);
            }
        });
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????????????????????????????????????????
                onItemClickListener.click(position);
            }
        });
        holder.cbChoose.setOnCheckedChangeListener(null);//???????????????CheckBox?????????????????????????????????null
        holder.cbChoose.setChecked(mCheckedFlags[position]);//????????????????????????CheckBox???????????????
        //???????????????CheckBox????????????????????????CheckBox????????????????????????????????????????????????????????????????????????
        holder.cbChoose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCheckedFlags[position] = b;
            }
        });
    }

    private void updateFloaderIconStyle(ImageView imageView) {
        switch (mIconStyle) {
            case Constant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.drawable.lfile_folder_style_blue);
                break;
            case Constant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.drawable.lfile_folder_style_green);
                break;
            case Constant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.drawable.lfile_folder_style_yellow);
                break;
        }
    }

    private void updateFileIconStyle(ImageView imageView) {
        switch (mIconStyle) {
            case Constant.ICON_STYLE_BLUE:
                imageView.setBackgroundResource(R.drawable.lfile_file_style_blue);
                break;
            case Constant.ICON_STYLE_GREEN:
                imageView.setBackgroundResource(R.drawable.lfile_file_style_green);
                break;
            case Constant.ICON_STYLE_YELLOW:
                imageView.setBackgroundResource(R.drawable.lfile_file_style_yellow);
                break;
        }
    }

    /**
     * ???????????????
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * ???????????????
     *
     * @param mListData
     */
    public void setmListData(List<File> mListData) {
        this.mListData = mListData;
        mCheckedFlags = new boolean[mListData.size()];
    }

    public void setmIconStyle(int mIconStyle) {
        this.mIconStyle = mIconStyle;
    }

    /**
     * ??????????????????
     *
     * @param isAllSelected
     */
    public void updateAllSelelcted(boolean isAllSelected) {

        for (int i = 0; i < mCheckedFlags.length; i++) {
            mCheckedFlags[i] = isAllSelected;
        }
        notifyDataSetChanged();
    }

    class PathViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private ImageView ivType;
        private TextView tvName;
        private TextView tvDetail;
        private CheckBox cbChoose;

        public PathViewHolder(View itemView) {
            super(itemView);
            ivType = (ImageView) itemView.findViewById(R.id.iv_type);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_item_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
            cbChoose = (CheckBox) itemView.findViewById(R.id.cb_choose);
        }
    }
}


