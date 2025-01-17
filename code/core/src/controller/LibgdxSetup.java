package controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** ApplicationListener that delegates to the MainGameController. Just some setup. */
public class LibgdxSetup extends Game {

    private MainController mc;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    private SpriteBatch batch;
    /** This batch is used to draw the HUD elements on it. */
    private SpriteBatch hudBatch;

    /**
     * <code>ApplicationListener</code> that delegates to the MainGameController. Just some setup.
     */
    public LibgdxSetup(MainController mc) {
        this.mc = mc;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        mc.setSpriteBatch(batch);
        mc.setHudBatch(hudBatch);
        setScreen(mc);
    }

    @Override
    public void dispose() {
        batch.dispose();
        hudBatch.dispose();
    }

    public void changeMainController(MainController mc) {
        this.mc = mc;
        if (mc.getSpriteBatch() == null) {
            create();
        } else {
            setScreen(mc);
        }
    }
}
