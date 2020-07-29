/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

/**
 * Contains the names of all possible chess pieces
 * @author uzivatel
 */
public enum Names {
    //private final String pgnType;
    
    PAWN("P"),
    KING("K"),
    QUEEN("Q"),
    ROOK("R"),
    KNIGHT("N"),
    BISHOP("B");

    private final String pgnType;
    
    Names (String s){
        pgnType = s;
    }
    
    public String getPgn(){
        return pgnType;
    }
}
