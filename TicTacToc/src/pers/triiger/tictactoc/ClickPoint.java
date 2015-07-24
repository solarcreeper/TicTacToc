package pers.triiger.tictactoc;
/**
 * 点击位置
 *
 * Created by Trigger on 2015/7/24.
 * @author yehongjiang/trigger yehongjiang2012@gmail.com
 * @version 1.0
 */
public class ClickPoint{
    private int x;
    private int y;

    /**
     * 构造器
     */
    public ClickPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * 获取X
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
}