
import b152a0b36pr2.chess.Board;
import b152a0b36pr2.chess.Field;
import b152a0b36pr2.chess.Piece;
import b152a0b36pr2.chess.PlayerType;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uzivatel
 */
public class BoardTest {
    Board b = new Board();
    
    @Test
    public void testBoard(){
        Field f = new Field(1,1);
        boolean res = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                f = b.getField(i, j);
                if (f.isOccupied()){
                    res = false;
                }
            }
        }
        assertTrue(res);
    }
    
    @Test
    public void testMove(){
        b.setDefaultPieces();
        Piece tmp = b.getField(6, 0).getPiece();
        b.movePiece(6, 0, 4, 0);
        assertTrue(tmp.equals(b.getField(4, 0).getPiece()));
    }
    
    @Test
    public void testing(){
        b.setDefaultPieces();
        ArrayList<Piece> tmp = b.getPieces(PlayerType.WHITE);
        ArrayList<Piece> tmp1 = b.getPieces(PlayerType.BLACK);
        assertTrue(tmp.size() == tmp1.size());
    }
}
