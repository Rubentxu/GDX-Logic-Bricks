package com.indignado.logicbricks.core;


import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.ArrayMap;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class MessageManager {
    private String tag = "MessageManager";
    private ArrayMap<Integer, String> messageType;
    private int count = 0;
    private MessageDispatcher messageDispatcher;


    public MessageManager() {
        messageType = new ArrayMap<>();
        messageDispatcher = new MessageDispatcher();

    }


    public int getMessageKey(String type) {
        if (messageType.containsValue(type, false)) {
            return messageType.getKey(type, false);
        } else {
            int index = count++;
            messageType.put(index, type);
            Log.debug(tag, "Store MessageType %s number %d", type, index);
            return index;
        }

    }


    public String getMessageType(int key) {
        Log.debug(tag, "Recover MessageType number %d", key);
        return messageType.get(key);

    }


    public void dispatchMessage(Telegraph sender, String msg) {
        messageDispatcher.dispatchMessage(0f, sender, null, getMessageKey(msg), null);

    }


    public void dispatchMessage(Telegraph sender, String msg, Object extraInfo) {
        messageDispatcher.dispatchMessage(0f, sender, null, getMessageKey(msg), extraInfo);

    }


    public void dispatchMessage(Telegraph sender, Telegraph receiver, String msg) {
        messageDispatcher.dispatchMessage(0f, sender, receiver, getMessageKey(msg), null);

    }


    public void dispatchMessage(Telegraph sender, Telegraph receiver, String msg, Object extraInfo) {
        messageDispatcher.dispatchMessage(0f, sender, receiver, getMessageKey(msg), extraInfo);

    }


    public void dispatchMessage(float delay, Telegraph sender, String msg) {
        messageDispatcher.dispatchMessage(delay, sender, null, getMessageKey(msg), null);

    }


    public void dispatchMessage(float delay, Telegraph sender, String msg, Object extraInfo) {
        messageDispatcher.dispatchMessage(delay, sender, null, getMessageKey(msg), extraInfo);

    }


    public void dispatchMessage(float delay, Telegraph sender, Telegraph receiver, String msg) {
        messageDispatcher.dispatchMessage(delay, sender, receiver, getMessageKey(msg), null);

    }


    public void dispatchMessage(float delay, Telegraph sender, Telegraph receiver, String msg, Object extraInfo) {
        messageDispatcher.dispatchMessage(delay, sender, receiver, getMessageKey(msg), extraInfo);

    }


    public MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;

    }

}