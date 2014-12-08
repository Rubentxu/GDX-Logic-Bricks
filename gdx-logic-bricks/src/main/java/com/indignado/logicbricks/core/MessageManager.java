package com.indignado.logicbricks.core;


import com.badlogic.gdx.utils.ArrayMap;
import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu
 */
public class MessageManager {
    private static String tag = "MessageManager";
    private static ArrayMap<Integer, String> messageType = new ArrayMap<>();
    private static int count = 0;



    public static int getMessageKey(String type) {
        if (messageType.containsValue(type, false)) {
            return messageType.getKey(type, false);
        } else {
            int index = count++;
            messageType.put(index, type);
            Log.debug(tag, "Store MessageType %s number %d",type, index);
            return index;
        }

    }


    public static String getMessageType(int key) {
        Log.debug(tag, "Recover MessageType number %d", key);
        return messageType.get(key);

    }

}