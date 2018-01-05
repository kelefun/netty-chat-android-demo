package com.funstill.netty.chat.fixtures;


import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatMessage;

import java.util.Date;

/*
 * Created by troy379 on 12.12.16.
 */
public final class MessagesFixtures extends FixturesData {
    private MessagesFixtures() {
        throw new AssertionError();
    }

    public static ChatMessage getImageMessage() {
        ChatMessage message = new ChatMessage(getRandomId(), getUser(), null,new Date());
        message.setImage(new ChatMessage.Image(getRandomImage()));
        return message;
    }

    public static ChatMessage getVoiceMessage() {
        ChatMessage message = new ChatMessage(getRandomId(), getUser(), null,new Date());
        message.setVoice(new ChatMessage.Voice("http://example.com", rnd.nextInt(200) + 30));
        return message;
    }

    public static ChatMessage getTextMessage(String text) {
        User user=getUser();
        user.setId("0");
        return new ChatMessage(getRandomId(), user, text,new Date());
    }


    private static User getUser() {
        boolean even = rnd.nextBoolean();
        return new User(
                even ? "0" : "1",
                even ? names.get(0) : names.get(1),
                even ? avatars.get(0) : avatars.get(1),
                true);
    }
}
