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
public class King extends Piece {

    
    private boolean elegibleForCastling = true;

    /**
     *
     * @param x the X coordinate of this piece's position
     * @param y the Y coordinate of this piece's position
     * @param color color of the piece
     *
     */
    public King(int x, int y, PlayerType color) {
        super(x, y, color, Names.KING);

    }

    private boolean checkField(Board board, PlayerType p, int x, int y) {
        return (!board.getField(super.getX() + x, super.getY() + y).isOccupied() || board.getField(super.getX() + x, super.getY() + y).getPiece().color == p);
    }

    private Boolean validKingMove(Board board, int x, int y) {
        Boolean res = false;
        if (checkMove(super.getX() + x, super.getY() + y)) {
            switch (color) {
                case WHITE: {

                    res = checkField(board, PlayerType.BLACK, x, y);
                    break;
                }
                case BLACK: {

                    res = checkField(board, PlayerType.WHITE, x, y);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public ArrayList<Move> possibleMoves(Board board) {
        ArrayList<Move> tmp = new ArrayList<>();
        ArrayList<Piece> enemyPieces = enemy(board);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0) && validKingMove(board, i, j) && !reachableForEnemy(board, enemyPieces, super.getX() + i, super.getY() + j)) {
                    tmp.add(new Move(this, new Position(super.getX() + i, super.getY() + j)));
                }
            }
        }
        if (checkLeftCastling(board)) {
            tmp.add(new Move(this, new Position(super.getX(), super.getY() - 2)));
        }
        if (checkRightCastling(board)) {
            tmp.add(new Move(this, new Position(super.getX(), super.getY() + 2)));
        }
        return tmp;

    }

    private boolean checkLeftCastling(Board board) {
        boolean res = false;
        int length = 4;

        if (elegibleForCastling) {
            for (int i = 1; i < length; i++) {
                if (board.getField(super.getX(), super.getY() - i).isOccupied()) {
                    return res;
                }
            }
            Field tmp = board.getField(super.getX(), super.getY() - length);
            if (tmp.isOccupied() && tmp.getPiece().getType() == Names.ROOK) {
                Rook t = (Rook) tmp.getPiece();
                if (t.castling()) {
                    res = true;
                }
            }
        }
        return res;
    }

    private ArrayList<Piece> enemy(Board board) {
        ArrayList<Piece> tmp = new ArrayList<>();
        switch (color) {
            case WHITE: {
                tmp = board.getPieces(PlayerType.BLACK);
                break;
            }
            case BLACK: {
                tmp = board.getPieces(PlayerType.WHITE);
            }
        }
        return tmp;
    }

    private boolean reachableForEnemy(Board board, ArrayList<Piece> enemy, int x, int y) {
        boolean res = false;
        for (Piece i : enemy) {
            if (i.getType() != Names.KING) {
                for (Move j : i.possibleMoves(board)) {
                    if (j.getXY().getX() == x && j.getXY().getY() == y) {
                        return true;
                    }
                }
            }
            if (i.getType() == Names.PAWN) {
               
                if (attackedByPawn(x, y, i.getX(), i.getY())) {
                    
                    return true;
                }

            }
        }
        return res;
    }

    private boolean checkRightCastling(Board board) {
        boolean res = false;
        int length = 3;

        if (elegibleForCastling) {
            for (int i = 1; i < length; i++) {
                if (board.getField(super.getX(), super.getY() + i).isOccupied()) {
                    return res;
                }
            }
            Field tmp = board.getField(super.getX(), super.getY() + length);
            if (tmp.isOccupied() && tmp.getPiece().getType() == Names.ROOK) {
                Rook t = (Rook) tmp.getPiece();
                if (t.castling()) {
                    res = true;
                }
            }
        }
        return res;
    }

    /**
     *
     * @return if piece hasnt move and can perform castling return true
     */
    public boolean castling() {
        return elegibleForCastling;
    }

    /**
     * registers that piece has moved and can no longer perform castling
     */
    public void hasMoved() {
        elegibleForCastling = false;
    }

    private boolean attackedByPawn(int x, int y, int x0, int y0) {
        boolean res = false;
        switch (color) {
            case BLACK: {
                if ((x0 - 1 == x && y0 - 1 == y) || (x0 - 1 == x && y0 + 1 == y)) {
                    res = true;
                }
                
                break;
            }

            case WHITE: {
                if ((x0 + 1 == x && y0 - 1 == y) || (x0 + 1 == x && y0 + 1 == y)) {
                    res = true;
                    
                }
                
                break;
            }
        }
        return res;
    }

}
