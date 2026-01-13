package Models;

//represents a mouse interaction event that can add particles to the environment.
public class UserMouseEvent{

    public boolean isPressed;
    public boolean isleftClick;
    public boolean notExpiredEvent;
    public int x;
    public int y;

    //creates a new mouse event at the given position.
    public UserMouseEvent(boolean isleftClick, boolean isPressed, int y, int x) {
        this.isPressed = isPressed;
        this.isleftClick = isleftClick;
        this.x = x;
        this.y = y;
        this.notExpiredEvent = true;
    }
}
