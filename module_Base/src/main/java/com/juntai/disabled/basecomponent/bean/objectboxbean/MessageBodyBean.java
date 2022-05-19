package com.juntai.disabled.basecomponent.bean.objectboxbean;

import android.os.Parcel;
import android.os.Parcelable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-09-29 9:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-29 9:39
 */
@Entity
public class MessageBodyBean extends BaseWsMessageBean implements Parcelable {

    /**
     * fromUserId : 101
     * fromAccount : 发送者账号
     * toUserId : 97
     * toAccount : 接收者账号
     * content : 消息内容
     * date : 2021-09-29 09:35:00
     * "faceTimeType":"音视频通话状态（1发起通话；2接听；3结束）",
     * "msgType":"消息类型（0：text；1：image；2：video；3：语音；4视频通话；5音频通话，6位置消息，7分享名片，8文件，9合并消息 10 外部分享的链接）",
     * "chatType":"聊天类型（1：私聊；2公聊；4好友添加）",
     * groupId : 群组id；仅chat_type为2时需要
     */
    @Id
    public long id;
    private int fromUserId;
    private String fromAccount;
    private String atUserId;
    /**
     * 此条消息的拥有者   主要用于解决切换账号后  数据紊乱的问题  存储用户的account就可以
     */
    private String   owner;
    private String fromNickname;
    /**
     * 华为推送需要的intenturi
     */
    private String hwPushIntentUrl;
    private String fromHead;
    private String title;
    private String createTime;
    private int orderId;
    private int toUserId;
    private int adapterPosition;
    private String toAccount;
    private String toNickname;
    private String toHead;
    /**
     * 未读数
     */
    private int unreadCount;
    private String content;
    //可被删除  主要用于离线的最后一条消息
    private  boolean  canDelete;
    private int type;
    /**
     * 0：text；1：image；2：video；3：语音；4：直播）
     */
    private int msgType;
    private boolean isRead;
    private String duration;//发送音频的总时长

    //图片 视频 文件 本地缓存路径
    private String localCatchPath;
    //上传进度
    private  int  uploadProgress;
    //图片 视频的旋转角度
    private String rotation;//
    private String videoCover;//


    public long getId() {
        return id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? "" : createTime;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromAccount() {
        return fromAccount == null ? "" : fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? "" : fromAccount;
    }

    public String getAtUserId() {
        return atUserId == null ? "" : atUserId;
    }

    public void setAtUserId(String atUserId) {
        this.atUserId = atUserId == null ? "" : atUserId;
    }

    public String getOwner() {
        return owner == null ? "" : owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? "" : owner;
    }

    public String getFromNickname() {
        return fromNickname == null ? "" : fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname == null ? "" : fromNickname;
    }

    public String getHwPushIntentUrl() {
        return hwPushIntentUrl == null ? "" : hwPushIntentUrl;
    }

    public void setHwPushIntentUrl(String hwPushIntentUrl) {
        this.hwPushIntentUrl = hwPushIntentUrl == null ? "" : hwPushIntentUrl;
    }

    public String getFromHead() {
        return fromHead == null ? "" : fromHead;
    }

    public void setFromHead(String fromHead) {
        this.fromHead = fromHead == null ? "" : fromHead;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public String getToAccount() {
        return toAccount == null ? "" : toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null ? "" : toAccount;
    }

    public String getToNickname() {
        return toNickname == null ? "" : toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname == null ? "" : toNickname;
    }

    public String getToHead() {
        return toHead == null ? "" : toHead;
    }

    public void setToHead(String toHead) {
        this.toHead = toHead == null ? "" : toHead;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDuration() {
        return duration == null ? "" : duration;
    }

    public void setDuration(String duration) {
        this.duration = duration == null ? "" : duration;
    }

    public String getLocalCatchPath() {
        return localCatchPath == null ? "" : localCatchPath;
    }

    public void setLocalCatchPath(String localCatchPath) {
        this.localCatchPath = localCatchPath == null ? "" : localCatchPath;
    }

    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setUploadProgress(int uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public String getRotation() {
        return rotation == null ? "" : rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation == null ? "" : rotation;
    }

    public String getVideoCover() {
        return videoCover == null ? "" : videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover == null ? "" : videoCover;
    }

    public MessageBodyBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.fromUserId);
        dest.writeString(this.fromAccount);
        dest.writeString(this.atUserId);
        dest.writeString(this.owner);
        dest.writeString(this.fromNickname);
        dest.writeString(this.hwPushIntentUrl);
        dest.writeString(this.fromHead);
        dest.writeString(this.title);
        dest.writeString(this.createTime);
        dest.writeInt(this.orderId);
        dest.writeInt(this.toUserId);
        dest.writeInt(this.adapterPosition);
        dest.writeString(this.toAccount);
        dest.writeString(this.toNickname);
        dest.writeString(this.toHead);
        dest.writeInt(this.unreadCount);
        dest.writeString(this.content);
        dest.writeByte(this.canDelete ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
        dest.writeInt(this.msgType);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
        dest.writeString(this.duration);
        dest.writeString(this.localCatchPath);
        dest.writeInt(this.uploadProgress);
        dest.writeString(this.rotation);
        dest.writeString(this.videoCover);
    }

    protected MessageBodyBean(Parcel in) {
        this.id = in.readLong();
        this.fromUserId = in.readInt();
        this.fromAccount = in.readString();
        this.atUserId = in.readString();
        this.owner = in.readString();
        this.fromNickname = in.readString();
        this.hwPushIntentUrl = in.readString();
        this.fromHead = in.readString();
        this.title = in.readString();
        this.createTime = in.readString();
        this.orderId = in.readInt();
        this.toUserId = in.readInt();
        this.adapterPosition = in.readInt();
        this.toAccount = in.readString();
        this.toNickname = in.readString();
        this.toHead = in.readString();
        this.unreadCount = in.readInt();
        this.content = in.readString();
        this.canDelete = in.readByte() != 0;
        this.type = in.readInt();
        this.msgType = in.readInt();
        this.isRead = in.readByte() != 0;
        this.duration = in.readString();
        this.localCatchPath = in.readString();
        this.uploadProgress = in.readInt();
        this.rotation = in.readString();
        this.videoCover = in.readString();
    }

    public static final Creator<MessageBodyBean> CREATOR = new Creator<MessageBodyBean>() {
        @Override
        public MessageBodyBean createFromParcel(Parcel source) {
            return new MessageBodyBean(source);
        }

        @Override
        public MessageBodyBean[] newArray(int size) {
            return new MessageBodyBean[size];
        }
    };
}
