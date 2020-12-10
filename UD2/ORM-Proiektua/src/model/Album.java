/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Mikel
 */
public class Album {
    private int albumId;
    private String title = "";
    private int artistId;
    private Artista artist = new Artista();

    public Album() {}
        
    public Album(int albumId, String title, int artistId) {
        this.albumId = albumId;
        this.title = title;
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
    
    public Artista getArtist() {
        return artist;
    }
    
    public void setArtist(Artista artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" + "AlbumId=" + albumId + ", Title=" + title + ", ArtistId=" + artistId + '}';
    }
    
}
