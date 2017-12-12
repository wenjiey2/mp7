package Infection;

import java.math.BigInteger;
import Infection.Cure.OnCureProcess;
import Infection.Dates.OnTimeChanged;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextField;
import objectdraw.*;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;

public class Infection extends WindowController{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final BigInteger[] initialPop = {new BigInteger("354076086"), 
    new BigInteger("300863043"), new BigInteger("739115290"), 
    new BigInteger("1246504865"), new BigInteger("4467213675"),
    new BigInteger("40467040")};
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private int dPoints, chosenButton, firstInfected;
    private int vLevel [] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private Text[] virusLevel = new Text[12];
    private Virus virus;
    private Population pop;
    private StartScreen startScreen;
    private TypeScreen typeScreen;
    private Map map;
    private PopulationWindow popWindow;
    private VirusUpgrade virusUpgrade;
    private TextField tf;
    private Container contentPane;
    private FilledRect continueButton, enter, update, population, selectInfo,
            get, dateRect, playRect, pauseRect, dna, cureInfo, 
            progress;
    private FramedRect progressFrame;
    private boolean startScreenOpen, typeScreenOpen, mapOpen, populationOpen,
            virusUpgradeOpen, endScreenOpen, cureStart, cInfoShown, timerPlay;
    private boolean[] checkMigrate = {false, false, false, false, false, false};
    private boolean infectChosen, lethalChosen, resistChosen,
            chosen, firstClick,chosenUpgrade;
    private Text continueText, updateTxt, populationTxt, enterTxt, sInfoTxt1, 
            sInfoTxt2, sInfoTxt3, getTxt, dateTxt, dnaPointsTxt, price, 
            cureInfoTxt1, cureInfoTxt2;
    private VisibleImage infectious, lethal, resistant, exit, exitPW, exitVU,pause, play, dnaPic, exit2;
    private String[] continents;
    private ArrayList<DrawableInterface> ssObjects;
    private ArrayList<DrawableInterface> tsObjects;
    private ArrayList<DrawableInterface> mapObjects;
    private ArrayList<DrawableInterface> vuObjects;
    private ArrayList<DrawableInterface> pwObjects;
    private ArrayList<DrawableInterface> esObjects;
    private ArrayList<DrawableInterface> cObjects;
    private ArrayList<DrawableInterface> sInfo;
    private ArrayList<DrawableInterface> tsInfo;
    private ArrayList<DrawableInterface> cInfo;
    private Text[] popTxt = new Text[24];
    private Text[] virusInfo = new Text [6];
    private VisibleImage[] upgradeButton = new VisibleImage[12];
    private VisibleImage[] virusImage = new VisibleImage [6];
    private Text[] percentDead = new Text[6];
    private FilledRect[] allUpgrades = new FilledRect[12];
    private Toolkit toolkit;
    private MediaTracker tracker;
    private Calendar cal;
    private Dates date;
    private Cure cure;
    
    public Infection(){
        //create the virus and population classes
        virus = new Virus();
        pop = new Population();
        
        //set startScreenOpen to true
        startScreenOpen = true;
        
        //initialize all arrays
        continents = new String[] {"South America", "North America", "Europe", 
            "Africa", "Asia", "Oceania"};
        ssObjects = new ArrayList<>();
        tsObjects = new ArrayList<>();
        mapObjects = new ArrayList<>();
        vuObjects = new ArrayList<>();
        pwObjects = new ArrayList<>();
        esObjects = new ArrayList<>();
        cObjects = new ArrayList<>();
        sInfo = new ArrayList<>();
        tsInfo = new ArrayList<>();
        cInfo = new ArrayList<>();
        
        //initialize the toolkit and tracker
        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);
        
         
        startController(WIDTH, HEIGHT);
        
    }
    
    public void begin(){
        //initialize the contentPane
        contentPane = getContentPane();
        
        //create the StartScreen
        createSS();
        
        //create the TypeScreen
        createTS();
        
        //create the map
        createMap();
        
        //create the DNA Rect
        createDNARect();
        
        //create the date rectangle to go with the map
        createDateRect();
        
        //create the population window
        createPW();
        
        //create the virus upgrade window
        createVU();
        
        //create the textField for the StartScreen
        tf = new TextField();
        contentPane.add(tf, BorderLayout.SOUTH);
        contentPane.validate();
        tf.setBounds(384, 441, 512, 74);
        contentPane.revalidate();
        tf.setText("Black Death");
        tf.setBackground(Color.WHITE);
        
        //create the text for the type screen
        createTSTxt();
        
        //override
        date.setOnTimeChanged(new OnTimeChanged(){
            @Override
            public void timeChanged(Calendar calendar) {
                dateTxt.setText((date.calendar.get(Calendar.MONTH) + 1 ) + 
                    " / " + date.calendar.get(Calendar.DATE) + " / " + 
                    date.calendar.get(Calendar.YEAR));
                if(date.getDuration() % 3 == 0){
                    dPoints++;
                }
                if(map != null){
                    setDNAPoints(dPoints);
                    //setPopulation();
                }
                checkGameOver();
                
                //check to see if the cure should start
                if(!cureStart){
                    checkCure();
                }
                
                //check to see if the virus migrates
                for(int i = 0; i < 6; i++){
                    if(!checkMigrate[i]){
                        checkMigrate();
                    }
                }
                //spread the virus
                pop.spread(virus);
                //kill infected people
                pop.kill(virus);
                //migrate the virus across a continent
                pop.migrate();
                
                //update the percent text
                if(chosen){
                   updatePercentText(); 
                }
            }
        });
        
        cure.setOnCureProcess(new OnCureProcess(){
            @Override
            public void cureProcess(int percent){
                map.getCureTxt().setText(" Research Progress: " + 
                        cure.getPercent() + "%");
                progress.setWidth(2 * cure.getPercent());
                map.getCureTxt().setFont(new Font("", Font.BOLD, 14));
            }    
        });
        
        //send the continueButton and ContinueText to the front
        continueButton.sendToFront();
        continueText.sendToFront();
        
    }
    
    //method to create the StartScreen
    public void createSS(){
        startScreen = new StartScreen(this, canvas);
        ssObjects.add(startScreen);
        
        //StartScreen buttons
        continueButton = new FilledRect(512, 544, 256, 74, canvas);
        continueButton.setColor(Color.white);
        continueText = new Text("Continue", 557, 551, canvas);
        continueText.setFontSize(40);
        continueText.setColor(Color.RED);
        ssObjects.add(continueButton);
        ssObjects.add(continueText);
    }
    
    //method to create the TypeScreen
    public void createTS(){
        // load the image into the tracker
        // load the image into the tracker
        Image[] tsImages = new Image[] {toolkit.getImage("infectious.jpeg"), 
            toolkit.getImage("lethal.jpeg"), toolkit.getImage("resistant.jpeg")};
        
        for (Image image: tsImages){
            tracker.addImage(image, 0);
        }
        
        //create the Type screen and hide it
        typeScreen = new TypeScreen(this, canvas);
        tsObjects.add(typeScreen);
        
        //TypeScreen buttons
        enter = new FilledRect(330, 500, 300, 75, canvas);
        enter.setColor(Color.white);
        enterTxt = new Text("ENTER THE GAME", 350, 515, canvas);
        enterTxt.setFontSize(30);
        enterTxt.setColor(Color.RED);
        
        infectious = new VisibleImage(tsImages[0], 160, 270, canvas);
        lethal = new VisibleImage(tsImages[1], 380, 270, canvas);
        resistant = new VisibleImage(tsImages[2], 600, 270, canvas);
        
        infectious.setSize(120, 120);
        lethal.setSize(120, 120);
        resistant.setSize(120, 120);
        
        tsObjects.add(enter);
        tsObjects.add(enterTxt);
        tsObjects.add(infectious);
        tsObjects.add(lethal);
        tsObjects.add(resistant);
        hide(tsObjects);
    }
    
    //method to create the map
    public void createMap(){
        // load the image into the tracker
        Image[] mapImages = new Image[] {toolkit.getImage("virus.jpeg"), 
            toolkit.getImage("exit.jpeg")};
        
        for (Image image: mapImages){
            tracker.addImage(image, 0);
        }
        
        //create the Map object and hide it 
        map = new Map(this, canvas);
        mapObjects.add(map);
        
        //Map buttons
        update = new FilledRect(240, 600, 240, 63, canvas);
        update.setColor(Color.WHITE);
        
        updateTxt = new Text("DISEASE UPGRADE", 290, 620, canvas);
        updateTxt.setFont(new Font("", Font.BOLD, 14));
        
        
        population = new FilledRect(480, 600, 260, 63, canvas);
        population.setColor(Color.WHITE);
        
        populationTxt = new Text("WORLD POPULATION", 530, 620, canvas);
        populationTxt.setFont(new Font("", Font.BOLD, 14));
        
        //create the virus image
        virusImage = new VisibleImage[] {new VisibleImage(mapImages[0], 270, 350, canvas), 
            new VisibleImage(mapImages[0], 140, 120, canvas), 
            new VisibleImage(mapImages[0], 560, 100, canvas),
            new VisibleImage(mapImages[0], 580, 250, canvas),
            new VisibleImage(mapImages[0], 850, 100, canvas),
            new VisibleImage(mapImages[0], 1000, 350, canvas)};
        
        virusImage[0].setSize(50, 50);
        virusImage[1].setSize(40, 40);
        virusImage[2].setSize(30, 30);
        virusImage[3].setSize(50, 50);
        virusImage[4].setSize(50, 50);
        virusImage[5].setSize(35, 35);
        
        percentDead[0] = new Text(pDead(0) + "%", 270, 350, canvas);
        percentDead[1] = new Text(pDead(1) + "%", 140, 120, canvas);
        percentDead[2] = new Text(pDead(2) + "%", 560, 100, canvas);
        percentDead[3] = new Text(pDead(3) + "%", 580, 250, canvas);
        percentDead[4] = new Text(pDead(4) + "%", 850, 100, canvas);
        percentDead[5] = new Text(pDead(5) + "%", 1000, 350, canvas);
        
        for (int i = 0; i < percentDead.length; i++){
            percentDead[i].setFont(new Font("", Font.BOLD, 18));
            percentDead[i].hide();
            mapObjects.add(percentDead[i]);
        }
        
        selectInfo = new FilledRect(350, 220, 550, 200, canvas);
        selectInfo.setColor(Color.WHITE);
        
        sInfoTxt1 = new Text("Pick the continent you want to start "
                + "the infection", 400, 260, canvas);
        sInfoTxt1.setFont(new Font("", Font.BOLD, 18));
        
        sInfoTxt2 = new Text("Click on one of the infection buttons put "
                + "your virus: ", 400, 310, canvas);
        sInfoTxt2.setFont(new Font("", Font.BOLD, 18));
        
        exit = new VisibleImage(mapImages[1], 350, 220, canvas);
        exit.setSize(40, 30);
        
        cureInfo = new FilledRect(350, 220, 550, 200, canvas);
        cureInfo.setColor(Color.WHITE);
        
        cureInfoTxt1 = new Text("The virus has been detected.", 400, 260, canvas);
        cureInfoTxt1.setFont(new Font("", Font.BOLD, 18));
        cureInfoTxt2 = new Text("A research team is established to find a cure."
                , 400, 310, canvas);
        cureInfoTxt2.setFont(new Font("", Font.BOLD, 18));
        exit2 = new VisibleImage(mapImages[1], 350, 220, canvas);
        exit2.setSize(40, 30);
        
        progress = new FilledRect(1020, 622, 1, 25, canvas);
        progress.setColor(Color.GREEN);
        cObjects.add(progress);
        
        progressFrame = new FramedRect(1018, 620, 204, 30, canvas);
        progressFrame.setColor(Color.BLACK);
        cObjects.add(progressFrame);
        
        hide(cObjects);
        
        cInfo.add(cureInfo);
        cInfo.add(cureInfoTxt1);
        cInfo.add(cureInfoTxt2);
        cInfo.add(exit2);
        
        sInfo.add(selectInfo);
        sInfo.add(sInfoTxt1);
        sInfo.add(sInfoTxt2);
        sInfo.add(exit);
       
        mapObjects.add(update);
        mapObjects.add(updateTxt);
        mapObjects.add(population);
        mapObjects.add(populationTxt);
        
        for (int i = 0; i < virusImage.length; i ++){
            mapObjects.add(virusImage[i]);
        }

        hide(cInfo);
        hide(sInfo);
    }
    
    //method to create the population window
    public void createPW(){
        Image image6 = toolkit.getImage("exit.jpeg");
        tracker.addImage(image6, 0);
        
        //create the PopulationWindow and hide it
        popWindow = new PopulationWindow(this, canvas);
        pwObjects.add(popWindow);
        
        //PopulationWindow Objects
        exitPW = new VisibleImage(image6, 0, 0, canvas);
        exitPW.setSize(80, 80);
        pwObjects.add(exitPW);
        hide(pwObjects);
    }
    
    //method to create the virus upgrade window
    public void createVU(){
        // load the image into the tracker
        Image image6 = toolkit.getImage("exit.jpeg");
        tracker.addImage(image6, 0);
        Image[] upgradeButtons = {toolkit.getImage("water.jpeg"), 
            toolkit.getImage("air.jpeg"), toolkit.getImage("wildanimal.jpeg"), 
            toolkit.getImage("rat.jpeg"), toolkit.getImage("cough.jpeg"), 
            toolkit.getImage("pneumonia.jpeg"), toolkit.getImage("stroke.jpeg"), 
            toolkit.getImage("heartattack.jpeg"), 
            toolkit.getImage("hotresist.jpeg"), 
            toolkit.getImage("resistant.jpeg"), 
            toolkit.getImage("coldresist.jpeg"),
            toolkit.getImage("complexity.jpeg")};
        for(int i = 0; i < 12; i++){
            tracker.addImage(upgradeButtons[i], 0);
        }
        
        //create the VirusUpgradeWindow and hide it
        virusUpgrade = new VirusUpgrade(this, canvas);
        vuObjects.add(virusUpgrade);
        
        // Upgrade buttons
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 9; j+=4){
                if(i % 2 == 0){
                    allUpgrades [i+j]= new FilledRect(80 + 80 * j, 180 + i * 80,
                            80, 80, canvas);
                    allUpgrades[i+j].setColor(Color.WHITE);
                    upgradeButton[i+j] = new VisibleImage(upgradeButtons
                            [i+j], 85 + 80 * j, 185 + i * 80, canvas);
                    upgradeButton[i+j].setSize(65, 65);
                    virusLevel [i+j]= new Text(vLevel[i+j], 148 + 80 * j, 240 + 
                            i * 80, canvas);
                    virusLevel[i+j].setFontSize(18);
                }
                else{
                    allUpgrades [i+j]= new FilledRect(160 + 80 * j, 180 + i * 80,
                            80, 80, canvas);
                    allUpgrades[i+j].setColor(Color.WHITE);
                    upgradeButton[i+j] = new VisibleImage(upgradeButtons
                            [i+j], 165 + 80 * j, 185 + i * 80, canvas);
                    upgradeButton[i+j].setSize(65, 65);
                    virusLevel [i+j]= new Text(vLevel[i+j], 228 + 80 * j, 240 + 
                            i * 80, canvas);
                    virusLevel [i+j].setFontSize(18);
                    }
            }
        }
        for(int i = 0; i < 12; i++){
            vuObjects.add(allUpgrades[i]);
            vuObjects.add(upgradeButton[i]);
            vuObjects.add(virusLevel[i]);
        }
        
        get = new FilledRect(1035, 460, 155, 60, canvas);
        get.setColor(Color.lightGray);
        getTxt = new Text("Upgrade", 1070, 475, canvas);
        getTxt.setFont(new Font("", Font.BOLD, 22));
        vuObjects.add(get);
        vuObjects.add(getTxt);

        price = new Text("", 980, 350, canvas);
        price.setFont(new Font("", Font.BOLD, 14));
        price.hide();
        
        exitVU = new VisibleImage(image6, 0, 0, canvas);
        exitVU.setSize(70, 70);
        vuObjects.add(exitVU);
        hide(vuObjects);

        vuObjects.add(get);
        vuObjects.add(getTxt);
        hide(vuObjects);
    }
    
    //create the DNA Rectangle
    public void createDNARect(){
        Image image2 = toolkit.getImage("dna.jpeg");
        tracker.addImage(image2, 0);
        
        //create the Rectangle
        dna = new FilledRect(0, 600, 240, 63, canvas);
        dna.setColor(Color.WHITE);
        dnaPointsTxt = new Text("DNA POINTS: 0", 70, 620, canvas);
        dnaPointsTxt.setFont(new Font("", Font.BOLD, 14));
        dnaPic = new VisibleImage(image2, 1, 602, canvas);
        dnaPic.setSize(60, 60);
        
        //add rectangle to the vuObjects and mapObjects
        vuObjects.add(dna);
        vuObjects.add(dnaPointsTxt);
        vuObjects.add(dnaPic);
        mapObjects.add(dna);
        mapObjects.add(dnaPointsTxt);
        mapObjects.add(dnaPic);
    }
    
    public void createDateRect(){
        // load the image into the tracker
        Image image[] = {toolkit.getImage("pause.jpeg"), 
            toolkit.getImage("play.jpeg")};
        
        dateRect = new FilledRect(1100, 0, 179, 45, canvas);
        dateRect.setColor(Color.WHITE);
        
        dateTxt = new Text("", 1130, 10, canvas);
        dateTxt.setFont(new Font("",Font.BOLD, 16));

        pauseRect = new FilledRect(1007, 0, 46, 46, canvas);
        pauseRect.setColor(Color.white);
        pause = new VisibleImage(image[0], 1007, 0, canvas);
        pause.setSize(45, 45);
        playRect = new FilledRect(1054, 0, 46, 46, canvas);
        playRect.setColor(Color.white);
        play = new VisibleImage(image[1], 1054, 0, canvas);
        play.setSize(42, 42);

        
        // initialize the calendar and dates objects
        cal = Calendar.getInstance();
        date = new Dates(cal);
        cure = new Cure();
        
        mapObjects.add(dateRect);
        mapObjects.add(dateTxt);
        mapObjects.add(pauseRect);
        mapObjects.add(playRect);
        mapObjects.add(pause);
        mapObjects.add(play);
        
        //hide the mapObjects now that everything for the map has been created
        hide(mapObjects);
    }
    
    //method to create the text for the type screen
    public void createTSTxt(){
        //create the text for TypeScreen
        for(int i = 0; i < 6; i++){
            if(i < 3){
                virusInfo[i] = new Text("", 840, 260 + 50 * i, canvas);
            }
            else if(i != 5){
                virusInfo[i] = new Text("", 840, 360, canvas);
            }
            else {
                virusInfo[i] = new Text("", 840, 410, canvas);
            }
            virusInfo[i].setFontSize(20);
            virusInfo[i].setColor(Color.RED);
            virusInfo[i].hide();
        }
        
        virusInfo[0].setText("In the initial setting of the three ");
        virusInfo[1].setText("features, the virus will have a ");
        virusInfo[2].setText("higher potential to infect people.");
        virusInfo[3].setText("higher potential to kill infected people.");
        virusInfo[4].setText("higher resistance to drugs, which can ");
        virusInfo[5].setText("slower down the research process.");
       
        for(int i = 0; i < 6; i++){
            tsInfo.add(virusInfo[i]);
        }

    }
    
    //go to Type Screen method
    public void toTypeScreen(){
        //change the screen
//        typeScreen.show();
//        startScreen.hide();
        
        //change boolean variables
        typeScreenOpen = true;
        startScreenOpen = false;
        
        //hide ssObjects
        hide(ssObjects);
        
        //show buttons
        show(tsObjects);
    }
    
    //go to map method
    public void toMap(){
        //open the map and change boolean variable
        //map.show();
        mapOpen = true;
        playRect.setColor(Color.CYAN);
        pauseRect.setColor(Color.white);
        
        if (!firstClick){
            sInfoTxt3 = new Text(virus.getName(), 400, 360, canvas);
            sInfoTxt3.setFont(new Font("", Font.BOLD, 18));
            sInfo.add(sInfoTxt3);
            show(sInfo);
        }
        if(chosen){
            date.resumeDate();
        }
        if(cureStart){
            cure.resumeDate();
        }
        
        //hide the screen that is already open
        if (typeScreenOpen){
            typeScreenOpen = false;
            
            //hide buttons
            hide(tsObjects);
            hide(tsInfo);
            
        }
        else if (populationOpen){
            //hide the populationWindow
            hide(pwObjects);
            remove(popTxt);
            populationOpen = false;
        }
        else if (virusUpgradeOpen){
            //hide the virusUpgradeWindow
            hide(vuObjects);
            virusUpgradeOpen = false;
        }
         
        //show buttons and text
        show(mapObjects);
        if(chosen){
            for(int i = 0; i < 6; i++){
                virusImage[i].hide();
            }
        }
        
        //hide percent dead if the continent is not chosen for the virus
        if(!chosen){
            for (int i = 0; i < percentDead.length; i++){
                percentDead[i].hide();
            }
        }
    }
    
    //go to PopulationWindow method
    public void toPopulationWindow(){
        mapOpen = false;
        hide(mapObjects);
        
        show(pwObjects);
        populationOpen = true;
        
        //show the text
        for(int i = 0; i < 6; i++){
            popTxt[i] = new Text(continents[i], 80, 190 + 85 * i, canvas);
            popTxt[i].setFont(new Font("", Font.BOLD, 18));
            popTxt[i + 6] = new Text(pop.getWorldPopulation()[i][0], 
                    400, 190 + 85 * i, canvas);
            popTxt[i + 6].setFont(new Font("", Font.BOLD, 18));
            popTxt[i + 12] = new Text(pop.getWorldPopulation()[i][1], 
                    700, 190+85*i, canvas);
            popTxt[i + 12].setFont(new Font("", Font.BOLD, 18));
            popTxt[i + 18] = new Text(pop.getWorldPopulation()[i][2], 
                    1000, 190+85*i, canvas);
            popTxt[i + 18].setFont(new Font("", Font.BOLD, 18));
            popTxt[i + 18].setColor(Color.white);
        }
    }
    
    //go to VirusUpgradeWindow method
    public void toVirusUpgrade() {
        mapOpen = false;
        hide(mapObjects);
        
        virusUpgradeOpen = true;
        show(vuObjects);
        
        dna.sendToFront();
        dnaPointsTxt.sendToFront();
        dnaPic.sendToFront();
    }
    
    //go to EndScreen method 
    //method should have an argument that gives whether the user won or not
    public void toEndScreen(boolean win){
        endScreenOpen = true;
        
        mapOpen = false;
        virusUpgradeOpen = false;
        populationOpen = false;
        
        //create the endscreen
        EndScreen eS = new EndScreen(this, win, canvas);
        exitPW.show();
        exitPW.sendToFront();
        
        //stop the threads
        cure.suspendDate();
        date.suspendDate();
    }
    
    // Override onMouseRelease method
    public void onMouseRelease(Location p){
        if (startScreenOpen){
            if(continueButton.contains(p)){
                virus.setName(tf.getText());
                toTypeScreen();
        
            }
        }
        
        else if (typeScreenOpen) {
            if(infectious.contains(p)){
                infectiousChosen();
                virus.setLevels(2, 0, 0);
                virusUpgrade.setTRect(2);
            }
            else if(lethal.contains(p)){
                lethalChosen();
                virus.setLevels(1, 1, 0);
                virusUpgrade.setTRect(1);
                virusUpgrade.setLRect(1);
            }
            else if(resistant.contains(p)){
                resistChosen();
                virus.setLevels(1, 0, 1);
                virusUpgrade.setTRect(1);
                virusUpgrade.setRRect(1);
                cure.addCureSleep();
            }
            if(infectChosen || lethalChosen || resistChosen){
                if(enter.contains(p)){
                    toMap();
                }
            }
        }
        
        else if (mapOpen){
            if(exit.contains(p)){
                hide(sInfo);
                firstClick = true;
            }
            
            if (!chosen){
                for(int i = 0; i < 6; i++){
                    if(virusImage[i].contains(p) && firstClick){
                        virus.setContinent(continents[i]);
                        firstInfected = i;
                        originate(i);
                    }
                }
            }
            
            if(population.contains(p) && firstClick && !cInfoShown){
                //toWorldPopulation
                toPopulationWindow();
                date.suspendDate();
                if(cureStart){
                    cure.suspendDate();
                }
            }    
            else if(update.contains(p) && chosen && !cInfoShown){
                toVirusUpgrade();
                date.suspendDate();
                if(cureStart){
                    cure.suspendDate();
                }
                //toVirusUpgrade
            }
            else if(chosen && date.suspended() && play.
                    contains(p) && !cInfoShown){
                date.resumeDate();
                if(cureStart){
                    cure.resumeDate();
                }
                playRect.setColor(Color.CYAN);
                pauseRect.setColor(Color.white);
            }
            else if(chosen && !date.suspended() && pause.contains(p) 
                    && !cInfoShown){
                date.suspendDate();
                if(cureStart){
                    cure.suspendDate();
                }
                pauseRect.setColor(Color.CYAN);
                playRect.setColor(Color.white);
            }
            else if(cureStart && exit2.contains(p)){
                hide(cInfo);
                cInfoShown = false;
                cInfo.clear();
                date.resumeDate();
                map.getCureTxt().setText(" Research Progress: 0%");
                map.getCureTxt().setFont(new Font("", Font.BOLD, 14));
                show(cObjects);
                new Thread(cure).start();
            }


        }
        
        else if (populationOpen) {
            if (exitPW.contains(p)){
                toMap();
            }
        }
        
        else if (virusUpgradeOpen) {
            if(exitVU.contains(p)){
                toMap();
                price.hide();
            }
            for(int i = 0; i < 12; i++){
                if(upgradeButton[i].contains(p) || allUpgrades[i].contains(p)){
                    for(int j = 0; j < 12; j++){
                        allUpgrades[j].setColor(Color.WHITE);
                    }
                    allUpgrades[i].setColor(Color.YELLOW);
                    chosenUpgrade = true;
                    chosenButton = i;
                    for(int j = 0; j < 12; j++){
                        virusUpgrade.getButtonInfo1(j).sendToBack();
                        virusUpgrade.getButtonInfo2(j).sendToBack();
                    }
                    virusUpgrade.getButtonInfo1(i).sendToFront();
                    virusUpgrade.getButtonInfo2(i).sendToFront();
                    price.setText("This upgrade costs " + (vLevel[i] + 3) + 
                            " DNA points");
                    price.show();
                }
            }
            if(chosenUpgrade && get.contains(p)){
                for (int i = 0; i < allUpgrades.length; i ++){
                    allUpgrades[i].setColor(Color.WHITE);
                }    
                if(get(chosenButton)){
                    virusLevel[chosenButton].setText(++vLevel[chosenButton]);
                    price.hide();
                }
            }
        } 
        
        else if (endScreenOpen){
            if (exitPW.contains(p)){
                System.exit(0);
            }
        }
    }
    
    //methods to change the graphics based on the virus type the user chooses
    public void infectiousChosen(){
        infectChosen = true;
        infectious.setSize(180, 180);
        infectious.moveTo(130, 240);
        lethal.setSize(120, 120);
        lethal.moveTo(380, 270);
        resistant.setSize(120, 120);
        resistant.moveTo(600, 270);
        virusInfo[0].show();
        virusInfo[1].show();
        virusInfo[2].show(); 
        virusInfo[3].hide();
        virusInfo[4].hide();
        virusInfo[5].hide();

    }
    
    public void resistChosen(){
        resistChosen = true;
        resistant.setSize(180, 180);
        resistant.moveTo(570, 240);
        lethal.setSize(120, 120);
        lethal.moveTo(380,270);
        infectious.setSize(120, 120);
        infectious.moveTo(160, 270);
        virusInfo[0].show();
        virusInfo[1].show();
        virusInfo[4].show();
        virusInfo[5].show();
        virusInfo[2].hide();
        virusInfo[3].hide();

    }
    
    public void lethalChosen(){
        lethalChosen = true;
        lethal.setSize(180, 180);
        lethal.moveTo(350,240);
        infectious.setSize(120, 120);
        infectious.moveTo(160, 270);
        resistant.setSize(120, 120);
        resistant.moveTo(600, 270);
        virusInfo[0].show();
        virusInfo[1].show();
        virusInfo[3].show(); 
        virusInfo[2].hide();
        virusInfo[4].hide();
        virusInfo[5].hide();

    }
    
    // An originate method for the virus
    public void originate(int index){
        chosen = true;
        
        for(int i = 0; i < 6; i++){
            virusImage[i].hide();
        }
        
        //set the color of the play rectangle
        playRect.setColor(Color.CYAN);
        
        //infect the first person
        pop.originate(index);
        
        //set the DNA points
        dPoints = 5;
        setDNAPoints(dPoints);
        dateTxt.setText((date.calendar.get(Calendar.MONTH) + 1 ) + 
                    " / " + date.calendar.get(Calendar.DATE) + " / " + 
                    date.calendar.get(Calendar.YEAR));
        
        // start the thread of the calendar
        date.start();
        timerPlay = true;
        
        //show the percent dead on each continent
        for (int i = 0; i < percentDead.length; i++){
            percentDead[i].show();
        }
    }
    
    // Override onMouseMove method
    public void onMouseMove(Location p){
        if (startScreenOpen) {
            if(continueButton.contains(p) && tf != null){
                continueButton.setColor(Color.YELLOW);
            }
            else{
                continueButton.setColor(Color.WHITE);
            }
        }
        
        if (typeScreenOpen) {
            if(infectChosen || lethalChosen || resistChosen){
                if(enter.contains(p)){
                    enter.setColor(Color.YELLOW);
                }
                else{
                    enter.setColor(Color.WHITE);
                }
            }
        }
        
        if (mapOpen){
            if(update.contains(p) && chosen && !cInfoShown){
                update.setColor(Color.YELLOW);
            }
            else{
               update.setColor(Color.WHITE);
            }

            if(population.contains(p) && firstClick && !cInfoShown){
                population.setColor(Color.YELLOW);
            }
            else{
               population.setColor(Color.WHITE);
            }
        }
    }
    
    //show and hide methods
    public void remove(Text[] array){
        for (int i = 0; i < array.length; i ++){
            array[i].removeFromCanvas();
        }
    }
    
    //show and hide methods
    public void hide(ArrayList<DrawableInterface> list){
        for (int i = 0; i < list.size(); i ++) {
           
            list.get(i).hide();
            
        }
    }
    
    public void show(ArrayList<DrawableInterface> list){
        for (int i = 0; i < list.size(); i ++) {
            
            list.get(i).show();
                        
        }
    }
    
    public boolean get(int i){
        if (dPoints >= vLevel[i] + 3){
            dPoints -= vLevel[i] + 3;
            setDNAPoints(dPoints);
            if (i < 4){
                virus.increaseSL(1);
                virusUpgrade.setTRect(virus.getSpreadLevel());
            }
            else if (i < 8){
                virus.increaseKL(1);
                virusUpgrade.setLRect(virus.getKillLevel());
            }
            else {
                virus.increaseRL(1);
                virusUpgrade.setRRect(virus.getResistLevel());
                cure.addCureSleep();
            }
            return true;
        }
        return false;
    }
    
    public void setDNAPoints(int points){
        dnaPointsTxt.setText("DNA POINTS: " + points);

        dPoints = points;
        
    }
    
    public void checkGameOver(){
        int deadContinent = 0;
        
        for (int i = 0; i < continents.length; i ++){
            //check if everyone is dead
            if (pop.getWorldPopulation()[i][0].equals(new BigInteger("0")) &&
                    pop.getWorldPopulation()[i][1].equals(new BigInteger("0"))){
                deadContinent += 1;
            }
        }
        
        if (deadContinent == 6){
            toEndScreen(true);
        }
        else if (cure.getPercent() >= 100){
            toEndScreen(false);
        }
    }
    
    public void checkCure(){
        for(int i = 0; i < 6; i++){
            if(pop.getWorldPopulation()[i][1].doubleValue() > initialPop[i].
                    doubleValue() / 1000 || pop.getWorldPopulation()[i][2].
                            doubleValue() > initialPop[i].doubleValue() / 5000){
                show(cInfo);
                cInfoShown = true;
                date.suspendDate();
                map.getCureTxt().setText("Virus detected!");
                cureStart = true;
            }   
        }
    }
    
    public int pDead(int i){
        double pDead = (pop.getWorldPopulation()[i][2].doubleValue()/
                (initialPop[i]).doubleValue()) * 100;
        return (int)pDead;
    }
    
    public void updatePercentText(){
        for(int i = 0; i < 6; i++){
            percentDead[i].setText(pDead(i) + "%");
        }
    }
    
     public void checkMigrate(){
        for(int i = 0; i < 6; i++){
            if(pop.getWorldPopulation()[i][1].compareTo(new BigInteger("0")) 
                    == 1 && i != firstInfected && !checkMigrate[i]){
                for(int j = 0; j < 5; j++){
                    virusImage[i].move(0, 6);
                    Twinkle twinkle = new Twinkle(virusImage[i]);
                    twinkle.start();
                    checkMigrate[i] = true;
                }
                dPoints += 5;
            }
        }
    }
    public static void main(String[] args) {
        Infection test = new Infection();
    }

}
