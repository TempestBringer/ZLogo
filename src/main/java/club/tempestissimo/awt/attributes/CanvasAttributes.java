package club.tempestissimo.awt.attributes;

public class CanvasAttributes
{
    /**
     * 绘图画布大小
     */
    private int canvasX;
    private int canvasY;
    /**
     * 默认绘制节点大小
     */
    private int defaultDrawSize;

    public int getCanvasX() {
        return canvasX;
    }

    public void setCanvasX(int canvasX) {
        this.canvasX = canvasX;
    }

    public int getCanvasY() {
        return canvasY;
    }

    public void setCanvasY(int canvasY) {
        this.canvasY = canvasY;
    }

    public int getDefaultDrawSize() {
        return defaultDrawSize;
    }

    public void setDefaultDrawSize(int defaultDrawSize) {
        this.defaultDrawSize = defaultDrawSize;
    }

    public CanvasAttributes(int canvasX, int canvasY, int defaultDrawSize) {
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        this.defaultDrawSize = defaultDrawSize;
    }

    public CanvasAttributes copy(){
        return new CanvasAttributes(canvasX,canvasY,defaultDrawSize);
    }

}
