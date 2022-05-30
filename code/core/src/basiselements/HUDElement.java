package basiselements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.HUDPainter;

/**
 * A object that can be controlled by the <code>HUDController
 * </code>.
 */
public abstract class HUDElement extends DungeonElement {
    private HUDPainter painter;
    private SpriteBatch batch;

    /**
     * @param painter Painter that draws this object
     * @param batch Batch to draw on
     */
    public void setPainterAndBatch(HUDPainter painter, SpriteBatch batch) {
        this.painter = painter;
        this.batch = batch;
    }

    /** Draws this instance on the batch. */
    @Override
    public void draw() {
        painter.draw(getTexturePath(), getPosition(), batch);
    }

    /**
     * Draws this instance on the batch with a scaling.
     *
     * @param xScaling x-scale
     * @param yScaling y-scale
     */
    public void drawWithScaling(float xScaling, float yScaling) {
        painter.drawWithScaling(xScaling, yScaling, getTexturePath(), getPosition(), batch);
    }
}
