package com.juntai.wisdom.project.mall;


import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.juntai.wisdom.project.mall.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}