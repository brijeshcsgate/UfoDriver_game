
package ufodriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.imageio.ImageIO;


public class UfoDriver extends JPanel implements KeyListener {

   
   Media background,aftercrash,button,hits,reset,eject,sup;           
   MediaPlayer backgroundpl,aftercrashpl,buttonpl,hitspl,resetpl,ejectpl,suppl;
    float sccon = 1, speed = (float) 0.1;
    int hit=0,saved=0 , width, height, up = 0, score = 0, bhit=0,bsaved=0,bscore=0 ,max_hit=10;
    BufferedImage img,bs,bv,fs,fv,sp,np;
    
    boolean game = true,pause=false,gameon=false,bsound=true,fsound=true;
   
    String pau,bso,fso,imor;
    float radius = 18;
    float diameter = radius * 2;

    
    float X = radius + 150;
    float Y = radius + 245;
    static Game_try2[] pipe;
   
    float dy = 3;
    File bac;
    JFXPanel jp;

    public UfoDriver() {
       
        try{
           img=ImageIO.read(getClass().getResourceAsStream("img.png"));
           bs=ImageIO.read(getClass().getResourceAsStream("backsilnt.png"));
           bv=ImageIO.read(getClass().getResourceAsStream("backsound.png"));
            fs=ImageIO.read(getClass().getResourceAsStream("frontsilent.png"));
           fv=ImageIO.read(getClass().getResourceAsStream("frontsound.png"));
           sp=ImageIO.read(getClass().getResourceAsStream("super.png"));
           np=ImageIO.read(getClass().getResourceAsStream("nosuper.png"));
            jp=new JFXPanel() ;   
            background = new Media(getClass().getResource("Background.mp3").toString());    //  extracting audio
              
            eject=new Media(getClass().getResource("space.mp3").toString());
            button=new Media(getClass().getResource("button.mp3").toString());
           hits = new Media(getClass().getResource("hits.mp3").toString());
            reset=new Media(getClass().getResource("restart.mp3").toString());
            aftercrash=new Media(getClass().getResource("aftercrash.mp3").toString());
            sup=new Media(getClass().getResource("super.mp3").toString());
            
            backgroundpl = new MediaPlayer(background);
           ejectpl=new MediaPlayer(eject);
           buttonpl=new  MediaPlayer(button);                                               
           hitspl=new MediaPlayer(hits);
           resetpl=new MediaPlayer(reset);
          aftercrashpl=new MediaPlayer(aftercrash);
          suppl=new MediaPlayer(sup);
           backgroundpl.setOnEndOfMedia(new Runnable() {                                     
              public void run() {
         backgroundpl.seek(Duration.ZERO);
       }
   });
                  backgroundpl.play();
                  
           
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        Thread thread = new Thread() {    // for making frames in jpanel(where we can move objects)
            public void run() {
                hit=0;
                while (true) {

                    width = getWidth();
                    height = getHeight();

                    if(hit>=max_hit)
                    {    game=false;
                        
                    }
          if(game&&(!pause)&&(gameon))
          {
                    if (Y <= (radius + 140)) {
                        up = 0;
                    }
                    if (up > 0) {
                        if(up==8)
                        {
                            ejectpl.seek(Duration.ZERO);
                            ejectpl.play();
                        }
                        Y = Y - 5;
                        up--;
                    } else if (Y < (height - (radius + 150))) {
                        Y = Y + dy;
                        dy += 0.4;
                    }}
                    repaint();   //to call printcomponent
          
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                    }

                }
                
            }
        };
        thread.start();
    }

    public void paintComponent(Graphics g) {   //for drawing  every thing
        super.paintComponent(g);
        setBackground(Color.cyan);
        g.setColor(Color.red);

        g.fillRect(0, 0, width, 140);

        g.fillRect(0, 500, width, 140);
        for (int i = 0; i <= 140; i = i + 35) {
            g.setColor(Color.black);
            
            g.drawLine(0, i, width, i);
            g.drawLine(0, 500 + i, width, 500 + i);
        }
        for (int j = 0; j < 140; j = j + 35) {
            for (int i = 0; i <= width; i = i + 100) {
                g.setColor(Color.black);
                if ((j % 2) == 0) {
                    g.drawLine(i, j, i, j + 35);
                    g.drawLine(i, 500 + j, i, 500 + j + 35);
                } else {
                    g.drawLine(i + 35, j, i + 35, j + 35);
                    g.drawLine(i + 35, 500 + j, i + 35, 535 + j);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < pipe.length; i++) {
            if (pipe[i].c_1 > max) {
                max = pipe[i].c_1;
            }

        }
       
        if(bscore<=score)
        {
            bscore=score;
            bhit=hit;
            bsaved=saved;
        }
        
        Font myFont = new Font("Forte", Font.BOLD,25);
        g.setFont(myFont);
         g.setColor(Color.white);
        
           if(pause)
           {
               pau="resume";
           }
           else
           {
               pau="pause";
           }
           if(max_hit==10)
           {
             imor="imortal" ; 
           }
           else
           {
               imor="mortal" ;
           }
           if(bsound)
           {
               bso="stop";
           }
           else
           {
               bso="play";
           }
           if(fsound)
           {
               fso="stop";
           }
           else
           {
               fso="play";
           }
             g.drawString("<2  to "+pau+">",306,560);
          g.drawString("<3 to reset game>",606,560);
          g.drawString("<4 to be "+imor+">",456,602);
           g.drawString("<5 to "+bso+" bacground sound> ",40,55);
        g.drawString("Score :-" + score+"  total :- "+saved+"  hit:- "+hit, 700, 55);
           
           g.drawString("<6 to "+fso+" foreground sound>   ",40,100);
            g.drawString("best score (" +bscore+" )", 700,100);
    g.setColor(Color.blue);
        if(saved>0)
        {
        if(saved <10)
        {
            g.drawString("come on a 9 year old can play better",10,525) ;
        }
        else if(saved<30)
        {
            g.drawString("better",10,525) ;
        }
        else if(saved<40)
        {
            g.drawString("good",10,525) ;
        }
        else if(saved<50)
        {
            g.drawString("it's ok to lose ",10,525) ;
        }
        else if(saved<60)
        {
            g.drawString("padhe hote ieta to pass ho gate ",10,525) ;
        }
        else if(saved<70)
        {
            g.drawString("no do parents ka bolna pade ga aap ka bacha time barbad kar raha he",10,525) ;
        }
        else if(saved<100)
        {
            g.drawString("jaa ke padhe le nalayak",10,525) ;
        }
        else if(saved>100)
        { 
            g.drawString("  get life",10,525) ;
        }
        
        }
        if(game)
        {
            aftercrashpl.seek(Duration.ZERO);
        }
         else
         {
             aftercrashpl.play();
         }
        if(game&&(!pause)&&(gameon))
        {
        Random rand = new Random();
        for (int i = 0; i < pipe.length; i++) {
            if (pipe[i].c_1 <= (0 - 36)) {
                pipe[i].c_1 = max + 200;
                pipe[i].c_2 = max + 200;
                pipe[i].r = Color.GREEN;
                  int r1, r2;
                  r1 = rand.nextInt(300);
                r2 = rand.nextInt(358 - (70 + (r1 - 12)));
                pipe[i].r_1=r1;
                pipe[i].r_2=r2;
                
                saved++;
            }

            if (pipe[i].c_1 <= X && pipe[i].c_1 >= (X - 35)) {
                
                if ((Y >= (500 - pipe[i].r_2) || (Y - radius) <= (pipe[i].r_1 + 140))) {
                   
                    if(!(Color.RED.equals(pipe[i].r)))
                    {
                        hitspl.seek(Duration.ZERO);
                    hitspl.play();
                    hit++; 
                    }
                    
                     pipe[i].r = Color.RED;
                } else {
                    score += sccon;
                   
                }

            }

            pipe[i].c_1 -= speed;
            pipe[i].c_2 -= speed;
            if (speed <= 25) {
                speed += 0.001;
                sccon += 0.01;
            } else {
                sccon = 25;
            }}
         g.setColor(Color.YELLOW);
        g.fillOval((int) (X - radius)-18, (int) (Y - radius)+17, 25, (int) 5 );
        
            
        if(up>0)
        {
            g.fillOval((int) (X - radius), (int) (Y - radius)+25, 10, (int) 10);
        }
        }
       
        for (int k = 0; k < pipe.length; k++) { //int x=200,y=140;

            g.setColor(pipe[k].r);

            g.fillRect(pipe[k].c_1, 140, 35, pipe[k].r_1);
            g.fillRect(pipe[k].c_2, (500 - pipe[k].r_2), 35, pipe[k].r_2);

        }
        
             g.setColor(Color.GREEN);
        g.fillOval(445,20,78,78);
         g.fillOval(445,55,78,78);
         
      
        g.setColor(Color.GRAY);
        
        if(max_hit!=10)
        {
             g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) (X - radius), (int) (Y - radius), (int) diameter, (int) diameter);
        }
         g.drawImage(img, (int) (X - radius), (int) (Y - radius), (int) diameter, (int) diameter, null);
         if(bsound)
         {
            g.drawImage(bv,  465, 30, 45, 45, null); 
         }
         else
         {
              g.drawImage(bs,  465, 30, 45, 45, null); 
         }
         if(fsound)
         {
            g.drawImage(fv,  465, 80, 45, 45, null); 
         }
         else
         {
              g.drawImage(fs,  465, 80, 45, 45, null); 
         }
         if(max_hit!=10)
         {
             g.drawImage(sp,  2, 250, 70, 55, null); 
         }
         else
         {
             g.drawImage(np,  2, 250, 70, 55, null);
         }
         g.setColor(Color.BLACK);
          Font myFont2 = new Font("Forte", Font.BOLD,30);
        g.setFont(myFont2);
         g.setColor(Color.DARK_GRAY);
         
        if(!gameon )
            
        {
            name(g);
            g.setFont(myFont2);
            g.drawString("Press 1 to Start",300,350) ;
           g.drawString("Press space to control UFO", 300,375);
           g.drawString("his ship can only handel 10 hits", 300, 400);
           g.drawString("help him reach home ", 300, 425);
        }
        else if(!game )
        {   
            name(g);
            g.setFont(myFont2);
           g.drawString("UFO down",385,350);
            g.drawString("press 3 to try again",325,425);
        }
        if(pause)
        {
            name(g);
            g.setFont(myFont2);
             g.drawString("game paused press 2 to reasume",300,400) ;
              
        }
        
    }
    public void name(Graphics g)
    {
        g.drawImage(img, 215,180, 140, 140,null); 
         Font myFont = new Font("Forte", Font.BOLD,55);
        g.setFont(myFont);
         
         g.drawString("UFO Driver", 375, 275);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UFO driver");

        frame.setSize(1100, 680);
        in();
        UfoDriver b = new UfoDriver();
        frame.setIconImage(b.img);
        frame.setContentPane(b);
        frame.setVisible(true);

        frame.addKeyListener(b);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }

    static void in() {
        pipe = new Game_try2[7];
        int c = 200;
        Random rand = new Random();
        for (int i = 0; i < pipe.length; i++) {
            int r1, r2;
            r1 = rand.nextInt(300);
            r2 = rand.nextInt(358 - (70 + (r1 - 12)));
            pipe[i] = new Game_try2(r1, r2, c, c, Color.GREEN);
            c += 200;

        }
        
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            up = 8;
            dy = 3;
            //score=0;

        }
        if(e.getKeyChar()=='1')
        {
            buttonpl.seek(Duration.ZERO);
            buttonpl.play();
            gameon=true;
        }
        if(e.getKeyChar()=='2')
        {
             buttonpl.seek(Duration.ZERO);
            buttonpl.play();
            if(pause)
            {
                pause=false;
             
            }
            else
            {
            pause=true;
           
            }
        }
         if(e.getKeyChar()=='4')
        {
            buttonpl.seek(Duration.ZERO);
            buttonpl.play();
            if(max_hit==10)
            {
            max_hit=1000000000;
            suppl.seek(Duration.ZERO);
            suppl.play();
            }
            else
            {
                max_hit=10;
            }
        }
         if(e.getKeyChar()=='5')
         {
             buttonpl.seek(Duration.ZERO);
            buttonpl.play();
             if(bsound)
             {
                 backgroundpl.setMute(true);
                 bsound=false;
             }
             else
             {
                 backgroundpl.setMute(false);
                 bsound=true;
             }
         }
         if(e.getKeyChar()=='6')
         {
             buttonpl.seek(Duration.ZERO);
            buttonpl.play();
             if(fsound)
             {
                 buttonpl.setMute(true);
                 hitspl.setMute(true);
                 aftercrashpl.setMute(true);
                 resetpl.setMute(true);
                 ejectpl.setMute(true);
                 fsound=false;
             }
             else
             {
                 buttonpl.setMute(false);
                 hitspl.setMute(false);
                 aftercrashpl.setMute(false);
                 resetpl.setMute(false);
                 ejectpl.setMute(false);
                 fsound=true;
             }
         }
        if(e.getKeyChar()=='3')
        {
            resetpl.seek(Duration.ZERO);
            resetpl.play();
            in();
            score=0;
            sccon = 1;
            speed = (float) 0.1;
            hit=0;
            saved=0;
             X = radius + 150;
             Y = radius + 245;
             dy=3;
             gameon=false;
             pause=false;
             game=true;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
 class Game_try2 {

    public Game_try2(int r_1, int r_2, int c_1, int c_2, Color r) {
        this.r_1 = r_1;
        this.r_2 = r_2;
        this.c_1 = c_1;
        this.c_2 = c_2;
        this.r = r;

    }

    int r_1, r_2, c_1, c_2;
    Color r;

}
