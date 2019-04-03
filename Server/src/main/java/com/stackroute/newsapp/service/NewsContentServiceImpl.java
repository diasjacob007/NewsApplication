package com.stackroute.newsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.exceptions.NewsContentAlreadyExistsException;
import com.stackroute.newsapp.exceptions.NewsContentNotFoundException;
import com.stackroute.newsapp.repositories.NewsContentRepository;

@Service
public class NewsContentServiceImpl implements NewsContentServiceIntrf {

	private final NewsContentRepository newsContentRepository;// newsContentRepository declared

	@Autowired
	public NewsContentServiceImpl(final NewsContentRepository newsContentRepository) {
		super();
		this.newsContentRepository = newsContentRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stackroute.newsapp.service.NewsContentServiceIntrf#saveNewsContent(com.
	 * stackroute.newsapp.domain.NewsData) Method implementation for saving new news
	 * content to db_01.26.2019
	 */
	@Override
	public boolean saveNewsContent(NewsData newsdata) throws NewsContentAlreadyExistsException {
		final Optional<NewsData> newsContentObject = newsContentRepository
				.findById(newsdata.getId());
		if (newsContentObject.isPresent()) {
			throw new NewsContentAlreadyExistsException(
					"You are not able to save this news, as it is an already existing one");
		}
		newsContentRepository.save(newsdata);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stackroute.newsapp.service.NewsContentServiceIntrf#updateNewsContent(com.
	 * stackroute.newsapp.domain.NewsData) Method implementation for updating and
	 * existing News Content. If the corresponding news data object is not existing,
	 * then Exception will be thrown Otherwise only the updated new data object will
	 * get updated into the db_01.26.2019
	 */
	@Override
	public NewsData updateNewsContent(NewsData newsdataToUpdate) throws NewsContentNotFoundException {
		final NewsData newsDataObject = newsContentRepository.findById(newsdataToUpdate.getId()).orElse(null);
		if (newsDataObject == null) {
			throw new NewsContentNotFoundException(
					"This particular news content not exists.So not able to update it!!!");
		}
		newsContentRepository.save(newsdataToUpdate);
		return newsDataObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stackroute.newsapp.service.NewsContentServiceIntrf#deleteNewsContentById(
	 * int) Method implementation for deleting a particular news content based on
	 * the news id. If the id of that news is not existing then it will throw the
	 * exception, otherwise that particular news will get deleted as
	 * expected_01.26.2019
	 */
	@Override
	public boolean deleteNewsContentById(int id) throws NewsContentNotFoundException {
		final NewsData newsdataObject = newsContentRepository.findById(id).orElse(null);
		if (newsdataObject == null) {
			throw new NewsContentNotFoundException("No such news exists, so not able to delete!!!");
		}
		newsContentRepository.delete(newsdataObject);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stackroute.newsapp.service.NewsContentServiceIntrf#getNewsContentById(
	 * int) Method implementation for getting the content of the news based on the
	 * news id. If the passed id is an existing one, then its content will be
	 * returned as News object, otherwise Exception will be thrown as
	 * expected_01.26.2019.
	 */
	@Override
	public NewsData getNewsContentById(int id) throws NewsContentNotFoundException {
		final NewsData newsdataObject = newsContentRepository.findById(id).get();
		if (newsdataObject == null) {
			throw new NewsContentNotFoundException("News Content Not Found.Better time next time");
		}
		return newsdataObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.stackroute.newsapp.service.NewsContentServiceIntrf#getMyNews(java.lang.
	 * String) Method implementation for getting all the news data corresponding to
	 * the Loggined User_01.26.2019
	 */
	@Override
	public List<NewsData> getMyNews(String userId) {
		return newsContentRepository.findByUserId(userId);
	}

}
