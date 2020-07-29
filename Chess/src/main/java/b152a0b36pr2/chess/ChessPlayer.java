/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import static b152a0b36pr2.chess.Piece.checkMove;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author uzivatel
 */
public class ChessPlayer implements Player {

    private ArrayList<Piece> pieces;
    public final PlayerType color;
    private Piece king;
    private final int[] list = {-1, 1};
    private Logger LOGGER = Logger.getLogger(ChessPlayer.class.getName());
    private final boolean AI = false;

    public ChessPlayer(PlayerType color, Board board) {

        this.color = color;
        switch (this.color) {  // Players get their default pieces
            case WHITE: {
                pieces = new ArrayList<>();
                for (int j = 6; j < 8; j++) {
                    for (int i = 0; i < 8; i++) {

                        pieces.add(board.getField(j, i).getPiece());

                    }
                }
                king = board.getField(7, 4).getPiece();

                break;
            }

            case BLACK: {
                pieces = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 2; j++) {

                        pieces.add(board.getField(j, i).getPiece());

                    }
                }

                king = board.getField(0, 4).getPiece();
                break;
            }

        }
    }

    @Override
    public Move performMove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int isChecked(Player enemy, Board board) {
        ArrayList<Field> tmp = new ArrayList<>();
        int res = 0;

        int checkType = 0;
        tmp = sortReachable(board.getField(king.getX(), king.getY()), findAttackers(board.getField(king.getX(), king.getY()), board), board);
        if (!tmp.isEmpty()) {
            res = 1;
        }

        checkType = checkingNumber(getNumbersOfPieces(tmp, board));
        if (checkType <= 1) {
            if (getNumbersOfPieces(tmp, board)[5] == 1) {
                checkType = -1;
            }
        }
        
        if (res == 1 && !moveKing(board)) {
            switch (checkType) {

                case -1: {//Attacked only by knight
                    if (!possibleMoveTo(tmp.get(0), board)) {
                        res = 2;

                    }
                    break;
                }

                case 1: {//Attacked by 1 piece other than knight
                    if (!possibleMoveTo(tmp.get(0), board) && !obstructView(tmp.get(0), board)) {
                        res = 2;
                    }
                    break;
                }

                default: {//Attacked by at least two
                    res = 2;
                    break;
                }
            }

        }
        return res;
    }

    private ArrayList<Field> findAttackers(Field where, Board board) {
        ArrayList<Field> tmp = new ArrayList<>();
        int[] listKnight = {-2, -1, 1, 2};
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (!(x == 0 && y == 0)) {
                    Field f = checkWay(where, x, y, board);
                    if (f != null) {
                        tmp.add(f);
                    }
                }
            }
        }

        for (int i : listKnight) {
            for (int j : listKnight) {
                if (abs(i) != abs(j)) {
                    if (checkMove(where.getX() + i, where.getY() + j)) {
                        if (board.getField(where.getX() + i, where.getY() + j).isOccupied()) {
                            if ((board.getField(where.getX() + i, where.getY() + j).getPiece().getColor() != color)
                                    && board.getField(where.getX() + i, where.getY() + j).getPiece().getType() == Names.KNIGHT) {
                                tmp.add(board.getField(where.getX() + i, where.getY() + j));
                            }
                        }
                    }
                }
            }
        }

        return tmp;
    }

    private ArrayList<Field> sortReachable(Field where, ArrayList<Field> f, Board board) {
        ArrayList<Field> tmp = new ArrayList<Field>();
        for (Field i : f) {
            boolean checks = false;
            for (Move j : board.getField(i.getX(), i.getY()).getPiece().possibleMoves(board)) {

                if ((j.getXY().getX() == where.getX() && j.getXY().getY() == where.getY())) {
                    checks = true;
                }
            }
            if (checks) {
                tmp.add(i);
            }
        }

        return tmp;
    }

    private int[] getNumbersOfPieces(ArrayList<Field> f, Board board) {
        int[] list = new int[6];
        for (Field i : f) {
            switch (board.getField(i.getX(), i.getY()).getPiece().getType()) {
                case KING:
                    list[0]++;
                    break;
                case QUEEN:
                    list[1]++;
                    break;
                case PAWN:
                    list[2]++;
                    break;

                case ROOK:
                    list[3]++;
                    break;
                case BISHOP:
                    list[4]++;
                    break;
                case KNIGHT:
                    list[5]++;
                    break;
            }
        }
        return list;
    }

    private int checkingNumber(int[] k) {
        int res = 0;

        for (int i = 0; i < k.length; i++) {
            res += k[i];
        }
        return res;
    }

    

    private Field checkWay(Field where, int x, int y, Board board) {
        int incX = x;
        int incY = y;
        boolean stopped = false;
        Field res = new Field(0, 0);
        while (!stopped) {
            if (checkMove(where.getX() + x, where.getY() + y)) {
                if (board.getField(where.getX() + x, where.getY() + y).isOccupied()) {
                    if (board.getField(where.getX() + x, where.getY() + y).getPiece().getColor() != color) {
                        res = board.getField(where.getX() + x, where.getY() + y);
                        stopped = true;
                    } else {
                        stopped = true;

                        return null;
                    }
                }
            } else {
                stopped = true;

                return null;
            }
            x = x + incX;
            y = y + incY;
        }

        return res;
    }

   
    

    @Override
    public ArrayList getPieces() {
        return pieces;
    }

    @Override
    public PlayerType getColor() {
        return color;
    }

    private boolean checkMove(int x, int y) {
        return ((0 <= x) && (x < 8) && (0 <= y) && (y < 8));
    }

    private boolean moveKing(Board board) {
        boolean res = false;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i == 0 && j == 0) && checkMove(king.getX() + i, king.getY() + j)
                        && (!board.getField(king.getX() + i, king.getY() + j).isOccupied()
                        || board.getField(king.getX() + i, king.getY() + j).getPiece().getColor() != color)) {
                    
                    if (sortReachable(board.getField(king.getX() + i, king.getY() + j),
                            findAttackers(board.getField(king.getX() + i, king.getY() + j), board), board).size() == 0) {
                        
                        return true;
                    }
                }
            }
        }
        
        return res;
    }

    private boolean possibleMoveTo(Field get, Board board) {
        boolean res = false;

        for (Piece i : pieces) {
            if (i.isAlive()) {
                for (Move j : i.possibleMoves(board)) {
                    if (j.getXY().getX() == get.getX() && j.getXY().getY() == get.getY()) {
                        res = true;
                    }
                }
            }
        }
        return res;
    }

    private boolean obstructView(Field get, Board board) {
        boolean res = false;
        int x,incX ;
        int y,incY;
       
        boolean stopped = false;
        ArrayList<Field> l = new ArrayList<>();
        x = findWay(get)[0];
        y = findWay(get)[1];
        incX = x;
        incY = y;
        while (!stopped) {
            if (!board.getField(king.getX() + x, king.getY() + y).isOccupied() && checkMove(king.getX() + x, king.getY() + y)) {
                l.add(board.getField(king.getX() + x, king.getY() + y));
                x = x + incX;
                y = y + incY;
            } else {
                stopped = true;
            }
        }

        for (Field i : l) {
            for (Piece j : pieces) {
                if (j.isAlive()) {
                    for (Move k : j.possibleMoves(board)) {
                        if (k.getXY().getX() == i.getX() && k.getXY().getY() == i.getY()) {
                            return true;
                        }
                    }
                }
            }
        }

        return res;
    }

    private int[] findWay(Field get) {
        int[] res = new int[2];
        if ((king.getX() - get.getX()) > 0) {
            res[0] = -1;
        } else if ((king.getX() - get.getX()) < 0) {
            res[0] = 1;
        }

        if ((king.getY() - get.getY()) > 0) {
            res[1] = -1;
        } else if ((king.getY() - get.getY()) < 0) {
            res[1] = 1;
        }
        return res;
    }

    @Override
    public boolean AI() {
        return AI;
    }

    @Override
    public boolean possibleToMove(Board board) {
        boolean res = false;
        for(Piece i : pieces){
            if(i.possibleMoves(board).size()>0){
                res = true;
                break;
            }
        }
        return res;
    }
    
    

}
