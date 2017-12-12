package Infection;

import objectdraw.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;

public class StartScreen extends FilledRect {
    
    // Instance Variables
    private ArrayList<DrawableInterface> window;
    private Text title, promptText, continueText;
    private FilledRect continueButton;
    private VisibleImage start1;
    
    public StartScreen(Infection infection, DrawingCanvas canvas){
        super(0, 0, 0, 0, canvas);
        
        window = new ArrayList<>();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(infection);
        
        // load the image into the tracker
        Image start1 = toolkit.getImage("start1.jpeg");
        tracker.addImage(start1, 0);
        this.start1 = new VisibleImage(start1, 0, 0, canvas);
        this.start1.setSize(1280, 664);
        
        //create the title text
        title = new Text("Infection", 390, 160, canvas);
        title.setFontSize(120);
        title.setColor(Color.RED);
        
        //create the prompt text
        promptText = new Text("What is your disease?", 420, 350, canvas);
        promptText.setColor(Color.RED);
        promptText.setFont(new Font("", Font.BOLD, 40));
        
        
        //add all objects to the array list
        window.add(this.start1);
        window.add(title);
        window.add(promptText);
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
