package com.a21zhewang.constructionapp.ui.jygz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.bitmap;

/**
 * Created by Administrator on 2021/3/11.
 */

public class Act_JygzQrCode extends BaseActivity {
    @BindView(R.id.qrimage)
    ImageView qr_image;
    @BindView(R.id.share_picture)
    ImageView share_picture;
    @BindView(R.id.share_weixin)
    ImageView share_weixin;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.more_share)
    ImageView more_share;
    @BindView(R.id.text_region)
    TextView text_region;
    @BindView(R.id.text_title)
    TextView text_title;
    private String qrcode_url;
    private String projectName;
    private Bitmap qr;
    private String region;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygz_qrcode_layout;
    }


    @OnClick({
            R.id.share_picture,
            R.id.share_weixin,
            R.id.more_share
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_picture: //分享单张图片
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/png");
//                intent.putExtra(Intent.EXTRA_STREAM, qrcode_url);
//                startActivity(Intent.createChooser(intent,"简易告知"));
                MediaStore.Images.Media.insertImage(getContentResolver(), qr, "简易告知", "简易告知二维码");
                showToast("二维码保存成功，请去相册查看。");
                break;
            case R.id.share_weixin: //分享到微信
                showToast("AppID申请中");
                break;
            case R.id.more_share: //分享文本信息
                Intent moreintent = new Intent(Intent.ACTION_SEND);
                moreintent.setType("text/plain");
                moreintent.putExtra(Intent.EXTRA_TEXT, qrcode_url);
                startActivity(moreintent.createChooser(moreintent, "简易告知"));
                break;
        }
    }
    @Override
    public void initViews() {
        qrcode_url = getIntent().getStringExtra("qrcode_url");
        projectName = getIntent().getStringExtra("projectName");
        region = getIntent().getStringExtra("region");
        project_name.setText(projectName);
        qr = CodeUtils.createImage(qrcode_url, 520, 520, BitmapFactory.decodeResource(null, R.mipmap.launcher));
        qr_image.setImageBitmap(qr);
        if(!"".equals(region)){
            text_title.setText("所属区：");
            text_region.setText(region);
        }
    }
    @Override
    public void initListener() {
//
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
