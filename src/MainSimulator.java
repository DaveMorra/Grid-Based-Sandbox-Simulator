import java.util.Calendar;

//runs a basic fluid particle simulation in a JFrame window.
public class MainSimulator {

    //starts the simulation loop, updating the environment, rendering particles, and processing mouse input.
    public static void main(String[] args) {

        //initialise particle simulation
        ParticleEnv env = new ParticleEnv();
        JFrameWindow window = new JFrameWindow();
        long previousFrameTime, maxFrameRate = 120;

        //simulate particles until jframe window is closed, enforce max frames per second
        try {
            while(true) {
                previousFrameTime = Calendar.getInstance().getTimeInMillis();
                env.simulate(window.userMouseEvent);
                window.update(env);
                while((previousFrameTime + (1000/maxFrameRate)) > Calendar.getInstance().getTimeInMillis()) {continue;}
            }
        } catch (Exception e) {
            System.out.println("Error: Simulation error, cant update next frame");
        }
    }
}
