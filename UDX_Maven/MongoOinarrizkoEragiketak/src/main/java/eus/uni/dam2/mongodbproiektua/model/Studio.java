/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.model;

/**
 *
 * @author lopez.pablo
 */
public class Studio {
    String name;
    int founded;
    String image;

    public Studio() {
    }

    public Studio(String name, int founded, String image) {
        this.name = name;
        this.founded = founded;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFounded() {
        return founded;
    }

    public void setFounded(int founded) {
        this.founded = founded;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Studio{" + "name=" + name + ", founded=" + founded + ", image=" + image + '}';
    }    
}
