package Infection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;
import objectdraw.*;

public class Map extends FilledRect{
    
    private ArrayList<DrawableInterface> window;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private VisibleImage worldMap, dnaPic, curePic, virus, virus2, virus3, 
            virus4, virus5, virus6, exit;
    private FilledRect dna, update, population, cure, selectInfo;
    private Text dnaPoints, updateTxt, populationTxt, cureTxt, sInfoTxt1,
            sInfoTxt2, sInfoTxt3;
    
    public Map(Infection infection, DrawingCanvas canvas){
        super(0, 0, 0, 0, canvas);
        
        window = new ArrayList<>();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(infection);
        
        // load the image into the tracker
        Image image = toolkit.getImage("worldmap.jpeg");
        Image image2 = toolkit.getImage("dna.jpeg");
        Image image3 = toolkit.getImage("cure.jpeg");
        tracker.addImage(image, 0);
        tracker.addImage(image2, 0);
        tracker.addImage(image3, 0);
        
        try {
            // load all the image for later use
            tracker.waitForAll();
        } catch (InterruptedException ex) {}
        
        
        
        // create the worldMap VisibleImage in the background
        worldMap = new VisibleImage(image, 0, 0, canvas);
        worldMap.setSize(WIDTH, 600);
        
        update = new FilledRect(240, 600, 240, 63, canvas);
        update.setColor(Color.WHITE);
        updateTxt = new Text("DISEASE UPGRADE", 290, 620, canvas);
        updateTxt.setFont(new Font("", Font.BOLD, 14));
        
        population = new FilledRect(480, 600, 260, 63, canvas);
        population.setColor(Color.WHITE);
        populationTxt = new Text("WORLD POPULATION", 530, 620, canvas);
        populationTxt.setFont(new Font("", Font.BOLD, 14));
               
        cure = new FilledRect(740, 600, 539, 63, canvas);
        cure.setColor(Color.WHITE);
        cureTxt = new Text("CURE PROGRESS: VIRUS NOT YET DETECTED", 820, 620, 
                canvas);
        cureTxt.setFont(new Font("", Font.BOLD, 14));
        curePic = new VisibleImage(image3, 755, 608, canvas);
        curePic.setSize(50, 50);
        
        //add all objects to the array list
        window.add(worldMap);
        window.add(update);
        window.add(updateTxt);
        window.add(population);
        window.add(populationTxt);
        window.add(cure);
        window.add(cureTxt);
        window.add(curePic);

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

    public Text getCureTxt(){
        return cureTxt;
    }
    
}