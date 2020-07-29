/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

/**
 * Manages the game
 *
 * @author uzivatel
 */
public class Game {

    private Board board = new Board();
    private Player whitePlayer;
    private Player blackPlayer;
    private PlayerType order = PlayerType.WHITE;//0 white players turn,1 black Players turn
    private boolean pieceChosen = false;
    private int[] from = new int[2];

    /**
     * Creates the game, sets default pieces and creates players
     */
    public Game() {

        this.board.setDefaultPieces();
        whitePlayer = new ChessPlayer(PlayerType.WHITE, board);
        blackPlayer = new ChessPlayer(PlayerType.BLACK, board);

    }

    /**
     *
     * @return true if there is a piece selected to be moved, false othewise
     */
    public boolean isChosen() {

        return pieceChosen;
    }

    /**
     * Gives the right to move to next player
     */
    public void nextRound() {

        switch (order) {
            case WHITE: {
                order = PlayerType.BLACK;
                if(blackPlayer.AI()){
                    blackPlayer.performMove();
                    nextRound();
                }
                break;
            }
            case BLACK:
                order = PlayerType.WHITE;
                if(whitePlayer.AI()){
                    whitePlayer.performMove();
                    nextRound();
                }
                break;
        }

    }

    /**
     *
     * @return the player whose turn it is
     */
    public PlayerType getOrder() {
        return order;
    }

    /**
     * Sets the coordinated of piece that we want to move
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void setFrom(int x, int y) {
        from[0] = x;
        from[1] = y;
    }

    /**
     *
     * @param t true if a piece is selected to be moved, false otherwise
     */
    public void setChosen(boolean t) {
        pieceChosen = t;
    }

    /**
     * Tries to move selected to piece to given position
     *
     * @param x X coordinate of final destination
     * @param y Y coordinate of final destination
     * @throws IllegalArgumentException if given piece cannot move to desired
     * coordinate
     */
    public void setTo(int x, int y) throws IllegalArgumentException {
        boolean possible = false;

        for (Move i : board.getField(from[0], from[1]).getPiece().possibleMoves(board)) {
            if (x == i.getXY().getX() && y == i.getXY().getY()) {
                possible = true;
                break;

            }
        }

        if (!possible) {
            throw new IllegalArgumentException();
        }
        
        
        
        board.movePiece(from[0], from[1], x, y);
        switch(order){
            case WHITE:{
                if(whitePlayer.isChecked(blackPlayer, board) != 0){
                    board.movePiece(x, y, from[0],from[1]);
                    for(Piece i : blackPlayer.getPieces()){
                        if(i.getX() == x && i.getY() == y){
                            i.setAlive();
                            board.getField(x, y).addPiece(i);
                            
                            throw new IllegalArgumentException();
                            
                        }
                    }
                    throw new IllegalArgumentException();
                } else {
                    board.writePgn();
                }
                break;
            }
            
            case BLACK:{
                if(blackPlayer.isChecked(whitePlayer, board) != 0){
                    board.movePiece(x, y, from[0],from[1]);
                    for(Piece i : whitePlayer.getPieces()){
                        if(i.getX() == x && i.getY() == y){
                            i.setAlive();
                            board.getField(x, y).addPiece(i);
                            System.out.println("Throw");
                            throw new IllegalArgumentException();
                            
                        }
                    }
                    throw new IllegalArgumentException();
                } else {
                    board.writePgn();
                }
                break;
            }
        }
    }

    /**
     *
     * @return current board
     */
    public Board getBoard() {
        return board;
    }

    public Game(String nameOfFile) {
        this();
        //Perform moves listed in the file
    }

    /**
     *
     * @param i 0 for white, 1 for black
     * @return for 0 return white player , for 1 returns black player
     */
    public Player getPlayers(int i) {
        switch (i) {
            case 0: {
                return whitePlayer;
            }
            default: {
                return blackPlayer;
            }
        }
    }
    
    
}
