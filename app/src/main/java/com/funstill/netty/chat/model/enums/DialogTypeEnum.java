package com.funstill.netty.chat.model.enums;

/**
 * @author liukaiyang
 * @date 2017/12/6 15:58
 */
public enum DialogTypeEnum {
    PRIVATE_DIALOG(1, "单聊"),
    ROOM_DIALOG(2,"聊天室"),
    GROUP_DIALOG(3, "群");
    private int index;
    private String name;

    DialogTypeEnum(int index, String name) {
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
