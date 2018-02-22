package com.davtyan.photoeditor;

import java.util.ArrayList;


public class ImageModel {

  private String strFolder;
  private ArrayList<String> alImagePath;

    public String getStrFolder() {
        return strFolder;
    }

    public void setStrFolder(String strFolder) {
        this.strFolder = strFolder;
    }

    public ArrayList<String> getAlImagePath() {
        return alImagePath;
    }

    public void setAlImagePath(ArrayList<String> alImagePath) {
        this.alImagePath = alImagePath;
    }
}
