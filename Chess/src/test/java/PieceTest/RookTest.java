/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PieceTest;

import b152a0b36pr2.chess.Names;
import b152a0b36pr2.chess.PlayerType;
import b152a0b36pr2.chess.Rook;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author uzivatel
 */
public class RookTest {
    int x =4;
    int y = 5;
    @Test
    public void testConstructor(){
        Rook r = new Rook(x,y,PlayerType.BLACK);
        
        assertTrue(r.getX() == x && r.getY() == y && r.getColor() == PlayerType.BLACK && r.getType() == Names.ROOK);
    }
}
