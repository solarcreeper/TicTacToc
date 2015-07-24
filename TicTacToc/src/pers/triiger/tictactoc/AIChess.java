package pers.triiger.tictactoc;

/**
 * AI
 *
 * Created by Trigger on 2015/7/23.
 * @author yehongjiang/trigger yehongjiang2012@gmail.com
 * @version 1.0
 */
public class AIChess {
    private static AIChess aiChess = null;

    private int steps;
    private ClickPoint point;
    private TicService ticService = null;

    /**
     * 构造器
     */
    private AIChess(){
        this.steps = 0;
        ticService = TicService.getTicService();
    }

    /**
     * 获取私有构造
     */
    public static AIChess getAI(){
        if(aiChess == null){
            aiChess = new AIChess();
        }
        return aiChess;
    }

    /**
     * 初始化AI
     *
     * @return void
     */
    public void initAI(){
        this.steps = 0;
    }

    /**
     * AI先手
     *
     * @param chesses Chess[][]
     * @return void
     */
    public ClickPoint aiFirstRun(Chess chesses[][]){
        switch (steps){
            case 0:
                steps++;
                return new ClickPoint(1, 1);
            case 1:
                steps++;
                if(chesses[0][0].getImage() == null) return new ClickPoint(0, 0);
                if(chesses[0][2].getImage() == null) return new ClickPoint(0, 2);
                if(chesses[2][2].getImage() == null) return new ClickPoint(2, 2);
                if(chesses[2][0].getImage() == null) return new ClickPoint(2, 0);
            case 2:
                steps++;
                ClickPoint calPoint = this.calPoint(chesses);
                if(calPoint != null) return calPoint;
                //一下步骤可能不必要
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (chesses[i][j].getImage() == null) {
                            return new ClickPoint(i, j);
                        }
                    }
                }
            case 3:
                steps++;
                ClickPoint calPoint1 = this.calPoint(chesses);
                if(calPoint1 != null) return calPoint1;
                //一下步骤可能不必要
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (chesses[i][j].getImage() == null) {
                            return new ClickPoint(i, j);
                        }
                    }
                }
            case 4:
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (chesses[i][j].getImage() == null) {
                            return new ClickPoint(i, j);
                        }
                    }
                }
            default:
                return null;
        }
    }

    /**
     * AI后手
     *
     * @param chesses Chess[][]
     * @return void
     */
    public ClickPoint aiSecondRun(Chess[][] chesses){
        switch (steps){
            case 0:
                steps++;
                if(chesses[1][1].getImage() == null) return new ClickPoint(1, 1);
                if(chesses[0][0].getImage() == null) return new ClickPoint(0, 0);
                if(chesses[0][2].getImage() == null) return new ClickPoint(0, 2);
                if(chesses[2][2].getImage() == null) return new ClickPoint(2, 2);
                if(chesses[2][0].getImage() == null) return new ClickPoint(2, 0);
            case 1:
                steps++;
                //检测对手是否下一步会胜利，如果是则防守
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        if(chesses[i][j].getImage() == null){
                            chesses[i][j].setBelonging(Chess.CHESS_O);
                            if(ticService.isOWin(chesses)){
                                chesses[i][j].setBelonging(Chess.CHESS_NULL);
                                return new ClickPoint(i, j);
                            }else {
                                chesses[i][j].setBelonging(Chess.CHESS_NULL);
                            }
                        }
                    }
                }
                //对手下一步无法胜利则选择进攻
                if(chesses[0][0].getImage() == null) return new ClickPoint(0, 0);
                if(chesses[0][2].getImage() == null) return new ClickPoint(0, 2);
                if(chesses[2][2].getImage() == null) return new ClickPoint(2, 2);
                if(chesses[2][0].getImage() == null) return new ClickPoint(2, 0);
            case 2:
                steps++;
                ClickPoint calPoint = this.calPoint(chesses);
                if(calPoint != null) return calPoint;
                //一下步骤可能不必要
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (chesses[i][j].getImage() == null) {
                            return new ClickPoint(i, j);
                        }
                    }
                }
            case 3:
                steps++;
                ClickPoint calPoint1 = this.calPoint(chesses);
                if(calPoint1 != null) return calPoint1;
                //一下步骤可能不必要
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (chesses[i][j].getImage() == null) {
                            return new ClickPoint(i, j);
                        }
                    }
                }
            default:
                return null;
        }
    }

    /**
     * AI计算
     *
     * @param chesses Chess[][]
     * @return ClickPoint
     */
    private ClickPoint calPoint(Chess[][] chesses){
        //检测能否直接胜利
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (chesses[i][j].getImage() == null) {
                    chesses[i][j].setBelonging(Chess.CHESS_X);
                    if (ticService.isXWin(chesses)) {
                        chesses[i][j].setBelonging(Chess.CHESS_NULL);
                        return new ClickPoint(i, j);
                    } else {
                        chesses[i][j].setBelonging(Chess.CHESS_NULL);
                    }
                }
            }
        }
        //检测对手是否下一步会胜利，如果是则防守
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(chesses[i][j].getImage() == null){
                    chesses[i][j].setBelonging(Chess.CHESS_O);
                    if(ticService.isOWin(chesses)){
                        chesses[i][j].setBelonging(Chess.CHESS_NULL);
                        return new ClickPoint(i, j);
                    }else {
                        chesses[i][j].setBelonging(Chess.CHESS_NULL);
                    }
                }
            }
        }
        return null;
    }
}
