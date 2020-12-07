package model;

public class Genre {

	int genreId;
	String Name;

	public Genre() {
	}

	public Genre(String name) {
		Name = name;
	}

	public Genre(int genreId, String name) {
		this.genreId = genreId;
		Name = name;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "Genre [genreId=" + genreId + ", Name=" + Name + "]";
	}
}
