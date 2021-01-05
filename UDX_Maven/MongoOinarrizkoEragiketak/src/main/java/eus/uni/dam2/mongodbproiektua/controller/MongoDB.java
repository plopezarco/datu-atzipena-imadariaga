/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.uni.dam2.mongodbproiektua.controller;

import com.mongodb.DBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoQueryException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import eus.uni.dam2.mongodbproiektua.model.Anime;
import eus.uni.dam2.mongodbproiektua.global.Global;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author Pablo
 */
public class MongoDB {

    private static ArrayList<Anime> animes = new ArrayList<Anime>();
    private static MongoClient mongoClient = MongoClients.create();
    private static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static MongoDatabase database = mongoClient.getDatabase(Global.DATUBASEA).withCodecRegistry(pojoCodecRegistry);
    private static MongoCollection<Anime> collectionAnimes = database.getCollection("animes", Anime.class);
    private static MongoCollection<Document> collectionUsers = database.getCollection("users");

    public static ArrayList<Anime> getAnimeArrayList() {
        Consumer<Anime> printBlock = new Consumer<Anime>() {
            @Override
            public void accept(final Anime anime) {
                animes.add(anime);
            }
        };
        collectionAnimes.find().forEach(printBlock);
        Collections.sort(animes, Anime.AnimeRatingComparator);
        return animes;
    }

    public static boolean insertAnime(Anime a) {
        try {
            collectionAnimes.insertOne(a);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteAnime(Anime a) {
        try {
            Document query = new Document("name", new Document("$eq", a.getName()));
            collectionAnimes.deleteOne(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean updateAnime(Anime a) {
        try {
            Document query = new Document("name", new Document("$eq", a.getName()));
            Document updateObject = new Document("$set", a);
            collectionAnimes.updateOne(query, updateObject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean insertUser(String user, String password) {
        try {
            Document doc = new Document("user", user).append("password", password);
            collectionUsers.insertOne(doc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkUser(String user, String password) {
        try {
            Document d = collectionUsers.find(and(eq("user", user), eq("password", password))).first();
            return d != null;
        } catch (Exception e) {
            return false;
        }
    }
}
