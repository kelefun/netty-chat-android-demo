package com.funstill.netty.chat.model.enums;

/**
 * @author liukaiyang
 * @date 2017/12/6 15:58
 */
public enum MsgTypeEnum {
    TEXT(1, "文字"),
    IMG(2, "图片"),
    AUDIO(3, "音频"),
    VIDEO(4, "视频");
    private int index;
    private String name;

    MsgTypeEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
