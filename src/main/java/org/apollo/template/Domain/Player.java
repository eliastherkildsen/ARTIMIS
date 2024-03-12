package org.apollo.template.Domain;

public class Player {

    private int Xpos, Ypos;
    private int health;
    private double Xvel, Yvel;
    private int xSize, ySize;

    public Player(int xpos, int ypos, int health, int xSize, int ySize) {
        Xpos = xpos;
        Ypos = ypos;
        this.health = health;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int xpos) {
        Xpos = xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int ypos) {
        Ypos = ypos;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getXvel() {
        return Xvel;
    }

    public void setXvel(double xvel) {
        Xvel = xvel;
    }

    public double getYvel() {
        return Yvel;
    }

    public void setYvel(double yvel) {
        Yvel = yvel;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }
}
