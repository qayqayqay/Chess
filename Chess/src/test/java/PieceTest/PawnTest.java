/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PieceTest;

import b152a0b36pr2.chess.Names;
import b152a0b36pr2.chess.Pawn;
import b152a0b36pr2.chess.PlayerType;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author uzivatel
 */
public class PawnTest {
    int x = 1;
    int y = 1;
    @Test
    public void testConstructor(){
        Pawn p = new Pawn(x,y,PlayerType.BLACK);
        
        assertTrue(p.getX() == x && p.getY() == y && p.getType() == Names.PAWN && p.getColor() == PlayerType.BLACK);
    }
    
}
