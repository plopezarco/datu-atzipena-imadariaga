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
public class AlbumArtist {
    String artistName;
    int albumKop;

    public AlbumArtist(String artistName, int albumKop) {
        this.artistName = artistName;
        this.albumKop = albumKop;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getAlbumKop() {
        return albumKop;
    }

    public void setAlbumKop(int albumKop) {
        this.albumKop = albumKop;
    }
    
    
}
