package com.example.androidphotos59.model;

import java.io.Serializable;
import java.util.*;

/**
 * The Photo class represents a Photo and its metadata (tags, caption, name etc)
 *
 * @author Rohan Deshpande, Saman Sathenjeri
 * @version 1.0
 */
public class Photo implements Serializable {

	/**
	 * List of Tags on a photo
	 */
	public ArrayList<Tag> tags;

	/**
	 * File of the picture
	 */
	public String photoFile;

	/**
	 * Constructor
	 */
	public Photo(String photoFile) {
		this.photoFile = photoFile;
		this.tags = new ArrayList<Tag>();
	}


	/**
	 * Getter for tags
	 *
	 * @return tags
	 */
	public ArrayList<Tag> getAllTags() {
		return this.tags;
	}

	/**
	 * Setter for tags
	 *
	 * @param tags
	 */
	public void setAllTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}



	/**
	 * Getter for Photo File
	 *
	 * @return file of Photo
	 */
	public String getFile() {
		return photoFile;
	}

	/**
	 * Setter for Photo Name
	 *
	 * @param file
	 */
	public void setFile(String file) {
		this.photoFile = file;
	}

	/**
	 * Helper to add tags to a photos tag list
	 *
	 * @param tag
	 */
	public void addTag(Tag tag) {
		this.getAllTags().add(tag);
	}

	/**
	 * Helper to remove tags to a photos tag list
	 *
	 * @param tag
	 */
	public void removeTag(Tag tag) {
		this.getAllTags().remove(tag);
	}

	/**
	 * Helper to find tag
	 *
	 * @param tag
	 * @return tag
	 */
	public Tag findTag(Tag tag) {
		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).getTagName().equalsIgnoreCase(tag.getTagName()) &&
					tags.get(i).getTagData().equalsIgnoreCase(tag.getTagData())) {
				return tags.get(i);
			}
		}
		return null;
	}

	/**
	 * @return Tags in string format
	 */
	public String setTagsToString() {
		String totalString = "";
		for (int i = 0; i < tags.size(); i++) {
			totalString = totalString + tags.get(i).tagAsString() + ", ";
		}
		return totalString;
	}
	@Override
	public String toString(){
		return this.getFile();
	}
}