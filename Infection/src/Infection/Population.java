package Infection;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.Random;

public class Population{
    
    // Instance Variables
    //0euro, 1sa, 2na, 3oce, 4afr, 5asia, 
    private static final BigInteger[] pop = {new BigInteger("354076086"), 
    new BigInteger("300863043"), new BigInteger("739115290"), 
    new BigInteger("1246504865"), new BigInteger("4467213675"),
    new BigInteger("40467040")};
    private Virus v;
    private BigInteger[][] worldPopulation;
    private static final String[] continents = {"South America", 
        "North America", "Europe", "Africa", "Asia", "Oceania"};
    
    // Constructor
    public Population(){
        //initialize the 2D array. The rows are for each continent and the
        //3 columns show the healthy, infected, and dead people
        worldPopulation = new BigInteger[6][3];
        for (int i = 0; i < worldPopulation.length; i++){
            for (int j = 0; j < worldPopulation[i].length; j++){
                if (j == 0) {
                    worldPopulation[i][j]= pop[i];
                }
                else {
                    worldPopulation[i][j] = new BigInteger("0");
                }
            }
        }
    }
    
    public BigInteger[][] getWorldPopulation(){
        return worldPopulation;
    }
    
    //infect the first person
    public void originate(int index){
        //make sure the healthy population is not 0
        if (!worldPopulation[index][0].equals(BigInteger.ZERO)) {
            worldPopulation[index][0] = worldPopulation[index][0].
                    subtract(new BigInteger("1"));
            worldPopulation[index][1] = worldPopulation[index][1].
                    add(new BigInteger("1"));
        }
    }
    
    public void spread(Virus virus){
        for (int i = 0; i < continents.length; i ++){
            BigDecimal infected = new BigDecimal(worldPopulation[i][1].
                    doubleValue() * virus.spreadRate());
            //make sure there are enough people to infect
            if (worldPopulation[i][0].subtract(infected.toBigInteger()).
                    compareTo(BigInteger.ZERO) > 0) {
                worldPopulation[i][0] = worldPopulation[i][0].subtract(
                    infected.toBigInteger());
                worldPopulation[i][1] = worldPopulation[i][1].add(
                    infected.toBigInteger());
            }
            //else infect everyone left
            else {
                worldPopulation[i][1] = worldPopulation[i][1].add(
                    worldPopulation[i][0]);
                worldPopulation[i][0] = BigInteger.ZERO;
            }
        }
    }
    
    public void kill(Virus virus){
        for (int i = 0; i < continents.length; i ++){
            BigDecimal dead = new BigDecimal(worldPopulation[i][1].doubleValue() * 
                    virus.killRate());
            //if the infected population is less than 10 and there are no 
            //healthy people left, kill the rest of the population
            if (worldPopulation[i][1].compareTo(BigInteger.TEN) < 0 && 
                    worldPopulation[i][0].equals(BigInteger.ZERO)){
                worldPopulation[i][2] = worldPopulation[i][2].add(
                    worldPopulation[i][1]);
                worldPopulation[i][1] = BigInteger.ZERO;
            }
            else if (worldPopulation[i][1].subtract(dead.toBigInteger()).
                    compareTo(BigInteger.ONE) > 0){
                worldPopulation[i][1] = worldPopulation[i][1].subtract(
                    dead.toBigInteger());
                worldPopulation[i][2] = worldPopulation[i][2].add(
                    dead.toBigInteger());
            }
            //if there is nobody healthy left, kill the rest of the infected
            else if (worldPopulation[i][0].equals(BigInteger.ZERO)){
                worldPopulation[i][2] = worldPopulation[i][2].add(
                    worldPopulation[i][1]);
                worldPopulation[i][1] = BigInteger.ZERO;
            }
            //else leave one infected person left
            else if (!worldPopulation[i][1].equals(BigInteger.ZERO)){
                worldPopulation[i][2] = worldPopulation[i][2].add(
                    worldPopulation[i][1]).subtract(BigInteger.ONE);
                worldPopulation[i][1] = BigInteger.ONE;
            }
        }
    }
    
    public void migrate(){
        for (int i = 0; i < continents.length; i++){
            Random rand = new Random();

            //test if the virus has spread to the majority of the population
            if (worldPopulation[i][1].add(worldPopulation[i][2]).compareTo(
                    worldPopulation[i][0]) >= 0) {
                //randomly migrate
                if (rand.nextInt(5) == 0){
                    int randomIndex = rand.nextInt(6);
                    //check if the disease has already migrated 
                    if (worldPopulation[randomIndex][0].equals(pop[randomIndex])){
                        originate(randomIndex);
                    }
                }
            }
        }
    }
}


