package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.indignado.games.components.AnimationComponent;
import com.indignado.games.components.StateComponent;
import com.indignado.games.components.TextureComponent;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class AnimationSystem extends IteratingSystem {
    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<AnimationComponent> am;
    private ComponentMapper<StateComponent> sm;


    public AnimationSystem() {
        super(Family.getFor(TextureComponent.class, AnimationComponent.class, StateComponent.class));
        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TextureComponent textureComponent = tm.get(entity);
        AnimationComponent animationComponent = am.get(entity);
        StateComponent stateComponent = sm.get(entity);
        Animation animation = animationComponent.animations.get(stateComponent.get());
        
        if (animation != null) {
            textureComponent.region = animation.getKeyFrame(stateComponent.time);
        }
        stateComponent.time += deltaTime;

    }
}
