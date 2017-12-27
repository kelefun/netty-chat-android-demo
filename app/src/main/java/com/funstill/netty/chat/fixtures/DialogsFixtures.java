package com.funstill.netty.chat.fixtures;


import com.funstill.netty.chat.model.chat.ChatMessage;
import com.funstill.netty.chat.model.User;
import com.funstill.netty.chat.model.chat.ChatDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * Created by Anton Bevza on 07.09.16.
 */
public final class DialogsFixtures extends FixturesData {
    private DialogsFixtures() {
        throw new AssertionError();
    }

    public static ArrayList<ChatDialog> getDialogs() {
        ArrayList<ChatDialog> chats = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -(i * i));
            calendar.add(Calendar.MINUTE, -(i * i));

            chats.add(getDialog(i, calendar.getTime()));
        }

        return chats;
    }

    private static ChatDialog getDialog(int i, Date lastMessageCreatedAt) {
        ArrayList<User> users = getUsers();
        return new ChatDialog();
    }

    private static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        int usersCount = 1 + rnd.nextInt(4);

        for (int i = 0; i < usersCount; i++) {
            users.add(getUser());
        }

        return users;
    }

    private static User getUser() {
        return new User(
                getRandomId(),
                getRandomName(),
                getRandomAvatar(),
                getRandomBoolean());
    }

    private static ChatMessage getMessage(final Date date) {
        return new ChatMessage(
                getRandomId(),
                getUser(),
                getRandomMessage(),
                date);
    }
}
