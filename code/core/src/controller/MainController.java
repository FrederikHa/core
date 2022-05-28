package controller;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import graphic.HUDPainter;
import graphic.Painter;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.generator.IGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import tools.Constants;

/** The heart of the framework. From here all strings are pulled. */
public abstract class MainController extends ScreenAdapter implements IOnLevelLoader {
    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    protected EntityController entityController;
    protected DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;
    /** This batch is used to draw the HUD elements on it. */
    protected SpriteBatch hudBatch;

    protected HUDController hudController;
    /** Draws hud */
    protected HUDPainter hudPainter;

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doFirstFocus = true;

    // --------------------------- OWN IMPLEMENTATION ---------------------------

    protected abstract void setup();

    protected abstract void gainFocus();

    protected abstract void beginFrame();

    protected abstract void endFrame();

    protected abstract void loseFocus();

    // --------------------------- END OWN IMPLEMENTATION ------------------------

    @Override
    public final void show() {
        if (doFirstFocus) {
            firstFocus();
        }
        gainFocus();
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        if (stopLoop()) return;
        clearScreen();
        beginFrame();
        if (stopLoop()) return;
        levelAPI.update();
        if (stopLoop()) return;
        entityController.update();
        if (stopLoop()) return;
        camera.update();
        if (stopLoop()) return;
        hudController.update();
        if (stopLoop()) return;
        endFrame();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void firstFocus() {
        doFirstFocus = false;
        entityController = new EntityController();
        setupCameras();
        painter = new Painter(camera);
        hudPainter = new HUDPainter();
        hudController = new HUDController(hudBatch);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        setup();
    }

    @Override
    public final void resume() {
        show();
    }

    @Override
    public final void pause() {
        hide();
    }

    @Override
    public final void hide() {
        loseFocus();
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public SpriteBatch getSpriteBatch() {
        return batch;
    }

    public void setHudBatch(SpriteBatch batch) {
        this.hudBatch = batch;
    }

    protected boolean stopLoop() {
        return false;
    }

    public SpriteBatch getHudBatch() {
        return hudBatch;
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }
}
