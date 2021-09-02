package cn.hzw.graffiti;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.io.File;

/**
 * 涂鸦参数
 */
public class GraffitiParams implements Parcelable {

    public GraffitiParams(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    /**
     * 图片路径
     */
    public String mImagePath;
    /**
     * 　保存路径，如果为null，则图片保存在根目录下/DCIM/Graffiti/
     */
    public String mSavePath=getSavaPath();
    /**
     * 　保存路径是否为目录，如果为目录，则在该目录生成由时间戳组成的图片名称
     */
    public boolean mSavePathIsDir=true;
    /**
     * 　橡皮擦底图，如果为null，则底图为当前图片路径
     * {@link GraffitiView#GraffitiView(Context, Bitmap, String, boolean, GraffitiListener)}
     */
    public String mEraserPath;

    /**
     * 橡皮擦底图是否调整大小，如果为true则调整到跟当前涂鸦图片一样的大小．
     * 默认为true
     */
    public boolean mEraserImageIsResizeable = true;

    /**
     * 触摸时，图片区域外是否绘制涂鸦轨迹
     */
    public boolean mIsDrawableOutside;

    /**
     * 涂鸦时（手指按下）隐藏设置面板的延长时间(ms)，当小于等于0时则为不尝试隐藏面板（即保持面板当前状态不变）;当大于0时表示需要触摸屏幕超过一定时间后才隐藏
     * 或者手指抬起时展示面板的延长时间(ms)，或者表示需要离开屏幕超过一定时间后才展示
     * 默认为800ms
     */
    public long mChangePanelVisibilityDelay = 800; //ms

    /**
     * 设置放大镜的倍数，当小于等于0时表示不使用放大器功能
     * 放大器只有在设置面板被隐藏的时候才会出现
     * 默认为2.5倍
     */
    public float mAmplifierScale = 0f;

    /**
     * 是否全屏显示，即是否隐藏状态栏
     * 默认为false，表示状态栏继承应用样式
     */
    public boolean mIsFullScreen = false;

    /**
     * 初始化的画笔大小
     */
    public float mPaintSize = 4;



    private static DialogInterceptor sDialogInterceptor;

    /**
     * 设置涂鸦中对话框的拦截器，如点击返回按钮（或返回键）弹出保存对话框，可以进行拦截，弹出自定义的对话框
     * 切记：需要自行处理内存泄漏的问题！！！
     */
    public static void setDialogInterceptor(DialogInterceptor interceptor) {
        sDialogInterceptor = interceptor;
    }

    public static DialogInterceptor getDialogInterceptor() {
        return sDialogInterceptor;
    }

    public enum DialogType {
        SAVE, CLEAR_ALL, COLOR_PICKER;
    }

    public interface DialogInterceptor {
        /**
         * @param activity
         * @param graffitiView
         * @param dialogType   对话框类型
         * @return 返回true表示拦截
         */
        boolean onShow(Activity activity, GraffitiView graffitiView, DialogType dialogType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImagePath);
        dest.writeString(this.mSavePath);
        dest.writeByte(this.mSavePathIsDir ? (byte) 1 : (byte) 0);
        dest.writeString(this.mEraserPath);
        dest.writeByte(this.mEraserImageIsResizeable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.mIsDrawableOutside ? (byte) 1 : (byte) 0);
        dest.writeLong(this.mChangePanelVisibilityDelay);
        dest.writeFloat(this.mAmplifierScale);
        dest.writeByte(this.mIsFullScreen ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.mPaintSize);
    }

    protected GraffitiParams(Parcel in) {
        this.mImagePath = in.readString();
        this.mSavePath = in.readString();
        this.mSavePathIsDir = in.readByte() != 0;
        this.mEraserPath = in.readString();
        this.mEraserImageIsResizeable = in.readByte() != 0;
        this.mIsDrawableOutside = in.readByte() != 0;
        this.mChangePanelVisibilityDelay = in.readLong();
        this.mAmplifierScale = in.readFloat();
        this.mIsFullScreen = in.readByte() != 0;
        this.mPaintSize = in.readFloat();
    }

    public static final Parcelable.Creator<GraffitiParams> CREATOR = new Parcelable.Creator<GraffitiParams>() {
        @Override
        public GraffitiParams createFromParcel(Parcel source) {
            return new GraffitiParams(source);
        }

        @Override
        public GraffitiParams[] newArray(int size) {
            return new GraffitiParams[size];
        }
    };
    /**
     * @return 获取 保存路径
     */
    public String getSavaPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString() + "/ImageOfConAPP";

    }
}
