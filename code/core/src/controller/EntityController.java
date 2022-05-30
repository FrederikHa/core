package controller;

import basiselements.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;

/** Keeps a set of entities and calls their update method every frame. */
public class EntityController extends AbstractController<Entity> {
    private Painter painter;
    private SpriteBatch batch;

    EntityController(Painter painter, SpriteBatch batch) {
        this.painter = painter;
        this.batch = batch;
    }

    @Override
    public boolean add(Entity t, ControllerLayer layer) {
        t.setPainterAndBatch(painter, batch);
        return super.add(t, layer);
    }
}
