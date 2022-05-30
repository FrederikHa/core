package basiselements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Painter;

/**
 * A object that can be controlled by the <code>EntityController
 * </code>.
 */
public abstract class Entity extends DungeonElement {
    private Painter painter;
    private SpriteBatch batch;

    /**
     * @param painter Painter that draws this object
     * @param batch Batch to draw on
     */
    public void setPainterAndBatch(Painter painter, SpriteBatch batch) {
        this.painter = painter;
        this.batch = batch;
    }

    /** Draws this instance on the batch. */
    @Override
    public void draw() {
        painter.draw(getTexturePath(), getPosition(), batch);
    }
}
