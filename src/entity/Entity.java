package entity;

import graph.GraphNode;
import sceneElements.SpriteImage;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public abstract class Entity extends Observable {

    private static final Logger LOG = Logger.getLogger(Entity.class.getName());

    protected final int id;
    protected String name;
    protected String description;
    protected GraphNode position;

    protected double currentPixelX;
    protected double currentPixelY;

    protected SpriteImage sprite;


    public Entity(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.position = position;
        this.sprite = sprite;
    }

    public Entity(int id, String name, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.description = null;
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GraphNode getPosition() {
        return position;
    }

    public void setPosition(GraphNode position) {
        this.position = position;
    }

    public SpriteImage getSprite() {
        return sprite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSprite(SpriteImage sprite) {
        this.sprite = sprite;
    }

    public double getCurrentPixelX() {
        return currentPixelX;
    }

    public double getCurrentPixelY() {
        return currentPixelY;
    }

    public boolean idEquals(Entity e) {
        return id == e.id;
    }

    public abstract void update();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        if (id != entity.id) return false;
        if (Double.compare(entity.currentPixelX, currentPixelX) != 0) return false;
        if (Double.compare(entity.currentPixelY, currentPixelY) != 0) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (description != null ? !description.equals(entity.description) : entity.description != null) return false;
        if (!position.equals(entity.position)) return false;
        return sprite.equals(entity.sprite);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + position.hashCode();
        temp = Double.doubleToLongBits(currentPixelX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentPixelY);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + sprite.hashCode();
        return result;
    }
}
