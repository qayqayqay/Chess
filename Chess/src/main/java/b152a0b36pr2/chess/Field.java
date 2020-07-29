/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

/**
 * Manages a single field of the chess board
 * @author uzivatel
 */
public class Field {
   private int x;
   private int y;
   private Piece piece;
   private boolean occupied = false;
   /**
    * Created a field with coordiantes [x,y]
    * @param x X coordinated of created field
    * @param y Y coordinate of created field
    */
    public Field(int x, int y){
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    
    /**
     * Adds a chess piece to this field
     * @param piece chess piece to be added
     */
    public void addPiece(Piece piece){
        piece.setX(this.x);
        piece.setY(this.y);
        
        this.piece = piece;
        this.occupied = true;
    }
    /**
     * removes the piece from this field(doesnt change the position of given piece)
     */
    public void clearField(){
        this.piece = null;
        this.occupied = false;
    }
    /**
     * 
     * @return returns the X coordinate of this field
     */
    public int getX(){
        return x;
    }
    /**
     * 
     * @return returns the Y coordinate of this field
     */
    public int getY(){
        return y;
    }
    
    /**
     * 
     * @return true if there is a piece on this field, false otherwise
     */
    public boolean isOccupied(){
        return occupied;
    }
    /**
     * 
     * @return the piece from this field 
     */
    public Piece getPiece(){
        return this.piece;
    }
}
