/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author uzivatel
 */
public class Knight extends Piece{

    private int[] list = {-2,-1,1,2};
    
    
    /**
     * 
     * @param x the X coordinate of this piece's position
     * @param y the Y coordinate of this piece's position
     * @param color color of the piece
     
     */
    public Knight(int x, int y, PlayerType color) {
        super(x, y, color, Names.KNIGHT);
        
    }

    @Override
    public ArrayList<Move> possibleMoves(Board board) {
        ArrayList<Move> tmp = new ArrayList<>();
        PlayerType enemy = null;
        switch(super.getColor()){
            case WHITE: enemy = PlayerType.BLACK;break;
            case BLACK: enemy = PlayerType.WHITE;break;    
        }
        
        for (int i : list){
            for (int j: list){
                if (abs(i) != abs(j)){
                    if(checkMove(super.getX()+i,super.getY()+j)){
                        if (!board.getField(super.getX()+i, super.getY()+j).isOccupied()){
                            tmp.add(new Move(this, new Position(super.getX() + i, super.getY() + j)));
                        }else if(board.getField(super.getX()+i,super.getY()+ j).getPiece().getColor() == enemy){
                            tmp.add(new Move(this, new Position(super.getX() + i, super.getY() + j)));
                        }
                    }
                }            
            }
        }
        
        
        return tmp;
    }
    
}
