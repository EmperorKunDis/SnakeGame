import javax.swing.*;


public class SnakeFrame extends JFrame {

    SnakeFrame(){

        this.add(new GamePanel());
        this.setTitle("Snake Game : Copied by Emperor.KunDis");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}