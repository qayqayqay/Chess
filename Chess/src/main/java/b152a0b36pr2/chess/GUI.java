/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b152a0b36pr2.chess;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.SwingConstants.HORIZONTAL;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 *
 * @author uzivatel
 */
public class GUI {

    private static Logger LOGGER = Logger.getLogger(GUI.class.getName());
    Game game;
    JPanel graph = new JPanel(new BorderLayout(3, 3));
    JButton[][] chessBoardSquares = new JButton[8][8];
    JPanel chessBoard = new JPanel();
    Clock whiteClock = new Clock(600);
    Clock blackClock = new Clock(600);
    final String messageInicial = "Chess is ready";
    final String messageWhiteTurn = "It's white's turn";
    final String messageBlackTurn = "It's black's turn";
    final String messageWhiteWin = "White won the game";
    final String messageBlackWon = "Black won the game";
    private final int[] list = {0, 1, 2, 3, 4, 5, 6, 7};
    private final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private final static JFrame f = new JFrame("CHESS");
    final JToolBar tools = new JToolBar();
    private static boolean isEnding = true;

    public static void main(String[] args) {
        initializeLogger();
        GUI g = new GUI();

        f.add(g.getGraph());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationByPlatform(true);
        f.pack();
        f.setVisible(true);

    }

    public static void initializeLogger() {

        LOGGER.setUseParentHandlers(true);

        FileHandler handler;
        try {
            handler = new FileHandler("C:/Users/uzivatel/brejlon1/Chess/myLog.txt");
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            Logger.getLogger("").addHandler(handler);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public GUI() {
        initializeGUI();

    }

    public final void initializeGUI() {

        graph.add(tools, BorderLayout.NORTH);//EAST

        tools.setLayout(new GridLayout(1, 0));//4,1
        tools.setFloatable(false);
        //tools.add(new JLabel(messageInicial),0);

        final JButton newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String event = JOptionPane.showInputDialog(graph, "Enter the name of the event");
                String site = JOptionPane.showInputDialog(graph, "Enter the name of the site");
                String date = JOptionPane.showInputDialog(graph, "Enter the date of the game");
                String white = JOptionPane.showInputDialog(graph, "Enter the name of white player");
                String black = JOptionPane.showInputDialog(graph, "Enter the name of black player");
                game = new Game();
                game.getBoard().setPgn(event, site, date, white, black);
                refresh();
                tools.remove(3);
                tools.add(new JLabel(messageWhiteTurn), 3);

                if (!whiteClock.isAlive()) {
                    whiteClock.start();
                    blackClock.start();
                }

                whiteClock.reset();
                blackClock.reset();
                whiteClock.resumeClock();
                blackClock.stopClock();

                f.getContentPane().validate();
                f.getContentPane().repaint();

            }

        });
        tools.add(newGame);

        final JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game != null) {
                    if (!game.getBoard().getPgn().ended()) {
                        game.getBoard().getPgn().addMove(" *");
                    }
                    game.getBoard().getPgn().closeWriter();
                }
                whiteClock.endThread();
                blackClock.endThread();
                f.dispose();

            }
        });
        tools.add(exit);

        tools.addSeparator();
        tools.add(new JLabel(messageInicial), 3);
        tools.setOrientation(HORIZONTAL);//VERTICAL
        tools.setVisible(true);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        graph.add(chessBoard);

        Insets margin = new Insets(5, 5, 5, 5);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton but = new JButton();
                but.setMargin(margin);

                ImageIcon icon;

                BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                icon = new ImageIcon(img);
                but.setIcon(icon);

                if ((i % 2 == 1 && j % 2 == 1)
                        || (i % 2 == 0 && j % 2 == 0)) {
                    but.setBackground(Color.WHITE);
                } else {
                    but.setBackground(Color.LIGHT_GRAY);
                }
                chessBoardSquares[i][j] = but;

            }
        }

        final JPanel timers = new JPanel();
        timers.add(new JLabel("Time remaining"));
        timers.add(new JSeparator());
        timers.add(new JLabel("White: "));
        timers.add(whiteClock.getClock());
        timers.add(new JSeparator());
        timers.add(new JLabel("Black: "));
        timers.add(blackClock.getClock());

        for (int i : list) {
            for (int j : list) {
                final int x = list[i];
                final int y = list[j];
                chessBoardSquares[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!game.isChosen()) {
                            if (game.getBoard().getField(x, y).isOccupied()) {
                                if (game.getOrder() == game.getBoard().getField(x, y).getPiece().getColor()) {
                                    game.setFrom(x, y);
                                    game.setChosen(true);

                                }
                            }
                        } else {
                            try {

                                game.setTo(x, y);
                                refresh();
                                int check = game.getPlayers(0).isChecked(game.getPlayers(1), game.getBoard());
                                if (check == 1) {
                                    game.getBoard().getPgn().addMove("+");
                                    JOptionPane.showMessageDialog(graph, "White is checked");
                                    LOGGER.log(Level.SEVERE, "White is checked");
                                } else if (check == 2) {
                                    game.getBoard().getPgn().addMove("# 0-1");
                                    game.getBoard().getPgn().closeWriter();
                                    JOptionPane.showMessageDialog(graph, "White is check mated");
                                    LOGGER.info("Game end");
                                    String n[] = new String[2];
                                    n[0] = "YES";
                                    n[1] = "NO";
                                    game.getBoard().getPgn().closeWriter();
                                    int res = JOptionPane.showOptionDialog(graph, "Start a new game?", "Start a new game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, n, 1);

                                    switch (res) {
                                        case 0: {
                                            newGame.doClick();
                                            break;
                                        }
                                        case 1: {
                                            exit.doClick();
                                            break;
                                        }
                                    }
                                    throw new IllegalArgumentException();
                                }

                                check = game.getPlayers(1).isChecked(game.getPlayers(0), game.getBoard());
                                if (check == 1) {
                                    game.getBoard().getPgn().addMove("+");
                                    LOGGER.log(Level.SEVERE, "Black is checked");
                                    JOptionPane.showMessageDialog(graph, "Black Is checked: ");
                                } else if (check == 2) {
                                    game.getBoard().getPgn().addMove("# 1-0");
                                    game.getBoard().getPgn().closeWriter();
                                    JOptionPane.showMessageDialog(graph, "Black is check mated");
                                    LOGGER.log(Level.SEVERE, "Black is checked");
                                    String n[] = new String[2];
                                    n[0] = "YES";
                                    n[1] = "NO";
                                    game.getBoard().getPgn().closeWriter();
                                    int res = JOptionPane.showOptionDialog(graph, "Start a new game?", "Start a new game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, n, 1);

                                    switch (res) {
                                        case 0: {
                                            newGame.doClick();
                                            break;
                                        }
                                        case 1: {
                                            exit.doClick();
                                            break;
                                        }
                                    }
                                    throw new IllegalArgumentException();
                                }
                                game.setChosen(false);
                                game.nextRound();

                                switch (game.getOrder()) {
                                    case WHITE: {
                                        tools.remove(3);

                                        tools.add(new JLabel(messageWhiteTurn), 3);
                                        blackClock.stopClock();
                                        whiteClock.resumeClock();
                                        f.getContentPane().validate();
                                        f.getContentPane().repaint();
                                        if (!game.getPlayers(0).possibleToMove(game.getBoard())) {
                                            game.getBoard().getPgn().addMove(" 1/2-1/2");
                                            game.getBoard().getPgn().closeWriter();
                                            JOptionPane.showMessageDialog(graph, "Game end in draw");
                                        }
                                        break;
                                    }
                                    case BLACK: {
                                        tools.remove(3);

                                        tools.add(new JLabel(messageBlackTurn), 3);
                                        blackClock.resumeClock();
                                        whiteClock.stopClock();
                                        f.getContentPane().validate();
                                        f.getContentPane().repaint();
                                        if (!game.getPlayers(1).possibleToMove(game.getBoard())) {
                                            game.getBoard().getPgn().addMove(" 1/2-1/2");
                                            game.getBoard().getPgn().closeWriter();
                                            JOptionPane.showMessageDialog(graph, "Game end in draw");
                                        }
                                        break;
                                    }

                                }

                            } catch (IllegalArgumentException ae) {

                                LOGGER.log(Level.FINE, "invalid move, reseting piece selection", ae);
                                game.setChosen(false);
                            }
                        }
                    }

                });
            }
        }
        chessBoard.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            chessBoard.add(
                    new JLabel("" + (chars[i]),
                            SwingConstants.CENTER));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (j == 0) {
                    chessBoard.add(new JLabel(Integer.toString(8 - i), SwingConstants.CENTER));
                }

                chessBoard.add(chessBoardSquares[i][j]);

            }
        }

        graph.add(timers, BorderLayout.PAGE_END);

        graph.setVisible(true);
    }

    public JPanel getGraph() {
        return graph;
    }

    public void refresh() {
        ImageIcon icon1 = new ImageIcon();
        BufferedImage img;
        String name;
        Field field;
        String c = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                field = game.getBoard().getField(i, j);
                if (field.isOccupied()) {
                    name = "";
                    name += field.getPiece().getColor().toString() + "_" + field.getPiece().getType().toString() + ".png";
                    
                    c = name;
                    //ImageIcon icon1 = new ImageIcon(getClass().getClassLoader().getResource(c));//works while building in NetBeans
                    URL u = GUI.class.getClassLoader().getResource("b152a0b36pr2/chess/"+name);
                    
                    if (u == null){
                       icon1 = new ImageIcon(getClass().getClassLoader().getResource(c));
                    } else {
                            
                    
                     icon1 = new ImageIcon(u);}
chessBoardSquares[i][j].setIcon(icon1);
                } else {

                    img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                    chessBoardSquares[i][j].setIcon(new ImageIcon(img));

                }

            }
        }

    }

}
