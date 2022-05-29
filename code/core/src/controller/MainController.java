package controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.DungeonCamera;
import resourceLoading.ResourceController;

/** The heart of the framework. From here all strings are pulled. */
public abstract class MainController extends Game {

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    private SpriteBatch batch;
    /** This batch is used to draw the HUD elements on it. */
    private SpriteBatch hudBatch;

    private DungeonCamera camera;
    private final ResourceController resourceController;
    private long targetFPS = 30;

    /**
     * <code>ApplicationListener</code> that delegates to the MainGameController. Just some setup.
     */
    public MainController() {
        this.resourceController = new ResourceController();
    }

    /**
     * sets the targeted fps count
     *
     * @param fps targeted fps coung
     */
    public void setTargetFPS(long fps) {
        this.targetFPS = fps;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        camera =
                setupCamera(
                        Lwjgl3ApplicationConfiguration.getDisplayMode().width / 16f,
                        Lwjgl3ApplicationConfiguration.getDisplayMode().height / 16f,
                        0.25f);
        setup();
    }

    /**
     * setup a new camera
     *
     * @param vitualWidth
     * @param virtualHeight
     * @param zoom
     */
    protected DungeonCamera setupCamera(float vitualWidth, float virtualHeight, float zoom) {
        DungeonCamera camera = new DungeonCamera(null, vitualWidth, virtualHeight);
        camera.zoom = zoom;
        return camera;
        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    protected abstract void setup();

    @Override
    public void render() {
        long frameStart = System.currentTimeMillis();
        update(Gdx.graphics.getDeltaTime());
        super.render();
        // execute waiting tasks in rest of frame
        do resourceController.runUIThreadTask();
        while (System.currentTimeMillis() - frameStart < 1000 / targetFPS - 10); // seconds to ms
    }

    protected abstract void update(float timeDelta);

    @Override
    public void dispose() {
        resourceController.interruptAll();
        super.dispose();
        batch.dispose();
        hudBatch.dispose();
    }

    protected ResourceController getResourceController() {
        return resourceController;
    }

    /**
     * registers a screen controller for this main controller
     *
     * @param screenController
     */
    public void registerScreenController(ScreenController screenController) {
        screenController.initilize(batch, hudBatch, camera, resourceController);
    }

    /**
     * changes the active screen controller for this main controller
     *
     * <p>if the screen controller is set up if not already
     *
     * @param screenController
     */
    public void changeScreenController(ScreenController sc) {
        if (!sc.isSetup()) registerScreenController(sc);
        setScreen(sc);
    }
}
