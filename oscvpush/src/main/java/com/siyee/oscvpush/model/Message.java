package com.siyee.oscvpush.model;

import java.util.Map;

public final class Message {

    /**
     * 这个字段用于通知的消息类型，在透传中都是默认0
     */
    private Integer notifyType;

    private String messageID;
    private String JsonData;

    private String title;

    private String message;

    private Map<String, String> extra;

    private Target target;

    public Integer getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(Integer notifyType) {
        this.notifyType = notifyType;
    }

    public static Message buildMessageForTarget(Target target) {
        Message message = new Message();
        if (target != null) {
            message.setTarget(target);
        }
        return message;
    }

    public String getJsonData() {
        return JsonData == null ? "" : JsonData;
    }

    public void setJsonData(String jsonData) {
        JsonData = jsonData == null ? "" : jsonData;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Message{" +
                "notifyType=" + notifyType +
                ", messageID='" + messageID + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", extra=" + extra +
                ", target=" + target +
                '}';
    }
}
