import java.util.ArrayList;
import java.awt.Color;
import Models.Particle;
import Models.UserMouseEvent;

//manages and simulates a collection of particles within the system
public class ParticleEnv {

    public ArrayList<Particle> elementList;
    public Particle[][] particles;
    public Particle selectedParticle;
    private int maxX = 198;
    private int maxY = 192;

    //initializes a grid representing the environment and an ArrayList containing all possible particle types
    public ParticleEnv(){

        this.elementList = new ArrayList<>();
        this.particles = new Particle[maxY][maxX];

        elementList.add(new Particle("sand", true, false, Color.decode("#F2E8D3")));
        elementList.add(new Particle("brick", false, false, Color.DARK_GRAY));
        elementList.add(new Particle("water", true, true, Color.BLUE));
        elementList.add(null);
        selectedParticle = elementList.get(0);

    }

    //given a mouse event, simulates the resulting behavior of the particles.
    public void simulate(UserMouseEvent mouseEvent){
        
        //simulate particles according to mouse event
        if(mouseEvent != null && mouseEvent.notExpiredEvent) {

            //if mouse event is a left click place blocks
            if(mouseEvent.isleftClick) {

                //place the selected block down onto the environment
                for(int y = (mouseEvent.y/5)-2; y < (mouseEvent.y/5)+4 ; y++) {
                    for(int x = (mouseEvent.x/5)-2; x < (mouseEvent.x/5)+3 ; x++) {
                        if(isParticleInBounds(y,x)) {particles[y][x] = selectedParticle;}
                    }
                }

            }

            //if mouse event is a right click toggle type of object that is getting selected for placement
            else if (mouseEvent.isPressed) {
                if(selectedParticle == null) {selectedParticle = elementList.get(0);}
                else {selectedParticle = elementList.get((1 + elementList.indexOf(selectedParticle)) % elementList.size());}
                mouseEvent.isPressed = false;
            }


        }

        //simulate each particle, and do so in a diaginal like pattern (doing every other pixel)
        for(int y = maxY - 1; y >= 0 ; y -= 2) {
            if(y %2==0) { for (int x = 0; x < maxX; x++) { particleMovment(y,x); } }
            else { for (int x = maxX - 1; x >= 0; x--) { particleMovment(y,x); } }
        }
        for(int y = maxY - 2; y >= 0 ; y -= 2) {
            if(y %2==0) { for (int x = 0; x < maxX; x++) { particleMovment(y,x); } } 
            else { for (int x = maxX - 1; x >= 0; x--) { particleMovment(y,x); } }
        }

    }

    //determines how each particle should be simulated based on its type.
    private void particleMovment(int y, int x) {

        //simulate movement according to particle type
        if(particles[y][x] == null) {return;}
        if(particles[y][x].isFluid) {particleMovmentFluid(y,x);}
        else if(particles[y][x].hasGravity) {particleMovmentGravity(y,x);}
        return;

    }

    //returns whether each particle is within the bounds of the environment.
    private boolean isParticleInBounds(int y, int x) {
        return (!(y < 0 || y >= maxY || x < 0 || x >= maxX));
    }

    //swaps the two particles and returns whether the swap was successful.
    private boolean particleSwap(int y1, int x1, int y2, int x2) {
        Particle p = particles[y1][x1];
        particles[y1][x1] = particles[y2][x2];
        particles[y2][x2] = p;
        return true;
    }

    //simulates particle movement for objects affected by gravity and returns whether the simulation was successful.
    private boolean particleMovmentGravity(int y, int x) {
        
        if(!isParticleInBounds(y+1, x)) {return false;}                                                             //if already at bottom of screen skip all steps
        if(isParticleInBounds(y+1, x) && particles[y+1][x] == null) { return particleSwap(y, x, y+1, x); }          //if particle has nothing under it, fall down a square
        if(isParticleInBounds(y+1, x) && particles[y+1][x].isFluid) { return particleSwap(y, x, y+1, x); }          //if particle has fluid under it, fall down a square and replace it
        if(isParticleInBounds(y+1, x+1) && particles[y+1][x+1] == null) { return particleSwap(y, x, y+1, x+1); }    //if particle has avalible spot down the right, move to it
        if(isParticleInBounds(y+1, x-1) && particles[y+1][x-1] == null) { return particleSwap(y, x, y+1, x-1); }    //if particle has avalible spot down the left, move to it
        return false;

    }

    //simulates particle movement for objects that are a fluid and returns whether the simulation was successful.
    private boolean particleMovmentFluid(int y, int x) {
        
        if(!isParticleInBounds(y+1, x)) {return false;}                                                                                             //if already at bottom of screen skip all steps
        if(isParticleInBounds(y+1, x) && particles[y+1][x] == null) { return particleSwap(y, x, y+1, x); }                                          //if particle has nothing under it, fall down a square
        if(isParticleInBounds(y+1, x+1) && particles[y+1][x+1] == null) { return particleSwap(y, x, y+1, x+1); }                                    //if particle has avalible spot down the right, move to it
        if(isParticleInBounds(y+1, x-1) && particles[y+1][x-1] == null) { return particleSwap(y, x, y+1, x-1); }                                    //if particle has avalible spot down the left, move to it
        if(isParticleInBounds(y+1, x+2) && particles[y+1][x+2] == null && particles[y+1][x+1].isFluid) { return particleSwap(y, x, y+1, x+2); }     //if particle has avalible spot down the right, move to it
        if(isParticleInBounds(y+1, x-2) && particles[y+1][x-2] == null && particles[y+1][x-1].isFluid) { return particleSwap(y, x, y+1, x-2); }     //if particle has avalible spot down the left, move to it
        if(isParticleInBounds(y, x+1) && particles[y][x+1] == null) { return particleSwap(y, x, y, x+1); }                                          //if particle has avalible spot to the right, move to it
        if(isParticleInBounds(y, x-1) && particles[y][x-1] == null) { return particleSwap(y, x, y, x-1); }                                          //if particle has avalible spot to the left, move to it
        return false;
    }
}
