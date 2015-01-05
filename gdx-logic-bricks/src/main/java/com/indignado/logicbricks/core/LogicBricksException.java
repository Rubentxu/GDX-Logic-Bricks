package com.indignado.logicbricks.core;

import com.indignado.logicbricks.utils.Log;

/**
 * @author Rubentxu.
 */
public class LogicBricksException extends RuntimeException {

    public LogicBricksException(String tag, String message) {
        super(message);
        Log.error(tag, message);

    }


    public LogicBricksException(Throwable cause) {
        super(cause);
        Log.error("LogicBricksException", cause.getMessage(),cause);

    }

}
