/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the chess board
 *
 * @author uzivatel
 */
public class Board {

    private Field[][] fields = new Field[8][8];
    private PlayerType[] list = {PlayerType.WHITE, PlayerType.BLACK};
    private Pgn pgn;
    private int numberOfTurns = 2;
    private final char chars[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private final int numbers[] = {1, 2, 3, 4, 5, 6, 7, 8};
    private String s = "";
    
    /**
     * constructs empty board
     */
    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.fields[i][j] = new Field(i, j);
            }
        }
        
            
        
    }

    /**
     *
     * @param x X coordinate of desire field
     * @param y Y coordinate of desired field
     * @return returns a field of coordinates [x,y]
     */
    public Field getField(int x, int y) {
        return fields[x][y];
    }

    /**
     * Puts pieces in default chess position
     */
    public void setDefaultPieces() {
        int k = 0;
        for (int i = 0; i < 8; i++) {

            fields[6][i].addPiece(new Pawn(6, i, PlayerType.WHITE));

            fields[1][i].addPiece(new Pawn(1, i, PlayerType.BLACK));

        }
        for (PlayerType i : list) {

            switch (i) {

                case WHITE: {
                    k = 7;
                    break;
                }

                case BLACK: {
                    k = 0;
                    break;
                }
            }
            fields[k][0].addPiece(new Rook(k, 0, i));
            fields[k][7].addPiece(new Rook(k, 7, i));

            fields[k][1].addPiece(new Knight(k, 1, i));
            fields[k][6].addPiece(new Knight(k, 6, i));

            fields[k][2].addPiece(new Bishop(k, 2, i));
            fields[k][5].addPiece(new Bishop(k, 5, i));
            int m = 0;
            if (k == 1) {
                m = 1;
            }

            fields[k][3 + m].addPiece(new Queen(k, 3 + m, i));
            fields[k][4 - m].addPiece(new King(k, 4 - m, i));
        }

    }

    /**
     * Moves piece from position [xFrom,Yfrom] to [xTo,yTo]
     *
     * @param xFrom X coordinate of selected piece
     * @param yFrom Y coordinate of selected piece
     * @param xTo X coordinate of final destination
     * @param yTo Y coordinate of final destination
     */
    public void movePiece(int xFrom, int yFrom, int xTo, int yTo) {
        s = "";
        if(numberOfTurns != 2){
            s = s+" ";
        }
        movedRook(xFrom, yFrom);
        movedKing(xFrom, yFrom);

        if (numberOfTurns % 2 == 0) {
            if (numberOfTurns % 6 == 0) {
                s = s+"\n";
            }
            s = s + Integer.toString(numberOfTurns / 2) + ". ";

        }
        if (isCastling(xFrom, yFrom, xTo, yTo)) {
            King t = (King) fields[xFrom][yFrom].getPiece();
            t.hasMoved();
            if ((yTo - yFrom) > 0) {
                s = s+ "O-O";
            } else {
                s = s+ "O-O-O";
            }
            performCastling(xFrom, yFrom, xTo, yTo);
        } else {
            s = s + fields[xFrom][yFrom].getPiece().getType().getPgn();
            s = s + chars[yTo] + Integer.toString(numbers[7 - xTo]) + "";
        }
        if (fields[xTo][yTo].isOccupied()) {
            fields[xTo][yTo].getPiece().removeFromGame();
            pgn.addMove("x");
        }
        fields[xTo][yTo].addPiece(fields[xFrom][yFrom].getPiece());
        fields[xFrom][yFrom] = new Field(xFrom, yFrom);

        numberOfTurns++;
    }

    private boolean isCastling(int xFrom, int yFrom, int xTo, int yTo) {
        boolean res = false;
        if (fields[xFrom][yFrom].getPiece().getType() == Names.KING) {
            if (xFrom == xTo && abs(yTo - yFrom) == 2) {
                res = true;
            }
        }

        return res;
    }

    public ArrayList<Piece> getPieces(PlayerType type) {
        ArrayList<Piece> tmp = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fields[i][j].isOccupied() && fields[i][j].getPiece().getColor() == type) {
                    tmp.add(fields[i][j].getPiece());
                }
            }
        }
        return tmp;
    }

    private void performCastling(int xFrom, int yFrom, int xTo, int yTo) {
        switch (yTo - yFrom) {
            case 2: {
                Rook t = (Rook) fields[xTo][yTo + 1].getPiece();
                t.hasMoved();
                //movePiece(xTo, yTo + 1, xTo, yTo - 1);
                fields[xTo][yTo -1].addPiece(fields[xTo][yTo +1].getPiece());
                fields[xTo][yTo+1] = new Field(xTo, yTo+1);
                break;
            }
            case -2: {
                Rook t = (Rook) fields[xTo][yTo - 2].getPiece();
                t.hasMoved();
                //movePiece(xTo, yTo - 2, xTo, yTo + 1);
                fields[xTo][yTo+1].addPiece(fields[xTo][yTo-2].getPiece());
                fields[xTo][yTo-2] = new Field(xTo, yTo-2);
                break;
            }
        }
    }

    public Pgn getPgn() {
        return pgn;
    }

    private void movedKing(int xFrom, int yFrom) {
        if (fields[xFrom][yFrom].getPiece().getType() == Names.KING) {
            King k = (King) fields[xFrom][yFrom].getPiece();
            k.hasMoved();
        }
    }

    private void movedRook(int xFrom, int yFrom) {
        if (fields[xFrom][yFrom].getPiece().getType() == Names.ROOK) {
            Rook k = (Rook) fields[xFrom][yFrom].getPiece();
            k.hasMoved();
        }
    }

    void setPgn(String event, String site, String date,String white, String black) {
        pgn = new Pgn(event,site,date,white, black);
    }

    void writePgn() {
        pgn.addMove(s);
    }
}
