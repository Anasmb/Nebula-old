package com.example.panels;

public class Panel {

    private int ID;
    private float xLocation;
    private float yLocation;
    private int realRotation;
    private int imageRotation;

    public Panel(int ID , float xLocation , float yLocation , int realRotation , int imageRotation){
        this.ID = ID;
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.imageRotation = imageRotation;
        this.realRotation = realRotation;

    }

    public int getID() {
        return ID;
    }

    public float getxLocation() {
        return xLocation;
    }

    public float getyLocation() {
        return yLocation;
    }

    public int getRealRotation(){
        return realRotation;
    }

    public int getImageRotation(){
        return imageRotation;
    }

    public String toString(){
        return "ID = " + ID + " , X Location = " + xLocation + " , Y Location = " + yLocation +
                " , Real Rotation = " + realRotation + " , Image Rotation = " + imageRotation;
    }


}
