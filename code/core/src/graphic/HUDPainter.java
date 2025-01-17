package graphic;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import textures.TextureMap;
import tools.Point;

/** Uses LibGDX to draw sprites on the various <code>SpriteBatch</code>es. */
public class HUDPainter {
    private final TextureMap textureMap = new TextureMap();

    /** Draws the instance based on its position. */
    public void draw(String texturePath, Point position, SpriteBatch batch) {
        Texture texture = textureMap.getTexture(texturePath);
        Sprite sprite = new Sprite(texture);

        // set up scaling of textures
        sprite.setSize(texture.getWidth(), texture.getHeight());

        // where to draw the sprite
        sprite.setPosition(
                position.x,
                Lwjgl3ApplicationConfiguration.getDisplayMode().height
                        - position.y
                        - texture.getHeight());

        // need to be called before drawing
        batch.begin();
        // draw sprite
        sprite.draw(batch);
        // need to be called after drawing
        batch.end();
    }

    /** Draws the instance based on its position with default offset and specific scaling. */
    public void drawWithScaling(
            float xScaling, float yScaling, String texturePath, Point position, SpriteBatch batch) {
        Texture texture = textureMap.getTexture(texturePath);
        Sprite sprite = new Sprite(texture);

        // set up scaling of textures
        sprite.setSize(texture.getWidth() * xScaling, texture.getHeight() * yScaling);

        // where to draw the sprite
        sprite.setPosition(
                position.x,
                Lwjgl3ApplicationConfiguration.getDisplayMode().height
                        - position.y
                        - texture.getHeight() * yScaling);

        // need to be called before drawing
        batch.begin();
        // draw sprite
        sprite.draw(batch);
        // need to be called after drawing
        batch.end();
    }
}
