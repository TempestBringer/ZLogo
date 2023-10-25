package club.tempestissimo.net.entities.attributes;

public class Position {
    private double xPosition;
    private double yPosition;
    private double degree;

    public Position copy(){
        return new Position(xPosition,yPosition,degree);
    }


    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    public static Position generateRandomPosition(double xLimit, double yLimit, double degreeLimit){
        return new Position(Math.random()*xLimit, Math.random()*yLimit, Math.random()*degreeLimit);
    }

    public Position(double xPosition, double yPosition, double degree) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.degree = degree;
    }
}
