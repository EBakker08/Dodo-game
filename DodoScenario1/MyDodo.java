import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }
    
    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove()) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final:   Same as initial situation.
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead()){    // If border OR fence is ahead then Dodo can't move
            return false;
        } else {    // Otherwise Dodo can move
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial: Dodo is somewhere in the world. 
     * <p> Final: Dodo has moved number of cells unless there is something in its path.
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
        
        if (distance <= 0) {    // If the distance Dodo wants to travel is 0 or lower then
            // Do nothing
        } else {
            System.out.println(nrStepsTaken + " steps made.");  // Otherwise print the steps that will be made
        }
    }
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East. Coordinates of each cell printed in the console.
     */
    public void walkToWorldEdge( ){
        while(!borderAhead()) { // While no fence or border ahead, move forward.
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg. (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *              false if Dodo can't lay an egg (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
             return false;
        }else{
            return true;
        }
    }
    
    /**
     * Turn Dodo around.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo has turned to face the other side of which it was facing.
     */
    
    public void turn180() {
        turnRight();    // Turn 90 degrees
        turnRight();    // Turn 90 degrees
                        // 90 + 90 = 180 so turned 180 degrees
    }
    
    /**
     * Dodo climbs over fence that is obscuring its path.
     * 
     * <p> Initial: Dodo is somewhere in the world with a fence infront of it.
     * <p> Final: Dodo climbs over the fence.
     */
    
    public void climbOverFence() {
        if (fenceAhead()) {
            turnLeft();     // Face upwards
            move();         // Move up
            turnRight();    // Turn facing right
            move();         // Move to the right
            move();         // Move to the right
            turnRight();    // Face down
            move();         // Move down
            turnLeft();     // Turn facing right
        } else {
            showError("No fence to climb!");    // If there is no fence, show this error
        }
    }
    
    /**
     * Dodo checks if there is a grain ahead of him.
     * 
     * <p> Initial: Dodo is behind a cell with a grain
     * <p> Final: Dodo is one cell behind the grain and says that there is a grain infront of it.
     */
    public boolean grainAhead() {
        move();                         // Move foward once.
        boolean onGrain = onGrain();    // Check if Dodo is standing on a grain.
        stepOneCellBackwards();         // Step backwards to original cell.
        return onGrain;                 // Return the result.
    }
    
    /**
     * Dodo goes to an egg on the same line as the dodo.
     * 
     * <p> Initial: Dodo is somewhere in the world with an egg on its path.
     * <p> Final: Dodo is on the closest egg.
     */
    public void goToEgg() {
        while (!onEgg()) {
            move();
        }
    }
    
    /**
     * Dodo walks to end of map and faces the other way.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo is on the last cell in the world facing the other way.
     */
    public void goBackToStartOfRowAndFaceBack() {
        turn180();  // Turn back the other way
        walkToWorldEdge();  // Go back to the start of the row
        turn180();  // Turn back to the original direction
    }
    
    /**
     * Dodo walks to a nest climbing over fences.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo is at a nest and layed an egg while he has climbed over all fences in its path.
     */
    public void walkToWorldEdgeClimbingOverFenceAndLayEgg() {
        while (!onNest()) { // While not on egg do this:
            move();
            if (fenceAhead() == true) { // If a fence is ahead...
                climbOverFence();   // ...climb over it
            }
            
            if (onNest() == true) { // When dodo is on nest...
                if (!onEgg()) {
                    layEgg();   // ...lay egg in it
                }
            }
        }
    }
    
    /**
     * Dodo walks to end of world while picking up grains and printing those coordinates.
     * 
     * <p> Initial: Dodo is somewhere in the world with grains in it's path.
     * <p> Final: Dodo is at end of world and has picked up every grain and printed the coordinates of those grains.
     */
    public void pickUpGrainsAndPrintCoordinates() {
        while (borderAhead() == false) {
            move();
            if (onGrain() == true) {    // If dodo is on a grain
                pickUpGrain();  // Pick grain up
                System.out.println("X: " + super.getX() + " Y: " + super.getY());   // Print coordinates of where dodo has picked up grain
            }
        }
    }
    
    /**
     * Dodo takes one step back.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo is one cell behind the original position.
     */
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }
    
    /**
     * Dodo walks to the end of the world and lays eggs in every empty nest.
     * 
     * <p> Initial: Dodo is somewhere in the world with nests in its path.
     * <p> Final: Dodo is at the edge of the world and has layed eggs in every empty nest.
     */
    public void walkToWorldEdgeAndLayEggs() {
        while (!borderAhead()) {    // While dodo is not at he end of the world
            move();
            if (onNest()) { // Lay an egg on every nest dodo encounters
                if (!onEgg()) {
                    layEgg();
                }
            }
        }
    }
    
    /**
     * Dodo walk around the fences to get to an egg.
     * 
     * <p> Initial: Dodo is somewhere in the world with fences making a path to an egg.
     * <p> Final: Dodo is on the egg after completing the loop around the fences.
     */
    public void walkAroundFencedArea() {
        while (!onEgg()) {
            move();
            turnRight();
            while (fenceAhead()) {
                turnLeft();
            }
        }
    }
    
    /**
     * Dodo walks over path of eggs to nest.
     * 
     * <p> Initial: Dodo is somewhere in world with trail of eggs infront of it.
     * <p> Final: Dodo is on nest after following trail of eggs.
     */
    public void eggTrailToNest() {
        move();
        while (!onNest()) {
            if (eggAhead() || nestAhead()) {    // If there is an egg or a nest infront of dodo
                move ();
            } else {    // Otherwise turn to the right
                turnRight();
                if (!eggAhead()) {  // If there is still no egg infront of dodo
                    turn180();
                }   // And repeat
            }
        }
    }
    
    /**
     * Dodo solves maze.
     * 
     * <p> Initial: Dodo is somewhere in a maze of fences with one nest at the end.
     * <p> Final: Dodo is on nest and has solved the maze.
     */
    public void walkToNestInMaze() {
        while (!onNest()) { // While dodo is not on a nest
            turnRight();
            if (canMove()) {    // If the dodo can move
                move();
            } else {
                turnLeft(); // Turn left if dodo cant move
                while (!canMove()) {    // while dodo cant move, turn left
                    turnLeft();
                }
                move();
            }
        }
        
        if (onNest()) {
            showCompliment("Congratulations! You found the nest."); // When dodo has found the nest, give compliment
        }
    }
    
    /**
     * Dodo goes to look to the east.
     * 
     * <p> Initial: Dodo looks any direction (North, East, South or West).
     * <p> Final: Dodo has turned to look at the east.
     */
    public void faceEast() {
        while (getDirection() != EAST) {
            turnRight();    // While dodo is not facing east, turn right.
        }
    }
    
    /**
     * Dodo faces the direction user tells it to look (0 = North, 1 = East, 2 = South and 3 = West)
     * 
     * <p> Initial: Dodo is facing any direction.
     * <p> Final: Dodo is facing the direction that the user told it to.
     */
    public void faceDirection(int newDirection) {
        if (newDirection >= 0 && newDirection <= 3) {   // If user input is between 0 and 3 do this
            while (getDirection() != newDirection) {
                turnRight();
            }
        } else {
            showError("Not an option!");    // If user input is bellow 0 or above 3 show this error.
        }
    }
    
    /**
     * The value of blue egg and golden egg are switched.
     * 
     * <p> Initial: Golden egg and blue egg have their original value.
     * <p> Final: The values of the two eggs are switched.
     */
    public void temporaryValueEgg() {
        BlueEgg blueEgg = new BlueEgg();
        GoldenEgg goldenEgg = new GoldenEgg();
        
        System.out.println(blueEgg.getValue());
        System.out.println(goldenEgg.getValue());
        
        goldenEgg.setValue(blueEgg.getValue());
        blueEgg.setValue(goldenEgg.getValue());
        
        System.out.println(blueEgg.getValue());
        System.out.println(goldenEgg.getValue());
    }
    
    /**
     * Dodo walks to coordinates that user has put in.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo has walked to coordinates that user put in and faces east.
     */
    public void goToLocation(int coordX, int coordY){   // give the coordinates you want to go to
        int moveX = coordX - getX();    // do the current coordinates of dodo minus the coordinates you want to go to
        int moveY = coordY - getY();
        
        if (moveX > 0){ // If moveX is higher then 0
           setDirection(0);
           turnRight();
           jump(moveX);
        } else {
           moveX = moveX *-1;
           setDirection(0);
           turnLeft();
           jump(moveX);
        }
        
        if (moveY < 0){ // do the same for the Y axis
           moveY = moveY *-1;
           setDirection(0);
           jump(moveY);
        } else {
           setDirection(0);
           turn180();
           jump(moveY);
        }
        
        faceEast();
    }
    
    /**
     * Check if coordinate input is lower or equal to world width and height.
     */
    public boolean validCoordinates(int x, int y) {
        if (x > getWorld().getWidth() || y > getWorld().getWidth()) {
            showError("Invalid Coordinates!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Dodo counts all the eggs in its path.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo has returned to its original position and counted all the eggs in its path.
     */
    public int countEggsInRow() {
        int eggCounter = 0;
        
        goBackToStartOfRowAndFaceBack();
        
        if (onEgg()) {
            eggCounter++;
        }
        
        while (!borderAhead()) {
            move();
            if (onEgg()) {
                eggCounter++;
            }
        }
        
        return eggCounter;
    }
    
    /**
     * Dodo lays a trail of eggs set by the user.
     * 
     * <p> Initial: Dodo is somewhere in world.
     * <p> Final: Dodo has walked the users amount and layed eggs.
     */
    public void layTrailOfEggs(int layEgg) {
        int moved = 0;
        
        while (moved < layEgg) {    // Do the following while moved is bellow inputed layEggs
            layEgg();
            moved++;
            if (borderAhead()) {    // If there is a border ahead then stop the loop
                break;
            }
            move();
        }
    }
    
    /**
     * Dodo counts all the eggs in the world.
     * 
     * <p> Initial: Dodo is at the top left of the world.
     * <p> Final: Dodo has counted all eggs in the world and has returned to his starting position facing east.
     */
    public void countAllEggsInWorld() {
        int countedEggs = 0;
        
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {    // Do the following for the whole lenght of the world
            countedEggs = countedEggs + countEggsInRow();   // Count all eggs in the current row
            System.out.println(countedEggs);
            turnRight();
            if (!borderAhead()) {   // Move down if posible and repeat
                move();
                turnLeft();
            }
        }
        
        goToLocation(0, 0);
        faceDirection(1);
        
        showCompliment("Congratulations! You've counted " + countedEggs);
    }
    
    /**
     * Dodo finds the row with the most eggs
     * 
     * <p> Initial: Dodo is at the top left of the world.
     * <p> Final: Dodo is back at the top left of the world and has counted all rows for eggs.
     */
    public void findRowWithMostEggs() {
        int onRow = 0;
        int highestEggsAmountRow = 0;
        int highestEggAmount = 0;
        
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {
            System.out.println("On row " + onRow + " is/are " + countEggsInRow() + " egg(s)");
            
            if (countEggsInRow() > highestEggAmount) {  // If the current counted row is higher then the row that had the most eggs, overwrite it
                highestEggAmount = highestEggAmount;
                highestEggsAmountRow = onRow;
            }
        
            turnRight();
            if (!borderAhead()) {   // Move down and do the same
                move();
                turnLeft();
            } else {
                turnLeft();
            }
            
            onRow++;
        }
        
        System.out.println("On row " + highestEggsAmountRow + " are the most eggs.");
        
        goToLocation(0, 0);
        faceDirection(1);
    }
    
    /**
     * Dodo lays a stairwell of eggs.
     * 
     * <p> Initial: Dodo is at the top left of the world.
     * <p> Final: Dodo has layed a stairwell of eggs.
     */
    public void makeStairOfEggs() {
        for (int worldWidth = 0; worldWidth < getWorld().getWidth(); worldWidth++) {
            if(canMove()) {
                layTrailOfEggs(worldWidth + 1); 
                goBackToStartOfRowAndFaceBack();
                turnRight();
                if (!borderAhead()) {
                    move();
                    turnLeft();
                } else {
                    turnLeft();
                }
            }
        }
        
        goToLocation(0, 0);
        faceDirection(1);
    }
    
    /**
     * Dodo has layed a second patern of eggs. (Multiplying every time times itself)
     * 
     * <p> Initial: Dodo is at the top left of the world
     * <p> Final: Dodo is back at the top left of the world and has layed a second patern of eggs.
     */
    public void makeStairOfEggsWithExtraSteps() {
        int stepDuplicate = 1;
        
        for (int worldWidth = 0; worldWidth < getWorld().getWidth(); worldWidth++) {
            if (canMove()) {
                layTrailOfEggs(stepDuplicate);
                goBackToStartOfRowAndFaceBack();
                turnRight();
                if (!borderAhead()) {
                    move();
                    turnLeft();
                } else {
                    turnLeft();
                }
                stepDuplicate = stepDuplicate * 2;
            }
        }
    
        goToLocation(0, 0);
        faceDirection(1);
    }
    
    /**
     * Dodo lays pyramid of eggs.
     * 
     * <p> Initial: Dodo is in the center of any row.
     * <p> Final: Dodo has layed a pyramid of eggs and has returned to the top left of the map.
     */
    public void makePyramidOfEggs() {  
        int eggsInRow = 1;
        int stepsMade = 0;
        int moved = 0;
    
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {
            layTrailOfEggs(eggsInRow);
            stepsMade = eggsInRow;
            turn180();
            
            while (moved != stepsMade) {
                move();
                moved++;
            }
            
            move();
            moved = 0;
            
            if (!canMove()) {
                break;
            }
    
            turnLeft();
            move();
            turnLeft();
    
            eggsInRow = eggsInRow + 2;
        }
        
        goToLocation(0, 0);
        faceDirection(1);
    }
    
    /**
     * Dodo counts the average amount of eggs in world
     * 
     * <p> Initial: Dodo is at the top left of the world
     * <p> Final: Dodo has counted all the eggs and given the average of the eggs.
     */
    public double averageOfEggsPerRow() {
        double averageOfEggs = 0;
        double totalOfEggs = 0;
        int rows = 0;
        
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {    // Count all the eggs in the world
            totalOfEggs = totalOfEggs + countEggsInRow();
            turnRight();
            if (canMove()) {
                move();
                turnLeft();
            } else {    // If you cant move then stop the loop
                break;
            }
        }
        
        averageOfEggs = totalOfEggs / getWorld().getHeight();   // Devide the total amount of eggs in the world by the height of the world.
        
        goToLocation(0, 0);
        faceDirection(1);
        
        return averageOfEggs;
    }
    
    /**
     * Dodo lays egg on broken row
     * 
     * <p> Initial: Dodo is on top left facing east.
     * <p> Final: Dodo has counted all the eggs in the world and has layed an egg on the location where an egg should be
     */
    public void makeEggsEven() {
        int errorLineX = 0;
        int errorLineY = 0;
        
        goToLocation(0, 0); // Go to starting location
        faceDirection(1);
        
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {    // Count all the eggs in the rows on the Y axis
            int eggs = countEggsInRow();
            
            turnRight();
            if (canMove()) {
                move();
                turnLeft();
            } else {
                faceDirection(1);
            }
            
            if (eggs % 2 == 1) {    // If the row equals 1 then save the row
                errorLineX = getY();
                System.out.println("Y: " + errorLineX);
            }
        }
        
        for (int worldWidth = 0; worldWidth < getWorld().getWidth(); worldWidth++) {    // Count all the eggs in the rows on the X axis
            faceDirection(0);
            
            int eggs = countEggsInRow();
            
            turnLeft();
            if (canMove()) {
                move();
                turnLeft();
            } else {
                faceDirection(1);
            }
            
            if (eggs % 2 == 1) {    // Do the same for the X axis
                errorLineY = getX();
                System.out.println("X: " + errorLineY);
            }
        }
        
        if (errorLineX != -1 && errorLineY != -1) { // Go to the location where the error line is and lay an egg
            goToLocation(errorLineX, errorLineY);
            layEgg();
        }
        
        goToLocation(0, 0); // Go back to starting location
        faceDirection(1);
    }
    
    /**
     * dodo will make the world even without using any derictional functions.
     * 
     * <p> Initial: Dodo is in the world.
     * <p> Final: Dodo has layed an egg on the location that it should be.
     */
    public void makeEggsEvenVersionAfterNoBrain(){
        walkToWorldEdge();
        turnLeft();
        walkToWorldEdge();
        turn180();
        
        int moved = 0;

        while(!borderAhead()){
            int eggs = countEggsInRow();
            
            goBackToStartOfRowAndFaceBack();        

            if(eggs % 2 == 1){
                walkToWorldEdge();
                turnLeft();
                walkToWorldEdge();
                turn180();
                
                while(!borderAhead()){
                    eggs = countEggsInRow();
                    goBackToStartOfRowAndFaceBack();

                    if(eggs % 2 == 1){
                        jump(moved);
                        layEgg();
                        break;
                    }
                    turnRight();
                    if(borderAhead()){
                        break;
                    }
                    move();
                    turnLeft();
                }
                break;
            }

            turnRight();
            
            if(borderAhead()){
                break;
            }
            
            move();
            moved++;
            turnLeft();
        }
    }

    /**
     * Places all the Egg objects in the world in a list.
     * 
     * @return List of Egg objects in the world
     */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }

    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }

    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        
        //the following is incorrect and is to be fixed in challenge 6.1c
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEggs( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
    
    /**
     * 10 surprise eggs get put on random places in the world.
     * 
     * <p> Initial: There is a world with 10 or more spaces.
     * <p> Final: The world is filled with 10 eggs.
     */
    public List<SurpriseEgg> makeListOfSurpriseEggs() {
        List<SurpriseEgg> eggs = SurpriseEgg.generateListOfSurpriseEggs(10, getWorld());
        return eggs;
    }

    public void printCoordinateOfEgg(Egg egg) {
        System.out.println("X: " + egg.getX() + " Y: " + egg.getY());
    }
    
    /**
     * 10 surprise eggs get put on random places in the world and the coordinates of these eggs are printed in the console.
     * 
     * <p> Initial: There is a world with 10 or more spaces.
     * <p> Final: The world is filled with 10 eggs on random positions and we know the coordinates.
     */
    public void makeListOfSurpriseEggsPrintingCoordinates() {
        makeListOfSurpriseEggs();
        
        for (Egg egg : getListOfEggsInWorld()) {
            printCoordinateOfEgg(egg);
        }
    }
    
    /**
     * We get the highest value of an egg.
     * 
     * <p> Initial: Eggs get spawned into the world.
     * <p> Final: All eggs are looked at and then one egg has the highest value.
     */
    public void mostValuableEgg() {
        int highestValueEgg = 0;
        int indexHighestEgg = -1;
        int highestEgg = -1;
        
        makeListOfSurpriseEggs();   // Print out a list of surprise eggs
        
        for (Egg egg : getListOfEggsInWorld()) {    // for each egg, do the following
            indexHighestEgg++;
            
            if (egg.getValue() > highestValueEgg) { // get the value of the egg and if its higher then the last saved then overwrite it
                highestValueEgg = egg.getValue();
                highestEgg = indexHighestEgg;
            }
        }
        
        System.out.println(highestEgg);
    }
    
    /**
     * All value's of eggs get counted up and devided by their amount to get the average value of an egg.
     * 
     * <p> Initial: An amount of eggs get spawned in the world.
     * <p> Final: All values of eggs have been added up and devided by their amount to get one average number.
     */
    public void averageValueOfEggs() {
        int totalValueOfEggs = 0;
        int averageValueOfEggs = 0;
        
        makeListOfSurpriseEggs();
        
        List<Egg> eggs = getListOfEggsInWorld();
        
        for (Egg egg : getListOfEggsInWorld()) {    // Add up the whole value of the list of eggs in the world
            totalValueOfEggs = totalValueOfEggs + egg.getValue();
        }
        
        averageValueOfEggs = totalValueOfEggs / eggs.size();    // Devide the value of all eggs combined by the size of the eggs list
        
        System.out.println(averageValueOfEggs);
    }
    
    /**
     * Dodo moves in a random direction for the amount of times that the user puts in.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Finals: Dodo has moved randomly for the amount of times that the user put in.
     */
    public void moveRandomly(int moves) {   // Give how many moves dodo can make
        int moved = moves;
        
        while (moved != 0) {    // Do the following while moved doesnt equal 0
            faceDirection(randomDirection());   // Face a random direction
            
            if (canMove()) {    // If dodo can move
                move();
                moved = moved - 1;
            } else {
                faceDirection(randomDirection());   // Otherwise pick another random direction
            }
        }
    }
    
    public void getScore(int score1, int score2) {
        Mauritius world = (Mauritius) getWorld();
        world.updateScore(score1, score2);
    }
    
    /**
     * Dodo finds closest egg and takes it.
     * 
     * <p> Initial:  Dodo is somewhere in the world with eggs.
     * <p> Final: Dodo has gone to the closest egg in the world to him and taken it.
     */
    public int searchClosestEggAndPickItUp() {
        List<Egg> eggs = getListOfEggsInWorld();
        
        Egg closestEgg = null;
        int smallestDistance = -1;
        int closestEggX = 0;
        int closestEggY = 0;
        
        for (Egg egg : eggs) {
            int aOrX = 0;   // A kwadraat is X
            int bOrY = 0;   // B kwadraat is y
            int c = 0;
            
            // Stelling van Pythagoras
            aOrX = egg.getX() - getX();
            bOrY = egg.getY() - getY();
            
            c = aOrX * aOrX + bOrY * bOrY;
            
            if (closestEgg == null || c < smallestDistance) {
                closestEgg = egg;
                smallestDistance = c;
            }
        }
        
        if (closestEgg != null) {
            int distanceX = closestEgg.getX() - getX();
            int distanceY = closestEgg.getY() - getY();
            
            int stepsTaken = distanceX + distanceY;   // dit is het aantal stappen dat goToLocation zet
            
            goToLocation(closestEgg.getX(), closestEgg.getY());
            pickUpEgg();
            return stepsTaken;
        } else {
            showError("There are no eggs left in the world.");
            return 0;
        }
    }
    
    public void eindopdrachtAlgorithm(int howManyMoves) {
        int moves = howManyMoves;
        int moved = 0;
        int eggScore = 0;
        
        do {
            Egg closestEgg = findClosestEgg();
            
            if (closestEgg != null) {   // If there is no closest egg
                int distanceX = closestEgg.getX() - getX();
                int distanceY = closestEgg.getY() - getY();
                
                if (distanceX > 0) {    // If egg is to the right, turn east
                    faceDirection(1);
                } else if (distanceX < 0) { // If egg is to the left, turn west
                    faceDirection(3);
                } else if (distanceY > 0) { // If egg is to the bottom, turn south
                    faceDirection(2);
                } else if (distanceY < 0) { // If egg is upwards, turn north
                    faceDirection(0);
                }
            }
            
            if (canMove()) {    // If dodo can move
                move();
                moved++;
                moves = moves - 1;
                
                if (onEgg()) {  // If dodo is on egg
                    Egg pickedUpEgg = pickUpEgg();  // Pick up the egg
                    eggScore = eggScore + pickedUpEgg.getValue();   // Depending on color of egg, add to score
                }
                
                getScore(moves, eggScore);  // Update scoreboard
            }
        } while (moves != 0 && getListOfEggsInWorld().size() > 0);  // Do all of this while dodo can legaly move and eggs in world arent 0
    }
    
    public Egg findClosestEgg() {
        List<Egg> eggs = getListOfEggsInWorld();    // Get all eggs in world
        
        Egg closestEgg = null;
        int smallestDistance = -1;
        
        for (Egg egg : eggs) {
            int aOrX = egg.getX() - getX(); // Distance from dodo on X axis
            int bOrY = egg.getY() - getY(); // Distance from dodo in Y axis
            
            int c = aOrX * aOrX + bOrY * bOrY;
            
            if (closestEgg == null || c < smallestDistance) {   // If egg is closer then last saved egg then save current egg
                closestEgg = egg;
                smallestDistance = c;
            }
        }
        
        return closestEgg;
    }
}
