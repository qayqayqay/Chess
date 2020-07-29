/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author uzivatel
 */
public class Pgn {
   private PrintWriter writer ;
   private final String event = "[Event \"";
   private final String site =  "[Site \"";
   private final String date = "[Date \"";
   private final String round = "[Round \"";
   private final String white = "[White \"";
   private final String black = "[Black \"";
   private final String result = "[Result \"";
   private final String end = "\"]";
   private boolean gameEnded = false;
   
   public  Pgn(String event, String site,String date,String white,String black) {
       try {
           writer = new PrintWriter("PgnOutput.txt","UTF-8");
       } catch (FileNotFoundException ex) {
           Logger.getLogger(Pgn.class.getName()).log(Level.SEVERE, null, ex);
       } catch (UnsupportedEncodingException ex) {
           Logger.getLogger(Pgn.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       addEvent(event);
       addSite(site);
       addDate(date);
       addWhite(white);
       addBlack(black);
       
   }
   
   public void addMove(String s){
       writer.print(s);
   }
   
   private void addEvent(String s){
       writer.println(event + s + end);
   }
   
   private void addSite(String s){
       writer.println(site + s +end);
   }
   
   private void addDate(String s){
       writer.println(date + s + end);
   }
   
   private void addRound(String s){
       writer.println(round + s +end);
   }
   
   private void addWhite(String s){
       writer.println(white + s + end);
   }
   
   private void addBlack(String s){
       writer.println(black + s +end);
   }
   
   private void addResult(String s){
       writer.println(result +s + end);
   }
   
   public void closeWriter(){
       writer.close();
       gameEnded = true;
   }
   
   public boolean ended(){
       return gameEnded;
   }
   
   
}
