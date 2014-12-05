package com.indignado.logicbricks.core;

import com.badlogic.gdx.Gdx;
import com.indignado.logicbricks.utils.Logger;

/**
 * @author Rubentxu.
 */
public class LogicBricksException extends RuntimeException {
    private Logger log = new Logger(this.getClass().getSimpleName());

    public LogicBricksException(String tag, String message) {
        super(message);
        if (Gdx.app != null) {
            Gdx.app.log(tag, message);
        } else {
            System.out.println(tag + " : " + message);
        }

    }


    public LogicBricksException(Throwable cause) {
        super(cause);

        if (Gdx.app != null) {
            Gdx.app.log("LogicBricksException", cause.getMessage());
        } else {
            System.out.println("LogicBricksException : " + cause.getMessage());
        }

    }

}
