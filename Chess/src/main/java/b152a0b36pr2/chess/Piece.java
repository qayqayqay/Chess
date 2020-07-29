/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

//import java.awt.Font;

import java.util.ArrayList;



/**
 *
 * @author uzivatel
 */
public abstract class Piece {
    private int x;
    private int y;
    private boolean isInGame = true;
    protected PlayerType color;
    private final Names type;
   // static Font font = new Font("Sans-Serif", Font.PLAIN, 50);
    
    /**
     * 
     * @param x the X coordinate of this piece's position
     * @param y the Y coordinate of this piece's position
     * @param color color of the piece
     * @param type type of the piece (Pawn,Queen,...)
     */
    public Piece(int x , int y, PlayerType color,Names type){
        this.x = x;
        this.y = y;
        
        this.color = color;
        this.type = type;
    }
   /**
    * 
    * @return returns the type of this piece
    */
    public Names getType(){
        return type;
    }
   /**
    * 
    * @return returns the X coordinate of this piece 
    */
    public int getX(){
        return this.x;
    }
    /**
     * 
     * @return returns the Y coordinate of this piece 
     */
    public int getY(){
        return this.y;
    }
    /**
     * Sets the X coordinate of the piece to desired value
     * @param x desired value
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * Sets the Y coordinate of the piece to desired value
     * @param y desired value
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * Removes the piece from game, the piece is no longer visible 
     */
    public void removeFromGame(){
        this.isInGame = false;
    }
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    
    public static boolean checkMove(int x , int y){
        return 0<=x&& x<8 && 0<=y &&y<8;
    }
    
    
    /**
     * Return ArrayList with all the possible moves of this piece for specified board state
     * @param board the board state in which we want to move the piece
     * @return ArrayList contaning possible moves
     */
    public abstract ArrayList<Move> possibleMoves(Board board);
    
    
    
    
    public PlayerType getColor(){
        return color;
    }
    
    public void setAlive(){
        isInGame = true;
    }  
    
    public boolean isAlive(){
        return isInGame;
    }
}
