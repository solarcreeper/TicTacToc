package pers.triiger.tictactoc;

import java.awt.*;

/**
 * 棋子对象
 *
 * Created by Trigger on 2015/7/22.
 * @author yehongjiang/tirgger yehongjiang2012@gmail.com
 * @version 1.0
 */
public class Chess {
    public static final int CHESS_NULL = 0;
    public static final int CHESS_O = 1;
    public static final int CHESS_X = 2;
    //棋子位置
    private int x;
    //棋子位置
    private int y;
    //棋子图片
    private Image image = null;
    //棋子归属。1->o, 2->x, 0->null
    private int belonging;

    /**
     * 带图片的构造器
     *
     * @param x int
     * @param y int
     * @param image Image
     */
    public Chess(int x, int y, Image image){
        super();
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /**
     * 不带图片的构造器
     *
     * @param x int
     * @param y int
     */
    public Chess(int x, int y){
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * 设置棋子图片
     *
     * @param image Image
     * @return void
     */
    public void setImage(Image image){
        this.image = image;
    }

    /**
     * 获取棋子图片
     *
     * @return Image
     */
    public Image getImage(){
        return this.image;
    }

    /**
     * 获取x
     *
     * @return int
     */
    public int getX(){
        return this.x;
    }

    /**
     * 获取y
     *
     * @return int
     */
    public int getY(){
        return this.y;
    }

    /**
     * 设置belonging
     *
     * @param be int
     * @return void
     */
    public void setBelonging(int be){
        this.belonging = be;
    }

    /**
     * 获取belonging
     *
     * @return unt
     */
    public int getBelonging(){
        return this.belonging;
    }
}
