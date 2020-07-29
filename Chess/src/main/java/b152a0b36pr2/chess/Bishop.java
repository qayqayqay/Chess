/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uzivatel
 */
public class Bishop extends Piece {

    private final int[] list = {-1, 1};
    private static final Logger LOGGER = Logger.getLogger(Bishop.class.getName());
 /**
     * 
     * @param x the X coordinate of this piece's position
     * @param y the Y coordinate of this piece's position
     * @param color color of the piece
     
     */
    public Bishop(int x, int y, PlayerType color) {
        super(x, y, color, Names.BISHOP);
    }

    @Override
    public ArrayList<Move> possibleMoves(Board board) {
        ArrayList<Move> tmp = new ArrayList<>();
        PlayerType enemy = null;
        switch (super.getColor()) {
            case WHITE:
                enemy = PlayerType.BLACK;
                break;
            case BLACK:
                enemy = PlayerType.WHITE;
                break;
        }

        for (int i : list) {
            boolean stopped = false;
            int x = 1;
            int y = 1;

            while (!stopped) {

                if (checkMove(super.getX() + i * x, super.getY() + i * x)
                        && (!board.getField(super.getX() + i * x, super.getY() + i * x).isOccupied()
                        || board.getField(super.getX() + i * x, super.getY() + i * x).getPiece().getColor() == enemy)) {
                    tmp.add(new Move(this, new Position(super.getX() + i * x, super.getY() + i * x)));
                    if (board.getField(super.getX() + i * x, super.getY() + i * x).isOccupied()) {
                        if (board.getField(super.getX() + i * x, super.getY() + i * x).getPiece().getColor() == enemy) {
                            stopped = true;
                        }
                    }
                    x++;
                    if (x > 7) {
                        stopped = true;
                    }
                } else {
                    stopped = true;
                }
            }
            stopped = false;
            while (!stopped) {

                if (checkMove(super.getX() + i * y, super.getY() - i * y)
                        && (!board.getField(super.getX() + i * y, super.getY() - i * y).isOccupied()
                        || board.getField(super.getX() + i * y, super.getY() - i * y).getPiece().getColor() == enemy)) {
                    tmp.add(new Move(this, new Position(super.getX() + i * y, super.getY() - i * y)));

                   if (board.getField(super.getX() + i * y, super.getY() - i * y).isOccupied()){
                        if (board.getField(super.getX() + i * y, super.getY() - i * y).getPiece().getColor() == enemy) {
                            stopped = true;
                        }
                   }
                    y++;
                    if (y > 7) {
                        stopped = true;
                    }
                } else {
                    stopped = true;
                }
            }
            stopped = false;
        }
        return tmp;
    }

}
