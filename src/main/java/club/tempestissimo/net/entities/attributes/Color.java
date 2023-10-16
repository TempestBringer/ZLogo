package club.tempestissimo.net.entities.attributes;

public class Color {
    private int r;
    private int g;
    private int b;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public static Color generateRandomColor(int rLimit, int gLimit, int bLimit){
        return new Color((int)(Math.random()*rLimit), (int)(Math.random()*gLimit), (int)(Math.random()*bLimit));

    }
    public java.awt.Color toAWTColor(){
        return new java.awt.Color(r,g,b);
    }

    public Color() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }
}
