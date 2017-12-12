package Infection;

import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.math.BigInteger;
import objectdraw.*;
import java.util.ArrayList;

public class PopulationWindow extends FilledRect{
    
     // Instance Variables
    private static final int ROW = 7;
    private VisibleImage background;
    private FilledRect[] slots = new FilledRect[28];
    private static final String[] popAll = {"354076086", "300863043", 
        "739115290", "1246504865", "4467213675", "40467040"};
    private FilledRect population;
    private Text populationTxt, conTxt, healthyTxt, infectedTxt, deadTxt;
    private Text[] Txt = new Text[24];
            /*saTxt, naTxt, europeTxt, africaTxt, asiaTxt, oceaniaTxt, saPopHTxt,
            naPopHTxt, euroPopHTxt, afrPopHTxt, asiaPopHTxt, ocePopHTxt, 
            saPopITxt, naPopITxt, euroPopITxt, afrPopITxt, asiaPopITxt,
            ocePopITxt, saPopDTxt, naPopDTxt, euroPopDTxt, afrPopDTxt, 
            asiaPopDTxt, ocePopDTxt;
            */
    private String[] continents = {"South America", 
        "North America", "Europe", "Africa", "Asia", "Oceania"};
    private Text[] popTxt = new Text[24];
    private ArrayList<DrawableInterface> window;
    

    public PopulationWindow(Infection infection, DrawingCanvas canvas){
        
        super(0, 0, 0, 0, canvas);
        
        //initialize the arrayList
        window = new ArrayList<>();
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(infection);
        
        // load the image into the tracker
        Image pop = toolkit.getImage("popBackground.jpeg");
        Image exit = toolkit.getImage("exit.jpeg");
        tracker.addImage(pop, 0);
        tracker.addImage(exit, 0);
        
        background = new VisibleImage(pop, 0, 0, canvas);
        background.setSize(1280, 664);
        
        population = new FilledRect(80, 0, 1280, 80, canvas);
        population.setColor(Color.GREEN);
        populationTxt = new Text("Population", 500, 20, canvas);
        populationTxt.setFont(new Font("", Font.BOLD, 18));
        
        for(int i = 0; i < slots.length; i += 4){
            slots[i] = new FilledRect(0, 80 + i * 83/ 4, 323, 83, canvas);
            slots[i].setColor(Color.LIGHT_GRAY);
            slots[i+1] = new FilledRect(323, 80 + i * 83/ 4, 323, 83, canvas);
            slots[i+1].setColor(Color.CYAN);
            slots[i+2] = new FilledRect(646, 80 + i * 83/ 4, 323, 83, canvas);
            slots[i+2].setColor(Color.PINK);
            slots[i+3] = new FilledRect(969, 80 + i * 83/ 4, 323, 83, canvas);
            slots[i+3].setColor(Color.BLACK);
            
            //add Rectangles to the window
            window.add(slots[i]);
            window.add(slots[i + 1]);
            window.add(slots[i + 2]);
            window.add(slots[i + 3]);
        }
        
        conTxt = new Text("Continent", 100, 100, canvas);
        conTxt.setFont(new Font("", Font.BOLD, 18));
        
        healthyTxt = new Text("Healthy", 400, 105, canvas);
        healthyTxt.setFont(new Font("", Font.BOLD, 18));
        
        infectedTxt = new Text("Infected", 700, 105, canvas);
        infectedTxt.setFont(new Font("", Font.BOLD, 18));
        
        deadTxt = new Text("Dead", 1000, 105, canvas);
        deadTxt.setFont(new Font("", Font.BOLD, 18));
        deadTxt.setColor(Color.WHITE);
        
        
        window.add(background);
        window.add(population);
        window.add(populationTxt);
        window.add(conTxt);
        window.add(healthyTxt);
        window.add(infectedTxt);
        window.add(deadTxt);
        
    }
    
    //show and hide methods
    public void hide(){
        for (int i = 0; i < window.size(); i ++) {
            
            window.get(i).hide();
            
        }
    }
    
    public void show(){
        for (int i = 0; i < window.size(); i ++) {
            
            window.get(i).show();
            
        }
    }
}