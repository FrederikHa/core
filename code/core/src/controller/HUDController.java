package controller;

import basiselements.HUDElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import graphic.HUDPainter;
import resourceLoading.ResourceController;

public class HUDController extends AbstractController<HUDElement> {
    private final HUDPainter painter;
    private final SpriteBatch batch;
    private final ResourceController resourceController;

    private Stage textStage;

    /**
     * Keeps a set of HUD elements and makes sure they are drawn.
     *
     * @param batch the batch for the HUD
     */
    public HUDController(
            HUDPainter painter, SpriteBatch batch, ResourceController resourceController) {
        this.painter = painter;
        this.batch = batch;
        this.resourceController = resourceController;
        resourceController.runOnUIThread(
                () -> {
                    textStage = new Stage(new ScreenViewport(), batch);
                });
    }

    @Override
    public boolean add(HUDElement t, ControllerLayer layer) {
        t.setPainterAndBatch(painter, batch);
        return super.add(t, layer);
    }

    /** Redraws the HUD and all HUD elements. */
    @Override
    public void update() {
        super.update();

        textStage.act();
        textStage.draw();
    }

    /** draws text from textstage */
    public void drawTextOnly() {
        textStage.draw();
    }

    /**
     * Draws a given text on the screen.
     *
     * @param text text to draw
     * @param fontPath path to the font
     * @param color color to use
     * @param size font size to use
     * @param width width of the text box
     * @param height height of the text box
     * @param x x-position in pixel
     * @param y y-position in pixel
     * @param borderWidth borderWidth for the text
     * @return Label (use this to alter text or remove the text later)
     */
    public Label drawText(
            String text,
            String fontPath,
            Color color,
            int size,
            int width,
            int height,
            int x,
            int y,
            int borderWidth) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = borderWidth;
        parameter.color = color;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        resourceController.runOnUIThread(
                () -> {
                    labelStyle.font = generator.generateFont(parameter);
                });
        generator.dispose();
        Label label = new Label(text, labelStyle);
        label.setSize(width, height);
        label.setPosition(x, Lwjgl3ApplicationConfiguration.getDisplayMode().height - y - height);

        textStage.addActor(label);
        return label;
    }

    /**
     * Draws a given text on the screen.
     *
     * @param text text to draw
     * @param fontPath path to the font
     * @param color color to use
     * @param size font size to use
     * @param width width of the text box
     * @param height height of the text box
     * @param x x-position in pixel
     * @param y y-position in pixel
     * @return Label (use this to alter text or remove the text later)
     */
    public Label drawText(
            String text,
            String fontPath,
            Color color,
            int size,
            int width,
            int height,
            int x,
            int y) {
        return drawText(text, fontPath, color, size, width, height, x, y, 1);
    }
}
