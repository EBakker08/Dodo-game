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
     * 
     */
    public boolean grainAhead() {
        if (onGrain()) {
            turn180();      // Turn to left
            move();         // Move left
            turn180();      // Turn to right
            return true;    // Return true
        } else {
            turn180();
            move();
            turn180();
            return false;
        }
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
}
