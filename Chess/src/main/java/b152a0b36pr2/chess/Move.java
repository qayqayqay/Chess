/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

/**
 *Contains a specified piece and the position to which we want to move it
 * @author uzivatel
 */
public class Move {
   private final Piece piece;
   private final Position destination;
   /**
    * Construct the move
    * @param piece piece we want to move
    * @param dest desired position to which we want to move the piece
    */
   public Move(Piece piece,Position dest){
       this.piece = piece;
       this.destination = dest;
   }
   /**
    * 
    * @return position to which we want to move the piece 
    */
   public Position getXY(){
       return destination;
   }
   /**
    * 
    * @return return the type of the piece
    */
   public Names getType(){
       return piece.getType();
   }
   
    
    
}
