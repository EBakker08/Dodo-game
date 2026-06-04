import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
        while( ! borderAhead() ){
            if (canMove() == true) {    // As long as canMove() = true move while printing coordinates
                move(); // Move
            }
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
        turnLeft();     // Face upwards
        move();         // Move up
        turnRight();    // Turn facing right
        move();         // Move to the right
        move();         // Move to the right
        turnRight();    // Face down
        move();         // Move down
        turnLeft();     // Turn facing right
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
     * Dodo goes to an egg.
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
        walkToWorldEdge();
        turn180();
    }
    
    /**
     * Dodo walks to a nest climbing over fences.
     * 
     * <p> Initial: Dodo is somewhere in the world.
     * <p> Final: Dodo is at a nest and layed an egg while he has climbed over all fences in its path.
     */
    public void walkToWorldEdgeClimbingOverFenceAndLayEgg() {
        while (!onNest()) {
            move();
            if (fenceAhead() == true) {
                climbOverFence();
            }
            
            if (onNest() == true) {
                if (!onEgg()) {
                    layEgg();
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
            if (onGrain() == true) {
                pickUpGrain();
                System.out.println("X: " + super.getX() + " Y: " + super.getY());
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
        while (!borderAhead()) {
            move();
            if (onNest()) {
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
            if (eggAhead() || nestAhead()) {
                move ();
            } else {
                turnRight();
                if (!eggAhead()) {
                    turn180();
                }
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
        while (!onNest()) {
            turnRight();
            if (canMove()) {
                move();
            } else {
                turnLeft();
                while (!canMove()) {
                    turnLeft();
                }
                move();
            }
        }
        
        if (onNest()) {
            showCompliment("Congratulations! You found the nest.");
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
            turnRight();
        }
    }
    
    /**
     * Dodo faces the direction user tells it to look (0 = North, 1 = East, 2 = South and 3 = West)
     * 
     * <p> Initial: Dodo is facing any direction.
     * <p> Final: Dodo is facing the direction that the user told it to.
     */
    public void faceDirection(int newDirection) {
        if (newDirection >= 0 && newDirection <= 3) {
            while (getDirection() != newDirection) {
                turnRight();
            }
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
    public void goToLocation(int coordX, int coordY){
        int moveX = coordX - getX();
        int moveY = coordY - getY();
        
        if(moveX > 0){
           setDirection(0);
           turnRight();
           jump(moveX);
        }else{
           moveX = moveX *-1;
           setDirection(0);
           turnLeft();
           jump(moveX);
        }
        
        if(moveY < 0){
           moveY = moveY *-1;
           setDirection(0);
           jump(moveY);
        }else{
           setDirection(0);
           turn180();
           jump(moveY);
        }
        
        faceEast();
    }
    
    /**
     * Check if coordinate input is lower or equal to world width and height.
     * 
     * <p> Initial: Dodo is somewhere in world.
     * <p> Final: Coordinate input gets validated.
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
        if (onEgg()) {
            eggCounter++;
        }
        
        while (!borderAhead()) {
            if (onEgg()) {
                eggCounter++;
            }
            move();
        }
        
        turn180();
        goBackToStartOfRowAndFaceBack();
        
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
        
        while (moved < layEgg) {
            if (borderAhead()) {
                showError("Can't move!");
                moved = layEgg;
            }
            
            move();
            layEgg();
            moved++;
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
        
        for (int worldHeight = 0; worldHeight < getWorld().getHeight(); worldHeight++) {
            countedEggs = countedEggs + countEggsInRow();
            System.out.println(countedEggs);
            turnRight();
            if (!borderAhead()) {
                move();
                turnLeft();
            }
        }
        
        goToLocation(0, 0);
        faceDirection(1);
        
        showCompliment("Congratulations! You've collected " + countedEggs);
    }
}
