/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

/**
 *Contains coordinates [x,y]
 * @author uzivatel
 */
class Position {
    private int x;
    private int y;
    /**
     * 
     * @param x X coordinate of the position
     * @param y Y coordinate of the position
     */
    public Position(int x,int y){
        this.x = x;
        this.y = y;
    }
    /**
     * 
     * @return returns the X coordinate of the position
     */
    public int getX(){
        return this.x;
    }
    /**
     * 
     * @return returns the Y coordinate of the position
     */
    public int getY(){
        return this.y;
    }
    /**
     * Sets X coordinate to value x
     * @param x  desired value 
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * Sets Y coordinate to value y
     * @param y  desired value 
     */
    public void setY(int y){
        this.y = y;
    }
    
    
    
    
}
