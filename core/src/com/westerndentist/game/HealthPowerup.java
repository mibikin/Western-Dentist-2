package com.westerndentist.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class HealthPowerup extends Powerup {

    private Texture texture = new Texture("Images/healthPickup.png");

    HealthPowerup(float lifetime, float value, Vector2 position) {
        this.lifetime = lifetime;
        this.value = value;

        setPosition(position.x, position.y);
        bounds = new Rectangle(getX(), getY(), texture.getWidth(), texture.getHeight());
        setName("Health");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}