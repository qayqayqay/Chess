/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author uzivatel
 */
public interface Player {
    /**
     * If the player is computer controled returns desired move
     * @return move the player wants to perform
     */
    public Move performMove();
    
    /**
     * This method finds out if the player is in check or not
     * @param enemy enemy player
     * @param board the current board state
     * @return returns 0 if player is not in check, 1 if player is in check, 2 if there is check mate
     */
    public int isChecked(Player enemy,Board board) ;
   
    
   /**
    * 
    * @return return ArrayList of players pieces
    */ 
    public ArrayList<Piece> getPieces();
    
    /**
     * 
     * @return return the color of players pieces (PlayerType) 
     */
    public PlayerType getColor();
    /**
     * 
     * @return returns true if player is computer controlled 
     */
    public boolean AI();
    /**
     * 
     * @param board current boardstate
     * @return returns true if there is a possible move for this player, false otherwise
     */
    public boolean possibleToMove(Board board);
}
