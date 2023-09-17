import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 900;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 90;
    final int x[] = new int[GAME_UNIT];
    final int y[] = new int[GAME_UNIT];
    int startingBody = 4;
    int eatedHumans;
    int humanX;
    int humanY;
    char direction = 'R';
    boolean runnin = false;
    Timer timer;
    Random random;
    Random randomC;

    String color1 = "0x8D5524";
    String color2 = "0xC68642";
    String color3 = "0xE0AC69";
    String color4 = "0xF1C27D";
    String color5 = "0xFFDBAC";
    String bodyColor;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("0x348C31"));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyBind());
        letsPlain();
    }

    public void letsPlain() {
        bornHuman();
        runnin = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(runnin){
            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }


            g.setColor(Color.decode(color1));
            g.fillOval(humanX, humanY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i< startingBody;i++) {
                if (i == 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("ROG FONT",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE : "+eatedHumans, (SCREEN_WIDTH - metrics.stringWidth("SCORE : "+eatedHumans))/2,g.getFont().getSize());
        }
        else {
            dieMFdie(g);
        }
    }
    public void bornHuman() {
        int colx = random.nextInt(6)+1;
        humanX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        humanY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        if (colx == 1){
            bodyColor = color1;
        }
        if (colx == 2){
            bodyColor = color2;
        }
        if (colx == 3){
            bodyColor = color3;
        }
        if (colx == 4){
            bodyColor = color4;
        }
        if (colx == 5){
            bodyColor = color5;
        }
    }
    public void move() {
        for (int i = startingBody; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }
    public void areUEatin() {
        if((x[0] == humanX) && (y[0] == humanY)) {
            startingBody ++;
            eatedHumans ++;
            bornHuman();
        }
    }
    public void collisionChecker() {
        for (int i = startingBody;i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                runnin = false;
            }
        }
        if (x[0] < 0) {
            runnin = false;
        }
        if (x[0] > SCREEN_WIDTH) {
            runnin = false;
        }
        if (y[0] < 0) {
            runnin = false;
        }
        if (y[0] > SCREEN_HEIGHT) {
            runnin = false;
        }
        if(!runnin){
            timer.stop();

        }
    }
    public void dieMFdie(Graphics g) {

        g.setColor(Color.RED);
        g.setFont(new Font("ROG FONT",Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("You DIED", (SCREEN_WIDTH - metrics.stringWidth("You DIED"))/2, SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (runnin) {
            move();
            areUEatin();
            collisionChecker();
        }
        repaint();
    }
    public class MyKeyBind extends KeyAdapter{


        public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            }
        }

    }

}