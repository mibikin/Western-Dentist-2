package com.westerndentist.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level2 extends Stage {

    private WesternDentist game;

    private Sequencer sequencer;

    private Image background = new Image(new Texture("Images/WesternDentist_L2_Layer1_Background.png")),
            background2 = new Image(new Texture("Images/WesternDentist_L2_Layer2_Background.png"));

    private boolean bossSpawned = false;
    private boolean change = false;
    private double increment1 = 10;
    private double increment2 = 0.01;

    /**
     *
     * @param game
     * constructor
     * game - instance of the game
     */
    Level2(final WesternDentist game) {
        super(game.viewport);

        float stageWidth = 800,
                stageHeight = 600;

        background.setPosition(0, 0);
        background2.setPosition(0, 0);
        background2.setSize(stageWidth, stageHeight);
        addActor(background2);
        addActor(background);

        addActor(game.player);
        game.player.addAura();

        sequencer = new Sequencer();

        sequencer.addPhase(300);
        sequencer.addPhase(400);
        sequencer.addPhase(100000);

        for(int i = 0; i < 10; ++i) {
            sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/T-Pose_Luigi.png"), 10, 100, -200));
            sequencer.addPhaseSpawnFrequency(0, 30 + i);
            sequencer.addPhaseSpawnPosition(0, new Vector2(600 + i, 700 + i));
        }

        for(int i = 0; i < 10; ++i) {
            sequencer.addActorToPhase(0, new DiagonalEnemy(new Texture("Images/T-Pose_Luigi.png"), 10, 100, 200));
            sequencer.addPhaseSpawnFrequency(0, 50 + i);
            sequencer.addPhaseSpawnPosition(0, new Vector2(0, 700 + i));
        }

        BossTPoseQueen boss = new BossTPoseQueen();
        boss.addAction(Actions.moveTo(200, 250, 6));

        sequencer.addActorToPhase(2, boss);
        sequencer.addPhaseSpawnFrequency(2, 70);
        sequencer.addPhaseSpawnPosition(2, new Vector2(300, 1000));




        this.game = game;
    }

    /**
     *
     * @param delta
     * delta - time since last frame
     * act - The back bone of the level
     */
    @Override
    public void act(float delta) {
        backgroundScrolling(delta);
        sequencer.update(delta, this);
        checkBoss();
        super.act(delta);
    }

    /**
     * draw - Draws all entities on the level
     */
    @Override
    public void draw() {
        //sortActors();
        super.draw();
    }

    /**
     * checkBoss - Checks if the boss has been spawned if so play,
     * if not and needs to be spawned then spawn him, if dead then
     * go to next level.
     */
    private void checkBoss() {
        if (!bossSpawned) {
            try {

                for (Actor actor: getActors()) {
                    if (actor instanceof BossTPoseQueen) {
                        bossSpawned = true;
                        game.playMusic(true);
                    }
                }
            }
            catch (NullPointerException e) {
                Gdx.app.log("Level2 Check Boss: ","An object was deleted before it could be checked.");
            }
        }else if (bossSpawned) {
            for (Actor actor: getActors()) {
                if (actor instanceof BossTPoseQueen) {
                    return;
                }
            }
            if(!change) {
                change = true;
                game.player.remove();
                Level3 level3 = new Level3(game);
                game.changeStage(level3);
            }
        }
    }

    /**
     * backgroundScrolling - Adds functionality to make background move,
     * can be set to scroll or in this case move back and forth.
     * @param delta
     */
    private void backgroundScrolling(float delta) {
        background.setY((float) (background.getY() - increment1 * delta));
        background2.rotateBy((float) increment2);

        if (Math.abs(background2.getRotation()) >= 2) {
            this.increment2 *= -1;
        }
    }
}