package basiselements;

import graphic.Animation;

/**
 * A object that has an <code>Animation</code>.
 *
 * <p>extends <code>Entity</code> Must be implemented for all objects that should be controlled by
 * the <code>EntityController
 * </code>.
 */
public abstract class Animatable extends Entity {

    /**
     * @return the current active <code>Animation</code> (example: idle or run)
     */
    public abstract Animation getActiveAnimation();

    @Override
    public String getTexturePath() {
        return getActiveAnimation().getNextAnimationTexturePath();
    }
}
