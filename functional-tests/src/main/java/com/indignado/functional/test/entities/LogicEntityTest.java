package com.indignado.functional.test.entities;

import com.badlogic.gdx.files.FileHandle;
import com.indignado.logicbricks.core.LogicEntity;

import java.io.File;
import java.net.URL;

/**
 * @author Rubentxu.
 */
public abstract class LogicEntityTest extends LogicEntity {

    protected FileHandle getFileHandle(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        FileHandle fileHandle = new FileHandle(file);
        return fileHandle;

    }

}
