package com.indignado.games.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.indignado.games.components.ViewsComponent;
import com.indignado.games.components.StateComponent;
import com.indignado.games.data.View;

/**
 * Created on 15/10/14.
 *
 * @author Rubentxu
 */
public class ViewsSystem extends IteratingSystem {
    private ComponentMapper<ViewsComponent> tm;
    private ComponentMapper<StateComponent> sm;



    public ViewsSystem() {
        super(Family.getFor(ViewsComponent.class, StateComponent.class));
        tm = ComponentMapper.getFor(ViewsComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);


    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ViewsComponent viewsComponent = tm.get(entity);
        StateComponent state = sm.get(entity);

        for(View view : viewsComponent.views) {
           if(view.animations != null){
               Animation animation = view.animations.get(state.get());
               if (animation != null){
                   view.textureRegion = animation.getKeyFrame(state.time);
               }

           }
        }

    }

}
