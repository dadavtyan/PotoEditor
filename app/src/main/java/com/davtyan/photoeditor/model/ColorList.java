package com.davtyan.photoeditor.model;


import java.util.ArrayList;
import java.util.List;

public class ColorList {
   public List<float[]> colorList = new ArrayList<>();

    public ColorList() {
        colorList.add(red);
        colorList.add(green);
        colorList.add(white);
        colorList.add(brownLite);
        colorList.add(brown);
        for (int i = 0; i < 10; i++) {
            colorList.add(brown);
        }
    }
    private float[] red = new float[]{
            1, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0};

   private float[] green = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 1, 0};

   private float[] white = new float[]{
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 0.3f, 0};

   private float[] brownLite = new float[]{
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0.3f, 0.59f, 0.11f, 0, 0,
            0, 0, 0, 1, 0,};

   private float[] brown = new float[]{
            -1, 0, 0, 0, 255,
            0, -1, 0, 0, 255,
            0, 0, -1, 0, 255,
            0, 0, 0, 1, 0,};

}
