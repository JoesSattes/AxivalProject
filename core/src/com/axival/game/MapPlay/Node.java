package com.axival.game.MapPlay;

import com.badlogic.gdx.math.Vector2;

public class Node extends Vector2 {
    public int row;
    public int col;
    public int corX;
    public int corY;
    public int parentX;
    public int parentY;
    public int level=0;
    private int obstacle;
    private boolean visit=false;
    public Node(int row, int col, int corX, int corY, int obstacle) {
        this.row = row;
        this.col = col;
        this.corX = corX;
        this.corY = corY;
        this.obstacle = obstacle;
    }

    public Vector2 getCoordinatiion() {
        return new Vector2(corX, corY);
    }
    public Vector2 getRowCol() {
        return new Vector2(col, row);
    }

    public void setParent(int parentX, int parentY) {
        this.parentX = parentX;
        this.parentY = parentY;
    }

    public Vector2 getParent() {
        return new Vector2(this.parentX, this.parentY);
    }

    public void setObstacle(int obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isObstacle() {
        if (obstacle == 1 || obstacle == 2) {
            return true;
        }
        return false;
    }

    public boolean isObstacle(boolean bool) {
        if (obstacle == 1) {
            return true;
        }
        return false;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public boolean isVisit() { return visit; }

    public void  setLevel(int level){
        this.level = level;
    }
}