package com.bidfrail.provider.ui.chat.model;

import com.library.adapter.recyclerview.adapter.IMultiType;

public class ChatMessageMultiItem implements IMultiType {

    private ChatMessage message;
    private int itemType;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
