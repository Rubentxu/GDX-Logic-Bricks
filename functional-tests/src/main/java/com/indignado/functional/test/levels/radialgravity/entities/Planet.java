package com.indignado.functional.test.levels.radialgravity.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.indignado.logicbricks.components.IdentityComponent;
import com.indignado.logicbricks.components.RadialGravityComponent;
import com.indignado.logicbricks.components.RigidBodiesComponents;
import com.indignado.logicbricks.components.ViewsComponent;
import com.indignado.logicbricks.core.EntityFactory;
import com.indignado.logicbricks.core.data.TextureView;
import com.indignado.logicbricks.utils.builders.BodyBuilder;
import com.indignado.logicbricks.utils.builders.EntityBuilder;
import com.indignado.logicbricks.utils.builders.LBBuilders;

/**
 * @author Rubentxu.
 */
public class Planet extends EntityFactory {
    private String planet = "assets/textures/planet.png";

    public Planet(LBBuilders builders, AssetManager assetManager) {
        super(builders, assetManager);

    }


    @Override
    public void loadAssets() {
        if (!assetManager.isLoaded(planet)) assetManager.load(planet, Texture.class);

    }


    @Override
    public Entity createEntity() {
        EntityBuilder entityBuilder = builders.getEntityBuilder();
        entityBuilder.initialize();
        BodyBuilder bodyBuilder = builders.getBodyBuilder();

        IdentityComponent identity = entityBuilder.getComponent(IdentityComponent.class);
        identity.tag = "Planet";

        Body bodyPlanet = bodyBuilder
                .fixture(bodyBuilder.fixtureDefBuilder()
                        .circleShape(5f)
                        .density(1)
                        .friction(2f))
                .mass(2f)
                .type(BodyDef.BodyType.StaticBody)
                .build();

        RigidBodiesComponents rigidByPool = entityBuilder.getComponent(RigidBodiesComponents.class);
        rigidByPool.rigidBodies.add(bodyPlanet);

        RadialGravityComponent radialGravityComponent = entityBuilder.getComponent(RadialGravityComponent.class);
        radialGravityComponent.radius = 24f;
        // radialGravityComponent.gravity = -18;


        TextureView planetView = new TextureView();
        planetView.setName("Planet");
        planetView.setTextureRegion(new TextureRegion(assetManager.get(planet, Texture.class)));
        planetView.setHeight(10f);
        planetView.setWidth(10f);
        planetView.position = new Vector2(0,10f);
        planetView.setAttachedTransform(bodyPlanet.getTransform());
        planetView.setLayer(0);

        ViewsComponent viewsComponent = entityBuilder.getComponent(ViewsComponent.class);
        viewsComponent.views.add(planetView);

        Entity entity = entityBuilder.getEntity();

        Gdx.app.log("Planet", "instance " + entity);
        return entity;

    }


}
