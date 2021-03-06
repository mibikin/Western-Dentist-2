package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Abstract base class for powerups
 */
public abstract class Powerup extends Actor {

    protected float lifetime;
    protected float value;

    private float xMov = 1;
    private float yMov = -1;

    private RandomXS128 rng = new RandomXS128();
    protected Rectangle bounds = new Rectangle();

    /**
     * Defualt constructor
     */
    Powerup(boolean boss) {
        if (!boss) {
            xMov *= rng.nextFloat();
            if (xMov < 0.5) {
                xMov = -1;
            } else {
                xMov = 1;
            }
        }
        else {
            xMov = 0;
            yMov = -2;
        }
    }

    /**
     * Updates every time step
     * @param delta time since the last frame in seconds
     */
    @Override
    public void act(float delta) {
        super.act(delta);
        updateLifetime(delta);
        updateBounds();
        moveBy(10 * xMov * delta, 10 * yMov *  delta);
    }

    /**
     * Update lifetime
     * @param delta
     */
    private void updateLifetime(float delta) {
        lifetime -=  100 * delta;
        if (lifetime <= 0) {
            remove();
        }
    }

    /**
     * Destroy
     * @return value
     */
    public float destroy() {
        remove();
        return value;
    }

    /**
     * Update bounds
     */
    public void updateBounds() {
        bounds.setPosition(getX(), getY());
    }

    /**
     * Get bounds for collisions
     * @return Rectangle bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
