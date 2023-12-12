
package com.example.androidphotos59.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.androidphotos59.utils.ToastMessage;

/**
 * This class represents the Album object.
 * <p>
 * An album will have a list of Photo. Each user will have a list of Album.
 *
 * @author Saman Sathenjeri
 * @author Rohan Deshpande
 * @version 1.0
 */
public class Album implements Serializable {
    /**
     * Name of the Album
     */
    private String albumName;

    /**
     * List of Photos in an album
     */
    private ArrayList<Photo> photos;

    /**
     * Constructor
     */
    public Album() {
        // no arg constructor
    }

    /**
     * Constructor
     *
     * @param albumName
     */
    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<>();
    }

    /**
     * Getter for list of photos in an album
     *
     * @return photos
     */
    public ArrayList<Photo> getPhotosInAlbum() {
        return this.photos;
    }

    /**
     * Setter for list of photos in an album
     *
     * @param photos
     */
    public void setPhotosInAlbum(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Helper to add photo to album
     *
     * @param photo
     */
    public boolean addPhoto(Photo photo) {
        if (!this.duplicatePhoto(photo)) {
            this.getPhotosInAlbum().add(photo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper to delete a photo to album
     *
     * @param photo
     */
    public boolean deletePhoto(Photo photo) {
        return this.getPhotosInAlbum().remove(photo);
    }

    public void foo(){

    }
    /**
     * Helper to return the number of photos in an album
     *
     * @return photos.size()
     */
    public int getNumOfPhotosInAlbum() {
        if (photos == null) {
            return 0;
        }
        return this.photos.size();
    }

    /**
     * Getter for album name
     *
     * @return albumName
     */
    public String getAlbumName() {
        return this.albumName != null ? this.albumName : "";
    }

    /**
     * Setter for albumName
     *
     * @param albumName
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }




    /**
     * Override of dateToString for dates
     *
     * @return date
     */
    private String dateToString(Date date) {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Checks if there are duplicate photos with the same filepath
     *
     * @param photo
     * @return
     */
    public boolean duplicatePhoto(Photo photo) {
        for (int y = 0; y < photos.size(); y++) {
            if (photos.get(y) != null && photos.get(y).getFile().compareTo(photo.getFile()) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets index of a photo
     *
     * @param photo
     * @return
     */
    public int getIndexOfPhoto(Photo photo) {
        for (int i = 0; i < this.getNumOfPhotosInAlbum(); i++) {
            if (this.getPhotosInAlbum().get(i).equals(photo)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns the next photo in the album
     *
     * @param photo
     * @return
     */
    public Photo getNextPhoto(Photo photo) {
        int index = getIndexOfPhoto(photo);
        index++;
        if (index < this.getNumOfPhotosInAlbum()) {
            return this.getPhotosInAlbum().get(index);
        }
        return null;
    }

    /**
     * returns the previous photo in the album
     *
     * @param photo
     * @return
     */
    public Photo getPreviousPhoto(Photo photo) {
        int index = getIndexOfPhoto(photo);
        index--;
        if (index >= 0) {
            return this.getPhotosInAlbum().get(index);
        }
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.albumName;
    }

    /**
     * Helper function for the tag query function.
     *
     * @param q
     * @return Tag if query is a valid tag or NULL.
     */
    public Tag parseTag(String q) {
        String[] tag = q.split("=", 2);
        if (tag.length != 2) {
            return null;
        }
        return new Tag(tag[0].trim(), tag[1].trim());
    }

    /**
     * Search for photos by tag type-value pairs. The following types of tag-based
     * searches should be implemented:
     * A single tag-value pair, e.g person=sesh
     * Conjunctive combination of two tag-value pairs, e.g. person=sesh AND
     * location=prague
     * Disjunctive combination of two tag-value pairs, e.g. person=sesh OR
     * location=prague
     * For conjunctions and disjunctions, if a tag can have multiple values for a
     * photo, it can appear on both arms of the conjunction/disjunction, e.g.
     * person=andre OR person=maya, person=andre AND person=maya
     *
     * @param query
     * @return Filtered Album
     */
    public Album searchByTag(String query) {
        Album returnAlbum = new Album();
        if (query.contains("AND") || query.contains("OR")) {
            String[] queries = query.contains("AND") ? query.split("AND") : query.split("OR");
            if (queries.length != 2) {
                return null;
            }
            Tag partOne = parseTag(queries[0]);
            if (partOne == null) {
                return null;
            }

            Tag partTwo = parseTag(queries[1]);
            if (partTwo == null) {
                return null;
            }

            for (int i = 0; i < photos.size(); i++) {
                ArrayList<Tag> tags = photos.get(i).getAllTags();
                if (query.contains("AND") && (tags.contains(partOne) && tags.contains(partTwo))) {
                    returnAlbum.addPhoto(photos.get(i));
                }
                if (query.contains("OR") && (tags.contains(partOne) || tags.contains(partTwo))) {
                    returnAlbum.addPhoto(photos.get(i));
                }
            }
        } else {
            Tag tagQuery = parseTag(query);
            if (tagQuery == null) {
                return null;
            }

            for (int i = 0; i < photos.size(); i++) {
                ArrayList<Tag> tags = photos.get(i).getAllTags();
                if (tags.contains(tagQuery)) {
                    returnAlbum.addPhoto(photos.get(i));
                }
            }
        }
        return returnAlbum;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Album)) {
            return false;
        }
        Album otherAlbum = (Album) o;
        return this.albumName.equals(otherAlbum.getAlbumName());
    }
}