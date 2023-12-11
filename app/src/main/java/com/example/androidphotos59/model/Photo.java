package com.example.androidphotos59.model;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Photo class represents a Photo and its metadata (tags, caption, name etc)
 * 
 * @author Rohan Deshpande, Saman Sathenjeri
 * @version 1.0
 */
public class Photo implements Serializable {
	/**
	 * Photo reference
	 */
	public String photoName;

	/**
	 * Caption of the Photo
	 */
	public String caption;

	/**
	 * List of Tags on a photo
	 */
	public ArrayList<Tag> tags;

	/**
	 * File of the picture
	 */
	public File photoFile;

	/**
	 * Calendar instance for date
	 */
	public Calendar calendar = new GregorianCalendar();

	/**
	 * Current date
	 */
	public Date date;

	/**
	 * Constructor
	 */
	public Photo(User photoUser, File photoFile) {
		if (photoFile == null) {
			// this.photoFile = new File();
		} else {
			this.photoFile = photoFile;
		}

		this.tags = new ArrayList<Tag>();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		this.date = calendar.getTime();
	}

	/**
	 * Getter for caption
	 * 
	 * @return userName
	 */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * Setter for caption
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
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
	 * Getter for date
	 * 
	 * @return date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Getter for Photo Name
	 * 
	 * @return name of photo
	 */
	public String getName() {
		return photoName;
	}

	/**
	 * Setter for Photo Name
	 * 
	 * @param photoName
	 */
	public void setName(String photoName) {
		this.photoName = photoName;
	}

	/**
	 * Getter for Photo File
	 * 
	 * @return file of Photo
	 */
	public File getFile() {
		return photoFile;
	}

	/**
	 * Setter for Photo Name
	 * 
	 * @param file
	 */
	public void setFile(File file) {
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

	/**
	 * Date printed a string formatted as Month, day and year
	 * 
	 * @param date
	 * @return date in formatted as a valid string.
	 */
	public String dateToString(Date date) {
		if (date != null) {
			String pattern = "MM/dd/yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			String todayAsString = df.format(date);
			return todayAsString;
		} else {
			return "";
		}
	}
}