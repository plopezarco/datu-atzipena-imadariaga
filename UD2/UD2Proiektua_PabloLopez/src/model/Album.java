package model;

public class Album {

    int albumId;
    String title;
    String artistName;

    public Album() {

    }

    public Album(String title, String artistName) {
        this.title = title;
        this.artistName = artistName;
    }

    public Album(int albumId, String title, String artistName) {
        this.albumId = albumId;
        this.title = title;
        this.artistName = artistName;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Album [albumId=" + albumId + ", title=" + title + ", artistName=" + artistName + "]";
    }

}
