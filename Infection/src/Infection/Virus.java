package Infection;

public class Virus {
    
    // Instance Variables
    public String origin;
    String name;
    private int DNA;
    public int spreadLevel, killLevel, resistLevel;
    
    // Constructor
    public Virus(){
        name = "";
        origin = "";
        this.DNA = 1;
        spreadLevel = 0;
        killLevel = 0;
        resistLevel = 0;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getContinent(){
        return origin;
    }
    
    public void setContinent(String origin){
        this.origin = origin;
    }
    
    public void setLevels(int sl, int kl, int rl){
        spreadLevel = sl;
        killLevel = kl;
        resistLevel = rl;
    }
    
    public void increaseSL(int increase){
        spreadLevel += increase;
    }
    
    public void increaseKL(int increase){
        killLevel += increase;
    }
    
    public void increaseRL(int increase){
        resistLevel += increase;
    }
    
    public int getSpreadLevel(){
        return spreadLevel;
    } 
    
    public int getKillLevel(){
        return killLevel;
    }
    
    public int getResistLevel(){
        return resistLevel;
    }
    // A spread method
    public double spreadRate(){
        return Math.exp(0.0575 * spreadLevel);
    }
    
    // A kill method
    public double killRate(){
        return killLevel/10.0; 
    }   
}