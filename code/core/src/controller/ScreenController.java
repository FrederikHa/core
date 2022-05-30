package controller;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import graphic.HUDPainter;
import graphic.Painter;
import java.util.logging.Logger;
import resourceLoading.ResourceController;

public abstract class ScreenController implements Screen {
    private static final Logger LOG = Logger.getLogger(ScreenController.class.getName());
    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    private SpriteBatch batch;

    protected EntityController entityController;
    private DungeonCamera camera;
    /** Draws objects */
    Painter painter;
    /** This batch is used to draw the HUD elements on it. */
    private SpriteBatch hudBatch;

    protected HUDController hudController;
    /** Draws hud */
    private HUDPainter hudPainter;

    private boolean setup = false;
    private ResourceController resourceController;

    // --------------------------- OWN IMPLEMENTATION ---------------------------

    protected abstract void setup();

    protected abstract void gainFocus();

    protected abstract void beginFrame();

    protected abstract void endFrame();

    protected abstract void loseFocus();

    // --------------------------- END OWN IMPLEMENTATION ------------------------

    /**
     * initilizes the state
     *
     * @param batch batch to paint on
     * @param hudBatch batch to paint the HUD on
     * @param camera camera for the batch
     * @param resourceController controller for multithreading
     */
    protected void initilize(
            SpriteBatch batch,
            SpriteBatch hudBatch,
            DungeonCamera camera,
            ResourceController resourceController) {
        if (setup) {
            LOG.info(this.getClass().getName() + " already initilizeed");
            return;
        }
        this.batch = batch;
        this.hudBatch = hudBatch;
        this.camera = camera;
        this.resourceController = resourceController;
        painter = new Painter(camera);
        entityController = new EntityController(painter, batch);
        hudPainter = new HUDPainter();
        hudController = new HUDController(hudPainter, hudBatch, resourceController);
        setup();
        setup = true;
    }

    @Override
    public final void show() {
        if (!setup) {
            LOG.severe(this.getClass().getName() + " not registerd in MainController");
            return;
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
    public final void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        if (stopLoop()) return;
        clearScreen();
        beginFrame();
        if (stopLoop()) return;
        camera.update();
        if (stopLoop()) return;
        drawBackground();
        if (stopLoop()) return;
        entityController.update();
        if (stopLoop()) return;
        hudController.update();
        if (stopLoop()) return;
        endFrame();
    }

    protected void drawBackground() {}

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
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

    protected boolean stopLoop() {
        return false;
    }

    /**
     * returns the dungeon camera
     *
     * @return the dungeon camera
     */
    protected DungeonCamera getCamera() {
        return camera;
    }

    boolean isSetup() {
        return setup;
    }

    protected ResourceController getResourceController() {
        return resourceController;
    }
}
