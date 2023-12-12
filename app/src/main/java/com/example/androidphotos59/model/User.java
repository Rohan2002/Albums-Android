package com.example.androidphotos59.model;

import android.content.Context;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * This class will store the user schema.
 * This password is currently stored in plain-text format. One future improvement
 * will be to add some encryption to the password string.
 *
 * @author Rohan Deshpande, Saman Sathenjeri
 * @version 1.0
 */

public class User implements Serializable {
    private ArrayList<Album> albums;
    private Album activeAlbum;
    private File userDataDirectory;

    /**
     * The user object needs the username and password.
     * The userDirectory is later created via the initUser routine.
     * The userDirectory will contain all the user's app data.
     *
     */
    public User(File userDataDirectory) {
        this.userDataDirectory = userDataDirectory;
        this.albums = new ArrayList<>();
        this.activeAlbum = null;
        getUserFromDisk();
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public File getUserDataDirectory() {
        return userDataDirectory;
    }

    public void setUserDataDirectory(File userDataDirectory) {
        this.userDataDirectory = userDataDirectory;
    }

    /**
     * Getter for activeAlbum
     *
     * @return active album state
     */
    public Album getActiveAlbum() {
        return this.activeAlbum;
    }

    /**
     * Setter for the activeAlbum
     *
     * @param setAlbum
     */
    public void setActiveAlbum(Album setAlbum) {
        this.activeAlbum = setAlbum;
        saveUserToDisk(this);
    }

    /*
     * Getter for List of Albums for an User
     *
     * @return albums
     */
    public ArrayList<Album> getAlbumsList() {
        return this.albums;
    }

    /**
     * Setter for List of Albums for an User
     *
     * @param albums
     */
    public void setAlbumsList(ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * Finds duplicate Array Names
     *
     * @param albumName
     */
    public boolean duplicateAlbumName(String albumName) {
        for (int y = 0; y < albums.size(); y++) {
            if (albums.get(y) != null && albums.get(y).getAlbumName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }

    public boolean saveUserToDisk(User u){
        File userFile = new File(this.userDataDirectory, "user.dat");
        if(!userFile.exists()){
            try {
                boolean createdFile = userFile.createNewFile();
                if(!createdFile){
                    throw new IOException("Could not create file for some reason.");
                }
            }catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile.getPath()))) {
            oos.writeObject(u);
            System.out.println("Wrote user to disk.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getUserFromDisk() {
        File userFile = new File(this.userDataDirectory, "user.dat");
        if(!userFile.exists()){
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFile.getPath()))) {
            System.out.println("Read user from disk.");
            User userObjectFromDisk = (User) ois.readObject();;
            this.albums = userObjectFromDisk.getAlbums();
            this.userDataDirectory = userObjectFromDisk.getUserDataDirectory();
            this.activeAlbum = userObjectFromDisk.getActiveAlbum();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper to update a photo from the list of albums
     *
     * @param album
     */
    public boolean addPhoto(Photo newPhoto) {
        getUserFromDisk();
        if(!this.activeAlbum.addPhoto(newPhoto)){
            return false;
        }
        int indexOfActiveAlbum = this.albums.indexOf(this.activeAlbum);

        if (indexOfActiveAlbum == -1){
            return false;
        }
        else{
            this.albums.set(indexOfActiveAlbum, this.activeAlbum);
        }
        return saveUserToDisk(this);
    }

    /**
     * Helper to update an album from the list of albums
     *
     * @param album
     */
    public boolean updateUserAlbum(Album oldAlbum, Album newAlbum){
        getUserFromDisk();
        int oldAlbumIndex = this.albums.indexOf(oldAlbum);
        if(oldAlbumIndex == -1){
            return false;
        }
        else {
            this.albums.set(oldAlbumIndex, newAlbum);
        }
        return saveUserToDisk(this);
    }

    /**
     * Helper to add an album from the list of albums
     *
     * @param album
     */
    public boolean addAlbum(Album album){
        getUserFromDisk();
        if(this.albums.contains(album)){
            return false;
        }
        if(!this.albums.add(album)){
            return false;
        }
        return saveUserToDisk(this);
    }
    /**
     * Helper to delete an album from the list of albums
     *
     * @param album
     */
    public boolean deleteAlbum(Album album){
        getUserFromDisk();
        if(!this.albums.remove(album)){
            return false;
        }
        return saveUserToDisk(this);
    }
}
