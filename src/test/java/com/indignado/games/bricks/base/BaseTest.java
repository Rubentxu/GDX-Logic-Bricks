package com.indignado.games.bricks.base;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import org.mockito.Mockito;

/**
 * Created on 18/10/14.
 *
 * @author Rubentxu
 */
public class BaseTest extends ApplicationAdapter{
    protected enum PlayerState { WALKING, JUMP, FALL, IDLE }
    protected String path;


    protected IntMap<Animation> getAnimations(){
        IntMap<Animation> animations = new IntMap<Animation>();
        animations.put(0, Mockito.mock(Animation.class));
        animations.put(1, Mockito.mock(Animation.class));

        return animations;

    }


    protected FileHandle getFileHandle(String relativePath){
        return new FileHandle(Thread.currentThread().getContextClassLoader().getResource(relativePath).getPath());

    }

}
