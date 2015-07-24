package pers.triiger.tictactoc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 程序逻辑处理
 *
 * Created by Trigger on 2015/7/22.
 * @author yehongjiang/trigger yehongjiang2012@gmail.com
 * @version 1.0
 */
public class TicService {

    private static final int CHESS_LENGTH = 100;
    private static final int CHESS_IMAGE_SIZE = 80;
    private static final int O_WIN_FLAG = Chess.CHESS_O * Chess.CHESS_O * Chess.CHESS_O;
    private static final int X_WIN_FLAG = Chess.CHESS_X * Chess.CHESS_X * Chess.CHESS_X;
    //棋子矩阵
    Chess chesses[][] = null;

    //棋子图片
    Image chessO = null;
    Image chessX = null;

    //消息图片
    Image oWin = null;
    Image xWin = null;
    Image tie = null;
    Image showMode = null;

    //当前执子方标志图片
    Image flag1 = null;
    Image flag2 = null;

    //OX得分
    private int scoresO;
    private int scoresX;

    //当前执子方, ture为O,false为X;
    private boolean isO;

    //先手方
    private boolean offensive;

    //游戏局数
    private int round;
    //游戏开始的标志
    private boolean isStoped;
    //游戏进行的步数
    private int count;

    //私有构造对象
    private static TicService ticService = null;

    //进入游戏，选择模式
    private int mode;
    /**
     * 构造器
     */
    private TicService(){
        super();
        this.initChesses();
    }

    /**
     * 获取私有构造
     */
    public static TicService getTicService(){
        if(ticService == null){
            ticService = new TicService();
        }
        return ticService;
    }



    /**
     * 初始化棋盘上的棋子
     *
     * @return void
     */
    public void initChesses(){
        //设置模式=0，b表示未选择
        this.mode = 0;
        //设置先手为O
        this.offensive = true;
        //设置开局
        this.isO = true;
        //设置回合数为1
        this.round = 1;
        //初始化开始游戏的标志
        this.isStoped = true;
        //初始化步数
        this.count = 0;

        //初始资源图片
        URL url = null;
        ImageIcon icon = null;

        url = TicFrame.class.getResource(TicFrame.CHESS_O);
        icon = new ImageIcon(url);
        chessO = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.CHESS_X);
        icon = new ImageIcon(url);
        chessX = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.O_WIN_IMG);
        icon = new ImageIcon(url);
        oWin   = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.X_WIN_IMG);
        icon = new ImageIcon(url);
        xWin   = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.TIE_IMG);
        icon = new ImageIcon(url);
        tie    = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.FLAG_IMG_O);
        icon = new ImageIcon(url);
        flag1  = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.FLAG_IMG_X);
        icon = new ImageIcon(url);
        flag2  = icon.getImage();
        url = TicFrame.class.getResource(TicFrame.SHOWMODE_IMG);
        icon = new ImageIcon(url);
        showMode = icon.getImage();

        //初始化棋子
        this.chesses = new Chess[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                chesses[i][j] = new Chess(j * CHESS_LENGTH + 10, i * CHESS_LENGTH + 10);
            }
        }
    }

    /**
     * 重新开始
     *
     * @return void
     */
    public void restart(){
        this.round++;
        this.isO = this.round%2 == 1 ? true:false;
        this.offensive = !this.offensive;
        this.isStoped = true;
        this.count = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                chesses[i][j] = new Chess(j * CHESS_LENGTH + 10, i * CHESS_LENGTH + 10);
            }
        }
    }


    /**
     * 增加得分
     *
     * @return void
     */
    public void addScores(){
        if(isOWin(chesses)){
            this.scoresO++;
        }
        if(isXWin(chesses)){
            this.scoresX++;
        }
    }

    /**
     * 获取当前棋局
     *
     * @return Chess[][]
     */
    public Chess[][] getChesses(){
        return this.chesses;
    }

    /**
     * 获取当前执子方
     *
     * @return boolean
     */
    public boolean isO(){
        return this.isO;
    }

    /**
     * 获得该局先手
     *
     * @return boolean
     */
    public boolean getOffensive(){
        return this.offensive;
    }

    /**
     * 获取当前点击的棋盘格
     *
     * @param e MouseEvent
     * @return ClickPoint(自造类)
     */
    public ClickPoint getClickLocation(MouseEvent e){
        int x = e.getY() / 100;
        int y = e.getX() / 100;
        return new ClickPoint(x, y);
    }

    /**
     * 判断O是否胜利
     *
     * @param chesses Chess[][]
     * @return boolean
     */
    public boolean isOWin(Chess[][] chesses){
        //水平方向检测输赢
        for(int i = 0; i < 3; i++){
            if(chesses[i][0].getBelonging() * chesses[i][1].getBelonging() * chesses[i][2].getBelonging() == O_WIN_FLAG){
                return true;
            }
        }
        //垂直方向检测输赢
        for(int i = 0; i < 3; i++){
            if(chesses[0][i].getBelonging() * chesses[1][i].getBelonging() * chesses[2][i].getBelonging() == O_WIN_FLAG){
                return true;
            }
        }
        if(chesses[0][0].getBelonging() * chesses[1][1].getBelonging() * chesses[2][2].getBelonging() == O_WIN_FLAG){
            return true;
        }
        if(chesses[0][2].getBelonging() * chesses[1][1].getBelonging() * chesses[2][0].getBelonging() == O_WIN_FLAG){
            return true;
        }
        return false;
    }

    /**
     * 判断X是否胜利
     *
     * @param chesses Chess[][]
     * @return boolean
     */
    public boolean isXWin(Chess[][] chesses){
        //水平方向检测输赢
        for(int i = 0; i < 3; i++){
            if(chesses[i][0].getBelonging() * chesses[i][1].getBelonging()*chesses[i][2].getBelonging() == X_WIN_FLAG){
                return true;
            }
        }
        //垂直方向检测输赢
        for(int i = 0; i < 3; i++){
            if(chesses[0][i].getBelonging() * chesses[1][i].getBelonging() * chesses[2][i].getBelonging() == X_WIN_FLAG){
                return true;
            }
        }
        if(chesses[0][0].getBelonging()*chesses[1][1].getBelonging()*chesses[2][2].getBelonging() == X_WIN_FLAG){
            return true;
        }
        if(chesses[0][2].getBelonging()*chesses[1][1].getBelonging()*chesses[2][0].getBelonging() == X_WIN_FLAG){
            return true;
        }
        return false;
    }

    /**
     * 检测平局
     *
     * @param chesses Chess[][]
     * @return boolean
     */
    public boolean isTie(Chess[][] chesses){
        if(this.count == 9 && !isOWin(chesses) && !isXWin(chesses)){
            return true;
        }
        return false;
    }

    /**
     * 更新棋盘
     *
     * @parm p ClickPoint
     * @return boolean
     */
    public boolean updateChessBoard(ClickPoint p){
        boolean result = false;
        int x = p.getX();
        int y = p.getY();
        if(chesses[x][y].getImage() == null){
            result = true;
            this.count++;
            if(isO){
                chesses[x][y].setImage(chessO);
                chesses[x][y].setBelonging(Chess.CHESS_O);
            }else {
                chesses[x][y].setImage(chessX);
                chesses[x][y].setBelonging(Chess.CHESS_X);

            }
            this.isO = !this.isO;
        }
        return result;
    }

    /**
     * 设置游戏结束的标志
     *
     * @param b boolean
     * @return void
     */
    public void setStoped(boolean b){
        this.isStoped = b;
    }

    /**
     * 获取是否结束游戏的标志
     *
     * @return boolean
     */
    public boolean isStoped(){
        return this.isStoped;
    }

    /**
     * 设置游戏mode
     *
     * @param mode int
     * @return void
     */
    public void setMode(int mode){
        this.mode = mode;
    }

    /**
     * 获取游戏mode
     *
     * @return int
     */
    public int getMode(){
        return this.mode;
    }


    /**
     * 绘制消息
     *
     * @param g Graphics
     * @return void
     */
    public void paintMsg(Graphics g){
        if(mode == 0){
            g.drawImage(showMode, 0, 0, TicFrame.MESSAGE_WIDTH, TicFrame.MESSAGE_HEIGHT, null);
        }
        if(isOWin(chesses)){
            g.drawImage(oWin, 0, 0, TicFrame.MESSAGE_WIDTH, TicFrame.MESSAGE_HEIGHT, null);
        }
        if(isXWin(chesses)){
            g.drawImage(xWin, 0, 0, TicFrame.MESSAGE_WIDTH, TicFrame.MESSAGE_HEIGHT, null);
        }
        if(isTie(chesses)){
            g.drawImage(tie, 0, 0, TicFrame.MESSAGE_WIDTH, TicFrame.MESSAGE_HEIGHT, null);

        }else {
            g.drawImage(null, 0, 0, TicFrame.MESSAGE_WIDTH, TicFrame.MESSAGE_HEIGHT, null);
        }

    }

    /**
     * 绘制分数面板
     * @param g Graphics
     * @return void
     */
    public void paintO(Graphics g){
        int a = scoresO/10;
        int b = scoresO%10;
        String strA = "/" + a + ".png";
        String strB = "/" + b + ".png";
        Image imgA = null;
        Image imgB = null;
        //使用URL,编译jar时资源不会出错
        URL url = TicFrame.class.getResource(strA);
        ImageIcon icon = new ImageIcon(url);
        imgA = icon.getImage();
        url = TicFrame.class.getResource(strB);
        icon = new ImageIcon(url);
        imgB = icon.getImage();
        g.drawImage(imgA, 0, 0, TicFrame.SCORESPANEL_WIDTH/2, TicFrame.SCORESPANEL_HEIGHT, null);
        g.drawImage(imgB, TicFrame.SCORESPANEL_WIDTH/2, 0, TicFrame.SCORESPANEL_WIDTH/2, TicFrame.SCORESPANEL_HEIGHT, null);
    }

    /**
     * 绘制分数面板
     * @param g Graphics
     * @return void
     */
    public void paintX(Graphics g){
        int a = scoresX/10;
        int b = scoresX%10;
        String strA = "/" + a + ".png";
        String strB = "/" + b + ".png";
        Image imgA = null;
        Image imgB = null;
        //使用URL,编译jar时资源不会出错
        URL url = TicFrame.class.getResource(strA);
        ImageIcon icon = new ImageIcon(url);
        imgA = icon.getImage();
        url = TicFrame.class.getResource(strB);
        icon = new ImageIcon(url);
        imgB = icon.getImage();
        g.drawImage(imgA, 0, 0, TicFrame.SCORESPANEL_WIDTH/2, TicFrame.SCORESPANEL_HEIGHT, null);
        g.drawImage(imgB, TicFrame.SCORESPANEL_WIDTH/2, 0, TicFrame.SCORESPANEL_WIDTH/2, TicFrame.SCORESPANEL_HEIGHT, null);
    }

    /**
     * 绘制棋盘上的棋子
     *
     * @param g Graphics
     * @return void
     */
    public void paint(Graphics g){
        int x;
        int y;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(chesses[i][j].getImage() != null){
                    x = chesses[i][j].getX();
                    y = chesses[i][j].getY();
                    g.drawImage(chesses[i][j].getImage(), x, y, CHESS_IMAGE_SIZE, CHESS_IMAGE_SIZE, null);
                }
            }
        }
    }

    /**
     * 绘制当前执子方的标志
     *
     * @param g Graphics
     * @return void
     */
    public void paintFlagL(Graphics g){
        //当前为
        if(isO){
            g.drawImage(flag1, 0, 0, TicFrame.FLAG_SIZE, TicFrame.FLAG_SIZE, null);
        }else {
            g.drawImage(null, 0, 0, TicFrame.FLAG_SIZE, TicFrame.FLAG_SIZE, null);
        }
    }

    public void paintFlagR(Graphics g){
        //当前为
        if(!isO){
            g.drawImage(flag2, 0, 0, TicFrame.FLAG_SIZE, TicFrame.FLAG_SIZE, null);

        }else {
            g.drawImage(null, 0, 0, TicFrame.FLAG_SIZE, TicFrame.FLAG_SIZE, null);
        }
    }
}
