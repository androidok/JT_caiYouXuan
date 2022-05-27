package com.juntai.wisdom.project.mall.base.displayPicVideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chat.MainContract;
import com.example.chat.chatmodule.ChatPresent;
import com.juntai.disabled.basecomponent.base.BaseWebViewActivity;
import com.juntai.disabled.basecomponent.bean.BaseMenuBean;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.widght.BaseBottomDialog;
import com.juntai.disabled.video.img.PhotoView;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseAppFragment;
import com.juntai.wisdom.project.mall.utils.bannerImageLoader.BannerObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022-02-27 11:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2022-02-27 11:48
 */
public class DisplayPhotoFragment extends BaseAppFragment<ChatPresent> implements View.OnClickListener, MainContract.IBaseView {


    private PhotoView mPhotoDisplayPv;
    private ImageView mDisplayPicActionMoreIv;
    private ImageView mDisplayPicActionDownloadIv;
    private static String PICPATH = "picPath";
    private static String MSGSTR = "msgStr";

    private String path;

    @Override
    protected ChatPresent createPresenter() {
        return new ChatPresent();
    }


    public static DisplayPhotoFragment getInstance(String picPath, BannerObject bannerObject) {
        DisplayPhotoFragment fragment = new DisplayPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PICPATH, picPath);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.display_pic_layout;
    }

    @Override
    protected void initView() {
        path = getArguments().getString(PICPATH);
        mPhotoDisplayPv = (PhotoView) getView(R.id.photo_display_pv);
        mDisplayPicActionMoreIv = (ImageView) getView(R.id.display_pic_action_more_iv);
//        mDisplayPicActionMoreIv.setOnClickListener(this);
        mDisplayPicActionDownloadIv = (ImageView) getView(R.id.display_pic_action_download_iv);
        mDisplayPicActionDownloadIv.setOnClickListener(this);
        mPhotoDisplayPv.enable();
        mPhotoDisplayPv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //图片长按的逻辑  如果有二维码 显示识别图中的二维码

                mDisplayPicActionMoreIv.performClick();

                return true;
            }
        });
        loadNetImageFile();
    }

    private void loadNetImageFile() {
        if (!FileCacheUtils.isFileExists(FileCacheUtils.getAppImagePath(true) + getSavedFileName(path))) {
            //本地没有缓存
            ImageLoadUtil.loadImageCache(getActivity(), path, mPhotoDisplayPv);
            ImageLoadUtil.setGlideDownloadFileToLocal((PicVideoDisplayActivity) getActivity(), mContext, path, true);
//            ToastUtils.toast(getBaseActivity(), "对方发送的   本地没有缓存");

        } else {
//            ToastUtils.toast(getBaseActivity(), "对方发送的   本地已缓存");
            ImageLoadUtil.loadImageCache(getActivity(), FileCacheUtils.getAppImagePath(true) + getSavedFileName(path), mPhotoDisplayPv);

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
    }

    /**
     * 将本地图片转成Bitmap
     *
     * @param path 已有图片的路径
     * @return
     */
    public static Bitmap getImageBitmap(String path) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onClick(View v) {
        MoreActionAdapter actionAdapter = new MoreActionAdapter(R.layout.more_action);
        int id = v.getId();
        if (id == R.id.display_pic_action_more_iv) {
            List<BaseMenuBean> menuBeans = getBaseActivity().getBaseBottomDialogMenus(
                     new BaseMenuBean(BaseMenuBean.PIC_MENU_SHARE, R.mipmap.share_icon)
//                    , new BaseMenuBean(BaseMenuBean.PIC_MENU_SAVE, R.mipmap.save_icon)
            );
            Bitmap bitmap = null;
            String result = null;
//            if (messageBodyBean.getFromUserId() == UserInfoManager.getUserId()) {
//                bitmap = getImageBitmap(messageBodyBean.getLocalCatchPath());
//            } else {
//                bitmap = getImageBitmap(FileCacheUtils.getAppImagePath(true) + getSavedFileName(messageBodyBean));
//            }
//            //“QRCODE_SCAN_TYPE ”和“ DATAMATRIX_SCAN_TYPE表示只扫描QR和Data Matrix的码
//            HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE).setPhotoMode(true).create();
//            HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(mContext, bitmap, options);
//            //处理扫码结果
//            if (hmsScans != null && hmsScans.length > 0) {
//                result = hmsScans[0].showResult;
//                //展示扫码结果
//                menuBeans.add(new BaseMenuBean(BaseMenuBean.PIC_MENU_SPOT_QRCODE, R.mipmap.create_qrcode_icon));
//            }


            String finalResult = result;
            ((PicVideoDisplayActivity) getActivity()).initBottomDialog(menuBeans
                    , actionAdapter
                    , new GridLayoutManager(mContext, 5), new BaseBottomDialog.OnItemClick() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            BaseMenuBean menuBean = (BaseMenuBean) adapter.getItem(position);
                            ((PicVideoDisplayActivity) getActivity()).releaseBottomSheetDialog();
                            switch (menuBean.getName()) {
                                case BaseMenuBean.PIC_MENU_SHARE:
                                    // TODO: 2022-02-28 分享
//                                    ShareTool.shareForMob(mContext,
//                                            "",
//                                            messageBodyBean.getContent(),
//                                            "",
//                                            messageBodyBean.getContent(),
//                                            new PlatformActionListener() {
//                                                @Override
//                                                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                                                    //  分享成功后的操作或者提示
//                                                    ToastUtils.success(mContext, "分享成功！");
//                                                }
//
//                                                @Override
//                                                public void onError(Platform platform, int i, Throwable throwable) {
//                                                    //  失败，打印throwable为错误码
//                                                    ToastUtils.warning(mContext, "分享失败！");
//                                                }
//
//                                                @Override
//                                                public void onCancel(Platform platform, int i) {
//                                                    //  分享取消操作
//                                                    ToastUtils.warning(mContext, "分享已取消！");
//                                                }
//                                            });
                                    break;
                                case BaseMenuBean.PIC_MENU_SAVE:
                                    // : 2022-02-28 下载图片
                                    downloadImage();
                                    break;

                                case BaseMenuBean.PIC_MENU_SPOT_QRCODE:
                                    // : 2022/4/6 识别二维码
                                    resolveQrcode(finalResult);


                                    break;
                                default:
                                    break;
                            }
                        }
                    });

        } else if (id == R.id.display_pic_action_download_iv) {
            // : 2022-02-28 图片下载
            downloadImage();
        } else if (id == R.id.photo_display_pv) {
            ((PicVideoDisplayActivity) getActivity()).onBackPressed();
        }
    }

    /**
     * 解析二维码
     * @param result
     */
    public void resolveQrcode(String result) {
        startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url", result));
    }

    private void downloadImage() {
        String oldFilePath = null;
        String newFilePath = null;
        oldFilePath = FileCacheUtils.getAppImagePath(true) + getSavedFileName(path);
        newFilePath = FileCacheUtils.getAppImagePath(false) + getSavedFileName(path);
        FileCacheUtils.copyFile((PicVideoDisplayActivity) getActivity(), oldFilePath, newFilePath, false);

    }


}
