import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Models.UserMouseEvent;

//a swing-based window for rendering the particle simulation.
public class JFrameWindow {
    
    private JFrame window;
    private JPanel frame;
    public UserMouseEvent userMouseEvent = null;
    private boolean postParticleinitialization = false;

    //creates a new simulation window with fixed size and title.
    public JFrameWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("SandBox Particle Simulator");
        window.setSize(1000, 1000);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
    }

    //updates the window by rendering the given environment 
    public void update(ParticleEnv env) {
        frame = new JPanel() {
        public void paintComponent(Graphics g) {

                //set background color to frame
                g.setColor(Color.decode("#828282"));
                g.fillRect(0, 0, 1000, 1000);

                //add each particle to the frame
                for(int y = 0; y < env.particles.length; y++) {
                    for (int x = 0; x < env.particles[0].length; x++) {
                        if(env.particles[y][x] != null) {
                            g.setColor(env.particles[y][x].color);
                            g.fillRect(x*5, y*5, 5, 5);
                        }
                    }
                }

                //add user actions to the screen (right/left click)
                if(userMouseEvent != null && userMouseEvent.notExpiredEvent) {
                    g.setColor(Color.darkGray);
                    g.fillOval(userMouseEvent.x-11,userMouseEvent.y-11, 20, 20);
                }

                //add text to the top right of the screen on what type of particle is selected
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 18));
                if(env.selectedParticle != null) {g.drawString("Select: " + env.selectedParticle.objectType, 20, 40);;}
                else {g.drawString("Select: air", 20, 40);;}

            }
        };
        
        //listen to mouse events
        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                userMouseEvent.y = e.getY();
                userMouseEvent.x = e.getX();
                userMouseEvent.isleftClick = SwingUtilities.isLeftMouseButton(e);
                userMouseEvent.isPressed = false;
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                userMouseEvent = new UserMouseEvent(SwingUtilities.isLeftMouseButton(e), true, e.getY(), e.getX());
            }
            public void mouseReleased(MouseEvent e) {
                userMouseEvent.notExpiredEvent = false;
            }
        });

        //add new frame to window
        window.repaint();
        window.add(frame);
        if(!postParticleinitialization) {window.setVisible(postParticleinitialization = true);}

    }

}
