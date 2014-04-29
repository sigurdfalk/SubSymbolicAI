package implementations.tracker;

/**
 * User: sigurd
 * Date: 01.04.14
 * Time: 15:57
 */
public class TrackerObject {
    public static final int OBJECT_FALLING = 0;
    public static final int OBJECT_FALLING_LEFT = 1;
    public static final int OBJECT_FALLING_RIGHT = 2;

    private int type;
    private int size;
    private int startIndex;

    public TrackerObject(int type, int size, int startIndex) {
        this.type = type;
        this.size = size;
        this.startIndex = startIndex;
    }

    public int getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
