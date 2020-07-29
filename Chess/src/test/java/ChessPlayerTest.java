
import b152a0b36pr2.chess.Board;
import b152a0b36pr2.chess.ChessPlayer;
import b152a0b36pr2.chess.Game;
import b152a0b36pr2.chess.PlayerType;
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
public class ChessPlayerTest {
    Board board = new Board();
    ChessPlayer p ;
    
    @Test
    public void testPlayer(){
        board.setDefaultPieces();
        p = new ChessPlayer(PlayerType.WHITE, board);
        assertTrue(p.getPieces().size() == 16 && p.getColor() == PlayerType.WHITE );
    }
    
    @Test
    public void testPlayer2(){
        Game game = new Game();
        p = (ChessPlayer)game.getPlayers(0);
        assertTrue(p.possibleToMove(game.getBoard()));
    }
}
