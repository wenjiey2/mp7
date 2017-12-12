package Infection;

import java.awt.Toolkit;
import java.awt.MediaTracker;
import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
import objectdraw.*;

public class EndScreen extends FilledRect{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Background
    private VisibleImage end1;
    private FilledRect end2;
    private Text lose;
    private boolean win;
    private ArrayList<DrawableInterface> window;
    
    
    //set the result, deaths, time, and etc. to be shown on the end screen
    public EndScreen(Infection infection, boolean win, DrawingCanvas canvas){
        super(0, 0, 0, 0, canvas);
        window = new ArrayList<>();
        this.win = win;
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(infection);
        
        // load the image into the tracker
        Image end1 = toolkit.getImage("end1.jpeg");
        tracker.addImage(end1, 0);
        if(this.win){
            this.end1 = new VisibleImage(end1, 0, 0, canvas);
            this.end1.setSize(1280, 664);
        }
        else{
            end2 = new FilledRect(0, 0, 1280, 664, canvas);
            end2.setColor(Color.CYAN);
            lose = new Text("Defeated", 500, 150, canvas);
            lose.setFontSize(60);
        }
        
        window.add(this.end1);
        window.add(end2);
        window.add(lose);
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
