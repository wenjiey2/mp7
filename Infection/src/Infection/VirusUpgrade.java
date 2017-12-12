package Infection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;
import objectdraw.*;

public class VirusUpgrade extends FilledRect{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final double MAX_LEVEL = 20.0;
    private static final int WIDTH = 1280;
    private Text[] vuButtonTxt = new Text [12], vuButtonTxt2 = new Text [12];
    private final String[] vuButtonInfo = {"Increase the transmission rate ", 
        "Increase the trasmission rate ", "Increase the transmission rate ", 
            "Increase the transmission rate", "Influenza", "Pneumonia", 
            "Stroke", "Heart Attack", "Increase the virus' resistance ", 
            "Increase virus' resistance ", "Increase the virus' resistance ", 
            "Increase the DNA complexity", "across continents.", 
            "inside a continent.", "in rural areas.", "in urban areas.", "", "",
            "", "", "to hot temperature.", "to drugs.", "to cold temperature.", 
            "of the virus"};
    private VisibleImage background;
    private FilledRect trans, symt, resist, description, transmission, 
            lethality, resistance, tLevel,lLevel, rLevel, tOptions, sOptions,
            rOptions;
    private Text title, transTxt, symtTxt, resistTxt, dnaPoints, tTxt, lTxt, 
            rTxt;
    private Line virusAttributes;
    private ArrayList<DrawableInterface> window;
    
    public VirusUpgrade(Infection infection, DrawingCanvas canvas){
        
        super(0, 0, 0, 0, canvas);
        
        //initialize the arraylist
        window = new ArrayList<>();
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(infection);
        
        // load the image into the tracker
        Image virus = toolkit.getImage("virusBackground.jpeg");
        Image exit = toolkit.getImage("exit.jpeg");
        Image image2 = toolkit.getImage("dna.jpeg");
        tracker.addImage(virus, 0);
        tracker.addImage(exit, 0);
        tracker.addImage(image2, 0);
        
        // create the VisibleImage in the background
        background = new VisibleImage(virus, 0, 0, canvas);
        //this.exit = new VisibleImage(exit, 0, 0, canvas);
        background.setSize(WIDTH, 664);
        //this.exit.setSize(80, 60);
        
        //create the title
        title = new Text("Virus Upgrade", 90, 5, canvas);
        title.setFont(new Font("", Font.BOLD, 48));
        
        // create the three columns of virus upgrade options
        trans = new FilledRect(10, 70, 315, 60, canvas);
        trans.setColor(Color.PINK);
        transTxt = new Text("Transmission", 110, 90, canvas);
        transTxt.setFont(new Font("", Font.BOLD, 18));
        transTxt.setColor(Color.RED);
        tOptions = new FilledRect(10, 130, 315, 410, canvas);
        tOptions.setColor(new Color(0, 0, 0, Color.OPAQUE));
        
        symt = new FilledRect(325, 70, 315, 60, canvas);
        symt.setColor(Color.PINK);
        symtTxt = new Text("Symptoms", 425, 90, canvas);
        symtTxt.setFont(new Font("", Font.BOLD, 18));
        symtTxt.setColor(Color.RED);
        sOptions = new FilledRect(325, 130, 315, 410, canvas);
        sOptions.setColor(new Color(0, 0, 0, Color.OPAQUE));
        
        resist = new FilledRect(640, 70, 315, 60, canvas);
        resist.setColor(Color.PINK);
        resistTxt = new Text("Resistances", 740, 90, canvas);
        resistTxt.setFont(new Font("", Font.BOLD, 18));
        resistTxt.setColor(Color.RED);
        rOptions = new FilledRect(640, 130, 315, 410, canvas);
        rOptions.setColor(new Color(0, 0, 0, Color.OPAQUE));
        
        //create the box for the description
        description = new FilledRect(955, 70, 315, 470, canvas);
        description.setColor(new Color(0, 0, 0, Color.OPAQUE));
        
        //create an area to show the virus attributes
        virusAttributes = new Line(240, 600, 1280, 600, canvas);
        
        tTxt = new Text("Transmission", 390, 615, canvas);
        tTxt.setFont(new Font("", Font.BOLD, 10));
        transmission = new FilledRect(290, 633, 280, 10, canvas);
        transmission.setColor(new Color(0, 0, 0, Color.OPAQUE));
        tLevel = new FilledRect(290, 633, 1, 10, canvas);
        tLevel.setColor(Color.RED);
        
        lTxt = new Text("Lethality", 730, 615, canvas);
        lTxt.setFont(new Font("", Font.BOLD, 10));
        lethality = new FilledRect(620, 633, 280, 10, canvas);
        lethality.setColor(new Color(0, 0, 0, Color.OPAQUE));
        lLevel = new FilledRect(620, 633, 1, 10, canvas);
        lLevel.setColor(Color.RED);
        
        rTxt = new Text("Resistance", 1060, 615, canvas);
        rTxt.setFont(new Font("", Font.BOLD, 10));
        resistance = new FilledRect(950, 633, 280, 10, canvas);
        resistance.setColor(new Color(0, 0, 0, Color.OPAQUE));
        rLevel = new FilledRect(950, 633, 1, 10, canvas);
        rLevel.setColor(Color.RED);
        
        for(int i = 0; i < 12; i++){
            vuButtonTxt[i] = new Text(vuButtonInfo[i], 980, 200, canvas);
            vuButtonTxt[i].setFont(new Font("", Font.BOLD, 16));
            vuButtonTxt2[i] = new Text(vuButtonInfo[i+12], 980, 240, canvas);
            vuButtonTxt2[i].setFont(new Font("", Font.BOLD, 16));
            window.add(vuButtonTxt[i]);
            window.add(vuButtonTxt2[i]);
            vuButtonTxt[i].sendToBack();
            vuButtonTxt2[i].sendToBack();
        }
        
        window.add(background);
        window.add(trans);
        window.add(symt);
        window.add(resist);
        window.add(description);
        window.add(transmission);
        window.add(lethality);
        window.add(resistance);
        window.add(tLevel);
        window.add(lLevel);
        window.add(rLevel);
        window.add(tOptions);
        window.add(sOptions);
        window.add(rOptions);
        window.add(title);
        window.add(transTxt);
        window.add(symtTxt);
        window.add(resistTxt);
        window.add(tTxt);
        window.add(lTxt);
        window.add(rTxt);
        window.add(virusAttributes);
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
    
     public Text getDNAPoints(){
        return dnaPoints;
    }
    
    public Text getButtonInfo1(int i){
        return vuButtonTxt[i];
    }
    
    public Text getButtonInfo2(int i){
        return vuButtonTxt2[i];
    }
    
    public void setTRect(int vTransmission){
        tLevel.setWidth((vTransmission/MAX_LEVEL) * 280.0);
    }
    
    public void setLRect(int vLethality){
        lLevel.setWidth((vLethality/MAX_LEVEL) * 280.0);
    }
    
    public void setRRect(int vResistance){
        rLevel.setWidth((vResistance/MAX_LEVEL) * 280.0);
    }

}
