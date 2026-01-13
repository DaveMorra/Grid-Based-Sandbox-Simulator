package Models;
import java.awt.Color;


//a particle class that stores the attributes for each type of particle.
public class Particle{
    public String objectType;
    public boolean hasGravity;
    public boolean isFluid;
    public Color color;

    //a function that assigns parameters to each particle type.
    public Particle(String objectType, boolean hasGravity, boolean isFluid, Color color) {
        this.objectType = objectType;
        this.hasGravity = hasGravity;
        this.isFluid = isFluid;
        this.color = color;
    }
}
