package com.company;

public class RootNode implements Cloneable {

    int data;
    int level = 0;
    RootNode left, right, parent;
    boolean rotateOpr = true;
    boolean ignoreRot = false;
    double binaryOpr = 0;

    int leftInt;
    int rightInt;


    RootNode(int data)
    {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    protected Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
