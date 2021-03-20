//Froggers.java
//Norika Upadhyay
//SImple game where frog faces obstacles trying to get to the other side
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.io.*;

public class Froggers extends JFrame {

    public Froggers() {
        super("Froggers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FrogPanel game = new FrogPanel();
        add(game);
        pack();
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Froggers frame = new Froggers();
    }
}

class FrogPanel extends JPanel implements KeyListener, ActionListener{
    private boolean []keys;//tells which key is pressed
    Timer myTimer;//controls frame rate
    private Timer gameTime,musicTime;//how long user has to complete round, how long the music goes
    private Time time;
    String mode = "intro";//what mode the program should be displaying
    private Image intro,bg,gameover,win;//background images
    private Frog frog;//main character
    private Cars car;//obstacles
    private Log1 log1;//has to get on these to continue
    private Log2 log2;
    private Log3 log3;
    private Turtle1 turt1;
    private Turtle2 turt2;
    private Goal goal;//last row
    private Font fnt;//font for text
    private Font scores;//font for displaying high scores
    private SoundEffect beep,start,death,lowTime,goalSound,music;//sound when key pressed,game starts up,frog dies,time's almost up,when goal is reached,and background music

    public FrogPanel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];

        time = new Time();
        myTimer = new Timer(100, this);
        int []controls = {KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN};

        gameover = new ImageIcon("gameOver.png").getImage();
        win = new ImageIcon("win.png").getImage();
        intro = new ImageIcon("intro.png").getImage();
        bg = new ImageIcon("bg.png").getImage();
        frog = new Frog(controls);

        car = new Cars();
        log1 = new Log1();
        log2 = new Log2();
        log3 = new Log3();
        turt1 = new Turtle1();
        turt2 = new Turtle2();

        goal = new Goal();
        fnt = new Font("Comic Sans", Font.BOLD,20);
        scores = new Font("Comic Sans", Font.PLAIN,30);

        beep = new SoundEffect("beep.wav");
        start = new SoundEffect("startUp.wav");
        start.play();//play start up sound
        death = new SoundEffect("death.wav");
        lowTime = new SoundEffect("lowTime.wav");
        goalSound = new SoundEffect("goal_sound.wav");
        music = new SoundEffect("music.wav");

        setPreferredSize(new Dimension(560, 640));
        addKeyListener(this);
    }

    // addNotify() triggers when the Panel is added to the frame.
    // If you start the timer early, you will get null-pointer exceptions
    //   x.y() will trigger a null-pointer if x is null.
    @Override
    public void addNotify() {
        super.addNotify();
        setFocusable(true);
        requestFocus();
        myTimer.start();
    }

    public void updateGame(){
        if(mode == "play") {
            time.increase();

            car.move();
            frog.carCollide(car);

            log1.move();
            log1.moveFrog(frog);
            log2.move();
            log2.moveFrog(frog);
            log3.move();
            log3.moveFrog(frog);
            turt1.move(time.get());
            turt1.moveFrog(frog);
            turt2.move(time.get());
            turt2.moveFrog(frog);

            goal.safety(frog,time,goalSound);
            time.countDown(death,lowTime);

            if (frog.died()){
                goal.highScore(frog);
                frog.winReset();
                goal.reset();
                time.restart();
                music.stop();
                musicTime.stop();
                mode = "gameover";
            }
            if (goal.complete()){
                goal.highScore(frog);
                frog.winReset();
                goal.reset();
                music.stop();
                musicTime.stop();
                mode = "win";
            }
        }
    }

    public void highScore(Graphics g){
        try {
            File highs = new File("highScore.txt");
            BufferedReader reader = new BufferedReader(new FileReader(highs));

            g.setFont(scores);
            g.setColor(Color.white);

            String line = reader.readLine();
            for (int i = 0; i < 5; i++) {
                g.drawString(line.split(" ")[0], 110, i * 58 + 245);
                g.drawString(line.split(" ")[1], 380, i * 58 + 245);
                line = reader.readLine();
            }
            reader.close();
        }catch (Exception e){
            System.out.println("error");
        }
    }

    @Override
    public void paint(Graphics g){
        if(mode == "intro"){
            g.drawImage(intro, 0, 0, null);
        }
        else if (mode == "gameover"){
            g.drawImage(gameover, 0, 0, null);
            highScore(g);
        }
        else if (mode == "win"){
            g.drawImage(win, 0, 0, null);
        }
        else {
            g.drawImage(bg, 0, 0, null);

            car.draw(g);
            log1.draw(g);
            log2.draw(g);
            log3.draw(g);
            turt1.draw(g);
            turt2.draw(g);
            frog.draw(g,death);

            goal.draw(g);

            g.setFont(fnt);
            time.draw(g,fnt);
            g.setColor(Color.white);
            g.drawString("SCORE", 0, 20);
            g.drawString(""+frog.getPoints(), 0, 45);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if(mode == "intro"){//if user presses key, game starts
            mode = "play";

            music.play();//start playing background music once the game begins
            musicTime = new Timer(14058, this);//how long the music is
            musicTime.start();

            gameTime = new Timer(60000, this);//one minute to do round
            gameTime.start();
        }
        if(mode == "gameover" || mode == "win"){//will go back to beginning once game is over
            mode = "intro";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        frog.move(e);
        beep.play();
        keys[e.getKeyCode()] = false;
    }
    @Override
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource()==gameTime){//if time goes up then user loses life and has to start from beginning
            time.restart();
            frog.loseLife();
        }
        if(evt.getSource()==musicTime){//loop music
            music.play();
        }
        updateGame();
        repaint();   // Asks the JVM to indirectly call paint
    }
}