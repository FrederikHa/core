package graphic;

import basiselements.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import tools.Point;

/** Sauron's eye. */
public class DungeonCamera extends OrthographicCamera {
    private Entity follows;
    private Point focusPoint;

    /**
     * Creates a new camera.
     *
     * @param follows the entity the camera should follow, <code>null</code> for default coordinates
     * @param vw virtual width
     * @param vh virtual height
     */
    public DungeonCamera(Entity follows, float vw, float vh) {
        super(vw, vh);
        if (follows != null) {
            this.follows = follows;
        }
    }

    /** Updates camera position. */
    public void update() {
        if (follows != null) {
            Point fp = getFollowedObject().getPosition();
            position.set(fp.x, fp.y, 0);
        } else {
            if (focusPoint == null) {
                focusPoint = new Point(0, 0);
            }
            position.set(focusPoint.x, focusPoint.y, 0);
        }
        super.update();
    }

    /**
     * Sets the entity to follow.
     *
     * @param follows entity to follow
     */
    public void follow(Entity follows) {
        this.follows = follows;
    }

    /**
     * @return the entity the camera currently follows
     */
    public Entity getFollowedObject() {
        return follows;
    }

    /**
     * Stops following and set the camera on a fix position.
     *
     * @param focusPoint <code>Point</code> to set the camera on
     */
    public void setFocusPoint(Point focusPoint) {
        follows = null;
        this.focusPoint = focusPoint;
    }
}
