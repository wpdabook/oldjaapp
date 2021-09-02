package com.a21zhewang.constructionapp.update;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.DownloadBean;
import com.a21zhewang.constructionapp.customview.NumberProgressBar;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;

import java.io.File;

/**
 * Created by Administrator on 2021/7/18.
 */

public class UpdateAppShowDialog extends DialogFragment {
    private static final String KEY_DOWNLOAD_BEAN = "download_bean";
    private DownloadBean mDownLoadBean;
    private static String DownLoadPath = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null){
            mDownLoadBean = (DownloadBean) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.down_updater,container,false);
        bindEvents(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void bindEvents(View view) {
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        TextView number = (TextView)view.findViewById(R.id.tv_update_number);
        TextView update_time = (TextView)view.findViewById(R.id.tv_update_time);
        TextView content = (TextView)view.findViewById(R.id.tv_content);
        ImageView iv_close = (ImageView)view.findViewById(R.id.iv_close);
        final NumberProgressBar progressBar = (NumberProgressBar)view.findViewById(R.id.npb_progress);
        final TextView update = (TextView)view.findViewById(R.id.tv_update);
        title.setText("小粉，我又上新啦！");
        if(mDownLoadBean.getVersionSign() == 1){
            int Code = (new Double(mDownLoadBean.Version)).intValue();
            update_time.setText("更新时间：  "+mDownLoadBean.CreateTime);
            number.setText("更新版本："+"【"+Code+"】");
            content.setText(mDownLoadBean.VerDisc);
            DownLoadPath = mDownLoadBean.VerPath;
        }else { //汉阳建安
            update_time.setText("更新时间：  "+ DateUtils.getTodayDateLong());
            int Code = (new Double(mDownLoadBean.version_code)).intValue();
            number.setText("更新版本："+"【"+Code+"】");
            content.setText(mDownLoadBean.version_desc);
            DownLoadPath = mDownLoadBean.version_path;
        }
//        progressBar.setProgressTextColor(getResources().getColor(R.color.update_default_theme_color));
//        progressBar.setReachedBarColor(getResources().getColor(R.color.update_default_theme_color));
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUpdater.getsInstance().getmNetManager().cancel(UpdateAppShowDialog.this);
                dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                update.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                final File targetFile = new File(getActivity().getCacheDir(),"target.apk");
                AppUpdater.getsInstance().getmNetManager().download(DownLoadPath, targetFile, new INetDownLoadCallBack() {
                    @Override
                    public void success(File apkFile) {
                        //安装的代码
                        v.setEnabled(true);
                        dismiss();
                        String fileMd5 = AppUtils.getFileMD5(targetFile);
                        AppUtils.installApk(getActivity(),apkFile);
                        progressBar.setVisibility(View.GONE);
//                        if(fileMd5 !=null && fileMd5.equals(mDownLoadBean.md5)){
//                            AppUtils.installApk(getActivity(),apkFile);
//                            progressBar.setVisibility(View.GONE);
//                        }else {
//                            Toast.makeText(getActivity(),"MD5检测失败",Toast.LENGTH_SHORT).show();
//                        }
                    }
                    @Override
                    public void progress(int progresss) {
                        //更新界面的代码
                        update.setText(progresss+"%");
                        progressBar.setProgress(progresss);
                        progressBar.setMax(100);

                    }

                    @Override
                    public void failed(Throwable throwable) {
                        v.setEnabled(true);
                        Toast.makeText(getActivity(),"文件下载失败", Toast.LENGTH_SHORT).show();
                    }
                },UpdateAppShowDialog.this);
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        AppUpdater.getsInstance().getmNetManager().cancel(UpdateAppShowDialog.this);
    }

    public static void show(FragmentActivity activity, DownloadBean bean){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DOWNLOAD_BEAN,bean);
        UpdateAppShowDialog dialog = new UpdateAppShowDialog();
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(),"UpdateAppShowDialog");
    };
}
