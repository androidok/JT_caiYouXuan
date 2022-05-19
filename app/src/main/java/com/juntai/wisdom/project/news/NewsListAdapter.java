package com.juntai.wisdom.project.news;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chat.bean.ContactBean;
import com.example.chat.util.MultipleItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.juntai.disabled.basecomponent.utils.CalendarUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.UrlFormatUtil;
import com.juntai.wisdom.project.R;
import com.negier.emojifragment.util.EmojiUtils;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/11/11 15:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/11 15:43
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private boolean isEdit = false;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_CHAT_LIST_CONTACT, R.layout.item_chat_list);
        addItemType(MultipleItem.ITEM_CHAT_LIST_GROUP, R.layout.item_chat_list);
    }

    public List<MessageBodyBean> changeGsonToList(String gsonString) {
        Gson gson = new Gson();
        List<MessageBodyBean> list = gson.fromJson(gsonString, new TypeToken<List<MessageBodyBean>>() {
        }.getType());
        return list;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        helper.addOnClickListener(R.id.sender_pic_iv);
        helper.addOnClickListener(R.id.receiver_pic_iv);
        switch (item.getItemType()) {

            case MultipleItem.ITEM_CHAT_LIST_CONTACT:
                ContactBean contactChatBean = (ContactBean) item.getObject();
                helper.setText(R.id.item_name_tv, contactChatBean.getNickname());
                ImageLoadUtil.loadSquareImage(mContext, UrlFormatUtil.getImageThumUrl(contactChatBean.getHeadPortrait()), helper.getView(R.id.contact_name_iv));
                MessageBodyBean messageBodyBean = contactChatBean.getMessageBodyBean();
                // TODO: 2022/4/18 新增消息类型的时候 这个地方需要注意
                switch (messageBodyBean.getMsgType()) {
                    case 0:
                        EmojiUtils.showEmojiTextView(mContext, helper.getView(R.id.item_content_tv), messageBodyBean.getContent(), 14);
                        break;
                    case 1:
                        helper.setText(R.id.item_content_tv, "[图片]");
                        break;
                    case 2:
                        helper.setText(R.id.item_content_tv, "[视频]");
                        break;
                    default:
                        helper.setText(R.id.item_content_tv, messageBodyBean.getContent());
                        break;
                }

                helper.setGone(R.id.amount_tv, !messageBodyBean.isRead());
                helper.setText(R.id.amount_tv,String.valueOf(messageBodyBean.getUnreadCount()));
                helper.setText(R.id.msg_time_tv, CalendarUtil.formatDataOfChatList(messageBodyBean.getCreateTime()));
                break;
            case MultipleItem.ITEM_CHAT_LIST_GROUP:
                break;
            default:
                break;
        }
    }

}
