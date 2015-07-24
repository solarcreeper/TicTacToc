package pers.triiger.tictactoc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 程序主界面
 *
 * Created by Trigger on 2015/7/22.
 * @author yehongjiang/triiger yehongjiang2012@gmail.com
 * @version 1.0
 */
public class TicFrame extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 488;
    public static final int CHESSBOARD_SIZE = 300;
    public static final int CHESSBOARD_TOP_X = 225;
    public static final int CHESSBOARD_TOP_Y = 80;
    public static final int MESSAGE_TOP_X = 125;
    public static final int MESSAGE_TOP_Y = 375;
    public static final int MESSAGE_WIDTH = 550;
    public static final int MESSAGE_HEIGHT = 60;
    public static final int SCORESPANEL_O_X = 60;
    public static final int SCORESPANEL_O_Y = 220;
    public static final int SCORESPANEL_X_X = 655;
    public static final int SCORESPANEL_X_Y = 220;
    public static final int SCORESPANEL_WIDTH = 90;
    public static final int SCORESPANEL_HEIGHT = 65;
    public static final int FLAG_L_X = 150;
    public static final int FLAG_L_Y = 110;
    public static final int FLAG_R_X = 615;
    public static final int FLAG_R_Y = 110;

    public static final int FLAG_SIZE = 40;



    public static final String BACKGROUND_IMG = "img/background.jpg";
    public static final String CHESSBOARD_IMG = "img/TicTacToc.jpg";
    public static final String CHESS_O = "img/o.png";
    public static final String CHESS_X = "img/x.png";
    public static final String O_WIN_IMG = "img/0Win.png";
    public static final String X_WIN_IMG = "img/xWin.png";
    public static final String TIE_IMG = "img/tie.png";
    public static final String FLAG_IMG_O = "img/flag1.png";
    public static final String FLAG_IMG_X = "img/flag2.png";
    public static final String SHOWMODE_IMG = "img/show.png";

    private ChessBoardPanel chessBoardPanel = null;
    private JLabel background = null;
    private JPanel message = null;
    private JPanel sorcesO = null;
    private JPanel sorcesX = null;
    private JPanel flagL   = null;
    private JPanel flagR   = null;

    private TicService ticService = null;
    private AIChess aiChess = null;
    /**
     * 构造器
     */
    public TicFrame(){
        super();
        this.initilize();
    }

    /**
     * 初始化界面
     *
     * @return void
     */
    private void initilize(){
        //初始化主窗体
        this.setTitle("TicTacToc");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setBackground(Color.white);
        this.setLayout(null);

        //设置信息显示窗
        message = this.getMessagePanel();
        this.add(message);
        //设置棋盘
        chessBoardPanel = this.getChessBoardPanel();
        this.add(chessBoardPanel);
        //设置分数面板
        sorcesO = getSorcesOPanel();
        this.add(sorcesO);
        sorcesX = getSorcesXPanel();
        this.add(sorcesX);
        //设置执子方标志
        flagL = getFlagLPanel();
        this.add(flagL);
        flagR = getFlagRPanel();
        this.add(flagR);
        //设置背景
        background = this.getBackround();
        this.add(background);
        //初始化服务
        this.ticService = TicService.getTicService();
        //初始化AI
        this.aiChess = AIChess.getAI();
        //初始化键盘监听
        this.createKeylistener();
    }

    /**
     * 获取绘图区
     *
     * @return ChessBoardPanel
     */
    private ChessBoardPanel getChessBoardPanel(){
        if(this.chessBoardPanel == null){
            chessBoardPanel = new ChessBoardPanel();
            chessBoardPanel.setOpaque(false);
            chessBoardPanel.setBounds(CHESSBOARD_TOP_X, CHESSBOARD_TOP_Y, CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        }
        return chessBoardPanel;
    }

    /**
     * 获得背景对象
     *
     * @return JLable
     */
    private JLabel getBackround(){
        if(this.background == null){
            ImageIcon imageIcon = new ImageIcon(BACKGROUND_IMG);
            background = new JLabel(imageIcon);
            background.setBounds(0, 0, WIDTH, HEIGHT);
        }
        return background;
    }

    /**
     * 初始化信息显示框
     *
     * @return JPanel
     */
    private JPanel getMessagePanel(){
        if(this.message == null){
            this.message = new MessagePanel();
        }
        message.setOpaque(false);
        message.setBounds(MESSAGE_TOP_X, MESSAGE_TOP_Y, MESSAGE_WIDTH, MESSAGE_HEIGHT);
        return message;
    }

    /**
     * 获取分数面板O对象
     *
     * @return JPanel
     */
    private JPanel getSorcesOPanel(){
        if(this.sorcesO == null){
            this.sorcesO = new ScoresOPanel();
        }
        sorcesO.setOpaque(false);
        sorcesO.setBounds(SCORESPANEL_O_X, SCORESPANEL_O_Y, SCORESPANEL_WIDTH, SCORESPANEL_HEIGHT);
        return sorcesO;
    }

    /**
     * 获取分数面板O对象
     *
     * @return JPanel
     */
    private JPanel getSorcesXPanel(){
        if(this.sorcesX == null){
            this.sorcesX = new ScoresXPanel();
        }
        sorcesX.setOpaque(false);
        sorcesX.setBounds(SCORESPANEL_X_X, SCORESPANEL_O_Y, SCORESPANEL_WIDTH, SCORESPANEL_HEIGHT);
        return sorcesX;
    }

    /**
     * 获取执子方标志对象
     *
     * @return JPanel
     */
    private JPanel getFlagLPanel(){
        if(this.flagL == null){
            flagL = new FlagLPanel();
        }
        flagL.setOpaque(false);
        flagL.setBounds(FLAG_L_X, FLAG_L_Y, FLAG_SIZE, FLAG_SIZE);
        return flagL;
    }

    /**
     * 获取执子方标志对象
     *
     * @return JPanel
     */
    private JPanel getFlagRPanel(){
        if(this.flagR == null){
            flagR = new FlagRPanel();
        }
        flagR.setOpaque(false);
        flagR.setBounds(FLAG_R_X, FLAG_R_Y, FLAG_SIZE, FLAG_SIZE);
        return flagR;
    }


    /**
     * 设置键盘事件监听器
     */
    private void createKeylistener(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_F2:
                        if(!ticService.isStoped()){
                            ticService.restart();
                            aiChess.initAI();
                            flagL.repaint();
                            flagR.repaint();
                            if(!ticService.isO()){
                                ClickPoint p = aiChess.aiFirstRun(ticService.getChesses());
                                ticService.updateChessBoard(p);
                            }
                            message.repaint();
                            chessBoardPanel.repaint();
                        }
                        break;
                    case KeyEvent.VK_F3:
                        if(ticService.getMode() == 0){
                            ticService.setMode(1);
                            flagL.repaint();
                            flagR.repaint();
                            message.repaint();
                        }
                        break;
                    case KeyEvent.VK_F4:
                        if(ticService.getMode() == 0){
                            ticService.setMode(2);
                            //若AI先手
                            if(!ticService.isO()){
                                ClickPoint p = aiChess.aiFirstRun(ticService.getChesses());
                                ticService.updateChessBoard(p);
                            }
                            flagL.repaint();
                            flagR.repaint();
                            message.repaint();
                            chessBoardPanel.repaint();
                        }
                        break;
                    case KeyEvent.VK_F1:
                        aiChess.initAI();
                        ticService.restart();
                        if(!ticService.isO()){
                            ClickPoint p = aiChess.aiFirstRun(ticService.getChesses());
                            ticService.updateChessBoard(p);
                        }
                        flagL.repaint();
                        flagR.repaint();
                        chessBoardPanel.repaint();
                        message.repaint();
                        break;


                }
            }
        });

    }

    /**
     * 棋盘绘图区
     *
     */
    private class ChessBoardPanel extends JPanel{
        /**
         * 构造器
         */
        public ChessBoardPanel(){
            super();
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    switch (ticService.getMode()){
                        case 0:
                            //未选择模式，点击无效
                            break;
                        case 1:
                            //双人模式
                            if(ticService.isStoped()){
                                ClickPoint p = ticService.getClickLocation(e);
                                ticService.updateChessBoard(p);
                                chessBoardPanel.repaint();
                                flagL.repaint();
                                flagR.repaint();
                            }
                            if((ticService.isOWin(ticService.getChesses()) || ticService.isXWin(ticService.getChesses())) && ticService.isStoped()){
                                ticService.addScores();
                                sorcesO.repaint();
                                sorcesX.repaint();
                                message.repaint();
                                ticService.setStoped(false);
                            }
                            if(ticService.isTie(ticService.getChesses())){
                                message.repaint();
                                ticService.setStoped(false);
                            }
                            break;
                        case 2:
                            //单人模式
                            if(ticService.isStoped()){
                                ClickPoint p = ticService.getClickLocation(e);
                                ticService.updateChessBoard(p);
                                chessBoardPanel.repaint();
                                flagL.repaint();
                                flagR.repaint();
                                if(ticService.isOWin(ticService.getChesses()) && ticService.isStoped()){
                                    ticService.addScores();
                                    sorcesO.repaint();
                                    message.repaint();
                                    ticService.setStoped(false);
                                }else {
                                    //AI执子
                                    ClickPoint p2 = null;
                                    if(!ticService.getOffensive()) p2 = aiChess.aiFirstRun(ticService.getChesses());
                                    if(ticService.getOffensive()) p2 = aiChess.aiSecondRun(ticService.getChesses());
                                    if(p2 != null) ticService.updateChessBoard(p2);
                                    if(ticService.isXWin(ticService.getChesses()) && ticService.isStoped()){
                                        ticService.addScores();
                                        sorcesX.repaint();
                                        message.repaint();
                                        ticService.setStoped(false);
                                    }
                                }

                            }
                            if(ticService.isTie(ticService.getChesses())){
                                message.repaint();
                                ticService.setStoped(false);
                            }
                            break;
                    }
                }
            });
        }

        /**
         * 重写void paint（Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paint(g);
        }

    }

    /**
     * 绘制message
     *
     */
    private class MessagePanel extends JPanel{

        /**
         * 重写void paint(Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paintMsg(g);
        }
    }

    private class ScoresOPanel extends JPanel{

        /**
         * 重写void paint(Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paintO(g);
        }
    }

    private class ScoresXPanel extends JPanel{
        /**
         * 重写void paint(Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paintX(g);
        }
    }

    private class FlagLPanel extends JPanel{
        /**
         * 重写void paint(Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paintFlagL(g);
        }
    }

    private class FlagRPanel extends JPanel{
        /**
         * 重写void paint(Graphics g)方法
         *
         * @param g Graphics
         * @return void
         */
        public void paint(Graphics g){
            super.paint(g);
            ticService.paintFlagR(g);
        }
    }

}
