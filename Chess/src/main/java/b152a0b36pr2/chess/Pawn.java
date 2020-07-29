/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.util.ArrayList;

/**
 *
 * @author uzivatel
 */
public class Pawn extends Piece {
/**
     * 
     * @param x the X coordinate of this piece's position
     * @param y the Y coordinate of this piece's position
     * @param color color of the piece
     
     */
    public Pawn(int x, int y, PlayerType color) {
        super(x, y, color, Names.PAWN);
        
    }


    @Override
    public ArrayList<Move> possibleMoves(Board board) {
        ArrayList<Move> tmp = new ArrayList<>();
        switch (super.color) {
            case WHITE: {
                checkField(board, PlayerType.BLACK, tmp);
                break;
            }

            case BLACK: {
                checkField(board, PlayerType.WHITE, tmp);
                break;
            }
        }
        return tmp;
    }

    private void checkField(Board board, PlayerType p, ArrayList tmp) {
        int i = 0;
        int start = 6;
        switch(p){
            case BLACK:{
                i = 1;
                break;
            }
            case WHITE:{
                i = -1;
                start = 1;
                break;
            }
        }
        
        if (checkMove(super.getX() - 1*i, super.getY()) && !(board.getField(super.getX() - 1*i, super.getY()).isOccupied())) {
            tmp.add(new Move(this, new Position(super.getX() - 1*i, super.getY())));

        }

        if (checkMove(super.getX() - 2*i, super.getY()) && !(board.getField(super.getX() - 2*i, super.getY()).isOccupied()) && super.getX() == start) {
            tmp.add(new Move(this, new Position(super.getX() - 2*i, super.getY())));
        }

        if (checkMove(super.getX() - 1*i, super.getY() - 1) && (board.getField(super.getX() - 1*i, super.getY() - 1).isOccupied()) && board.getField(super.getX() - 1*i, super.getY() - 1).getPiece().color == p) {
            tmp.add(new Move(this, new Position(super.getX() - 1*i, super.getY() - 1)));
        }

        if (checkMove(super.getX() - 1*i, super.getY() + 1) && (board.getField(super.getX() - 1*i, super.getY() + 1).isOccupied()) && board.getField(super.getX() - 1*i, super.getY() + 1).getPiece().color == p) {
            tmp.add(new Move(this, new Position(super.getX() - 1*i, super.getY() + 1)));
        }
    }
}
