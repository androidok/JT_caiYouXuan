package com.example.chat.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.BaseAppUtils;
import com.juntai.disabled.basecomponent.utils.CalendarUtil;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.bdmap.BaseRequestLocationActivity;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Author: tobato
 * @Description: 作用描述  选择图片的基类
 * @CreateDate: 2020/5/21 15:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 15:04
 */
public abstract class BaseChatSelectPicsActivity<P extends BasePresenter> extends BaseRequestLocationActivity<P> {


    private int SELECT_PIC_RESULT = 1000;
    private int TAKE_PICTURE = 1001;
    public String cameraPath;
    private int compressedSize = 0;//被压缩的图片个数
    private List<String> icons = new ArrayList<>();


    protected abstract void selectedPicsAndEmpressed(List<String> icons);



    /**
     * 获取拍照存储URI
     *
     * @param context
     * @return
     */
    private Uri getOutputMediaFileUri(Context context) {
        File mediaFile = null;
        // 文件名
        String filename = CalendarUtil.getCurrentTime("yyyyMMdd_HHmmss") + ".png";
        // file对象，注意路径要和resource xml里配置的一样
        mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator, filename);
        cameraPath = mediaFile.getAbsolutePath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            Uri contentUri = FileProvider.getUriForFile(context, BaseAppUtils.getFileprovider(),//与清单文件中android
                    // :authorities的值保持一致
                    mediaFile);//FileProvider方式或者ContentProvider。也可使用VmPolicy方式
            return contentUri;
        } else {
            return Uri.fromFile(mediaFile);//或者 Uri.isPaise("file://"+file.toString()

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == SELECT_PIC_RESULT && resultCode == RESULT_OK) {
//            imageCompress(Matisse.obtainPathResult(data));
            imageCompressContainVideo(Matisse.obtainPathResult(data));
        } else if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            imageCompress(cameraPath);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 图片压缩
     */
    private void imageCompress(final List<String> paths) {
        compressedSize = 0;
        showLoadingDialog(mContext, false);
        Luban.with(mContext).load(paths).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath(true)).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  压缩开始前调用，可以在方法内启动 loading UI

            }

            @Override
            public void onSuccess(File file) {
                compressedSize++;
                //  压缩成功后调用，返回压缩后的图片文件
                icons.clear();
                icons.add(file.getPath());
                selectedPicsAndEmpressed(icons);
                if (compressedSize == paths.size()) {
                    stopLoadingDialog();
                }

            }

            @Override
            public void onError(Throwable e) {
                //  当压缩过程出现问题时调用
                compressedSize++;
                LogUtil.e("push-图片压缩失败");
                if (compressedSize == paths.size()) {
                    stopLoadingDialog();
                }

            }
        }).launch();
    }

    /**
     * 图片压缩
     */
    private void imageCompressContainVideo(List<String> paths) {
        compressedSize = 0;
        showLoadingDialog(mContext, false);
        List<String> pics = new ArrayList<>();
        for (String path : paths) {
            if (1 == FileCacheUtils.getFileType(path)) {
                imageCompress(path);
            } else if (2 == FileCacheUtils.getFileType(path)) {
                stopLoadingDialog();
                pics.add(path);
            } else {

            }

        }
        selectedPicsAndEmpressed(pics);
    }

    /**
     * 图片压缩
     */
    private void imageCompress(String path) {
        showLoadingDialog(mContext, false);
        Luban.with(mContext).load(path).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath(true)).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  压缩开始前调用，可以在方法内启动 loading UI

            }

            @Override
            public void onSuccess(File file) {
                //  压缩成功后调用，返回压缩后的图片文件
                List<String> pics = new ArrayList<>();
                pics.add(file.getPath());
                selectedPicsAndEmpressed(pics);
                stopLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("push-图片压缩失败");
                stopLoadingDialog();
            }
        }).launch();
    }

}
