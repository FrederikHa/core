package controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.generator.IGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import resourceLoading.ResourceController;

public abstract class LevelScreenController extends ScreenController implements IOnLevelLoader {

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    @Override
    protected void initilize(
            SpriteBatch batch,
            SpriteBatch hudBatch,
            DungeonCamera camera,
            ResourceController resourceController) {
        super.initilize(batch, hudBatch, camera, resourceController);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
    }

    @Override
    protected void drawBackground() {
        levelAPI.update();
    }
}
