/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.model;

import java.util.Comparator;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author lopez.pablo
 */
public class Anime {

    String name;
    String description;
    double rating;
    int episodes;
    List<String> categories;
    Studio studio;
    String img;

    public Anime() {
    }

    public Anime(String name, String description, double rating, int episodes, List<String> categories, Studio studio, String img) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.episodes = episodes;
        this.categories = categories;
        this.studio = studio;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Anime{" + "name=" + name + ", description=" + description + ", rating=" + rating + ", episodes=" + episodes + ", categories=" + categories + ", studio=" + studio + ", img=" + img + '}';
    }

    public static String listToString(List<String> list) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        Formatter fmt = new Formatter(sb);
        if (list.size() % 2 == 0) {
            for (int i = 0; i < list.size(); i += 2) {
                fmt.format("\u2022 %-20s\t\u2022 %-20s\n", list.get(i), list.get(i + 1));
            }
        } else {
            for (int i = 0; i < (list.size() - 1); i += 2) {
                fmt.format("\u2022 %-20s\t\u2022 %-20s\n", list.get(i), list.get(i + 1));
            }
            fmt.format("\u2022 %-20s", list.get(list.size() - 1));
        }
        result = sb.toString();
        return result;
    }

    public static Comparator<Anime> AnimeNameComparatorAZ = new Comparator<Anime>() {
        @Override
        public int compare(Anime a1, Anime a2) {
            String animeName1 = a1.getName().toUpperCase();
            String animeName2 = a2.getName().toUpperCase();

            //ascending order
            return animeName1.compareTo(animeName2);
        }
    };

    public static Comparator<Anime> AnimeNameComparatorZA = new Comparator<Anime>() {
        @Override
        public int compare(Anime a1, Anime a2) {
            String animeName1 = a1.getName().toUpperCase();
            String animeName2 = a2.getName().toUpperCase();

            //descending order
            return animeName2.compareTo(animeName1);
        }
    };

    public static Comparator<Anime> AnimeRatingComparatorAsc = new Comparator<Anime>() {
        @Override
        public int compare(Anime a1, Anime a2) {

            double animeRating1 = a1.getRating();
            double animeRating2 = a2.getRating();

            /*For ascending order*/
            return Double.compare(animeRating1, animeRating2);
        }
    };

    public static Comparator<Anime> AnimeRatingComparatorDes = new Comparator<Anime>() {
        @Override
        public int compare(Anime a1, Anime a2) {

            double animeRating1 = a1.getRating();
            double animeRating2 = a2.getRating();

            /*For descending order*/
            return Double.compare(animeRating2, animeRating1);
        }
    };
}
