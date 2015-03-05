package com.indignado.functional.test.levels.base.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.StateComponent;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;
import com.indignado.logicbricks.utils.builders.joints.JointBuilder;

/**
 * @author Rubentxu.
 */
public class Pulley extends EntityFactory {
    private String box2 = "assets/textures/box2.png";

    public Pulley(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
        if (!assetManager.isLoaded(box2)) assetManager.load(box2, Texture.class);

    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();
        JointBuilder jointBuilder = builders.getJointBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Pulley";
        //identity.category = game.getCategoryBitsManager().getCategoryBits("Pulley");

        StateComponent state = entityBuilder.getComponent(StateComponent.class);
        state.createState("Default");

        Body body1 = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1)
                .friction(0.5f)
                .restitution(0.5f))
                .type(BodyDef.BodyType.DynamicBody)
                .build();


        Body body2 = bodyBuilder.fixture(bodyBuilder.fixtureDefBuilder()
                .boxShape(1, 1)
                .friction(0.5f)
                .restitution(0.5f))
                .position(2, 3)
                .type(BodyDef.BodyType.DynamicBody)
                .build();

        Joint joint = jointBuilder.distanceJoint()
                .initialize(body1, body2, body1.getWorldCenter(), body2.getWorldCenter())
                .dampingRatio(1)
                .frequencyHz(3)
                .build();

        RigidBodiesComponents bodiesComponents = entityBuilder.getComponent(RigidBodiesComponents.class);
        bodiesComponents.rigidBodies.add(body1);

        bodiesComponents.rigidBodies.add(body2);

        TextureView boxView = new TextureView();
        boxView.setName("box");
        boxView.setTextureRegion(new TextureRegion(assetManager.get(box2, Texture.class)));
        boxView.setHeight(2.5f);
        boxView.setWidth(2.5f);
        boxView.setAttachedTransform(body1.getTransform());
        boxView.setLayer(0);

        TextureView boxView2 = new TextureView();
        boxView2.setName("box2");
        boxView2.setTextureRegion(new TextureRegion(assetManager.get(box2, Texture.class)));
        boxView2.setHeight(2.5f);
        boxView2.setWidth(2.5f);
        boxView2.setAttachedTransform(body2.getTransform());
        boxView2.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(boxView);
        viewsComponent.views.add(boxView2);

        Entity entity = entityBuilder.getEntity();
        Gdx.app.log("Crate", "instance" + entity);
        return entity;

    }


}
