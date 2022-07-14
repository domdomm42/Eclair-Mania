package dungeonmania.Entities;

public class Position {
    private int xCoordinate, yCoordinate, layer;

    public Position(int xCoordinate, int yCoordinate, int layer) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.layer = layer;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    
}
