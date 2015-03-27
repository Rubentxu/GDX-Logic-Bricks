package com.indignado.functional.test.levels.buoyancy.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.data.RigidBody2D;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.FixtureDefBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class Box extends EntityFactory {
    private String box = "assets/textures/box1.png";

    public Box(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);


    }


    @Override
    public void loadAssets() {
        if (!assetManager.isLoaded(box)) assetManager.load(box, Texture.class);
    }


    @Override
    public Entity createEntity(float x, float y, float z) {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Box";

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");


        float widt = MathUtils.random(0.2f, 1);
        float height =  MathUtils.random(0.1f, 1f);

        RigidBody2D bodyBox = bodyBuilder.fixture(new FixtureDefBuilder()
                .boxShape(widt, height)
                .friction(0.5f)
                .density(1f))
                .type(BodyDef.BodyType.DynamicBody)
                .position(x,y)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(bodyBox);

        TextureView boxView = new TextureView();
        boxView.setName("Box");
        boxView.setTextureRegion(new TextureRegion(assetManager.get(box, Texture.class)));
        boxView.transform.matrix.scl(widt * 2, height * 2, 1);
        boxView.transform.rigidBody = bodyBox;
        boxView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(boxView);


        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Box", "instance" + entity);
        return entity;

    }


}
