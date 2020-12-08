/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Pablo
 */
public class TrackAlbum {

    String albumTitle;
    int trackKop;

    public TrackAlbum(String albumTitle, int trackKop) {
        this.albumTitle = albumTitle;
        this.trackKop = trackKop;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public int getTrackKop() {
        return trackKop;
    }

    public void setTrackKop(int trackKop) {
        this.trackKop = trackKop;
    }

}
