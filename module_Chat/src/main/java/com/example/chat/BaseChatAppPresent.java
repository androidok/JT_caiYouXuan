package com.example.chat;


import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/6/3 8:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:38
 */
public abstract class BaseChatAppPresent<M extends IModel, V extends IView> extends BasePresenter<M, V> {

//    /**
//     * 上传文件
//     *
//     * @param
//     * @param body
//     */
//    private void uploadFiles(RequestBody body,  String tag) {
//        AppNetModuleChat.createrRetrofit()
//                .uploadFiles(body)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<UploadFileBean>(getView()) {
//                    @Override
//                    public void onSuccess(UploadFileBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o.getFilePaths());
//                        }
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
//    }
//
//
//    /**
//     * 上传文件
//     *
//     * @param filePaths
//     */
//    public void uploadFile(String tag, String... filePaths) {
//        if (filePaths.length > 0) {
//            MultipartBody.Builder builder = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM);
//            for (String filePath : filePaths) {
//                try {
//                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//            uploadFiles(builder.build(), tag);
//        }
//
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param filePaths
//     */
//    public void uploadFile(List<String> filePaths, String tag) {
//        if (filePaths.size() > 0) {
//            MultipartBody.Builder builder = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM);
//            for (String filePath : filePaths) {
//                try {
//                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//            uploadFiles(builder.build(), tag);
//        }
//
//    }
}
