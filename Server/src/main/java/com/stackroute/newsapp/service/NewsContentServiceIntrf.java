/**
 * 
 */
package com.stackroute.newsapp.service;

import java.util.List;

import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.exceptions.NewsContentAlreadyExistsException;
import com.stackroute.newsapp.exceptions.NewsContentNotFoundException;

/**
 * @author ubuntu
 *
 */
public interface NewsContentServiceIntrf {
	/*
	 * 01.26.2019 Declaring NewsApp related methods Method declaration for saving
	 * the News Content
	 */
	boolean saveNewsContent(NewsData newsdata) throws NewsContentAlreadyExistsException;

	/*
	 * Method for updating the News Content
	 */
	NewsData updateNewsContent(NewsData newsdata) throws NewsContentNotFoundException;

	/*
	 * Method for deleting one particular News Content based on its id
	 */
	boolean deleteNewsContentById(int id) throws NewsContentNotFoundException;

	/*
	 * Method for getting one particular News Content
	 */
	NewsData getNewsContentById(int id) throws NewsContentNotFoundException;

	/*
	 * Method for fetching all the news details that exists inside the 'newsdata'
	 * table
	 */
	List<NewsData> getMyNews(String userId);
}
