package com.stackroute.newsapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.exceptions.NewsContentAlreadyExistsException;
import com.stackroute.newsapp.exceptions.NewsContentNotFoundException;
import com.stackroute.newsapp.repositories.NewsContentRepository;
public class NewsContentServiceImplTest {
	@Mock
	private NewsContentRepository repository;
	private NewsData newsData;
	@InjectMocks
	private NewsContentServiceImpl newscontentServiceImpl;
	Optional<NewsData> newsDataOptions;

	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
		newsData = new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin");
		newsDataOptions = Optional.of(newsData);
	}

	@Test
	public void testMockCreation() {
		assertNotNull("jpa repository creation fails: use @injectMocks on newscontentServiceImpl", repository);
	}
	
	@Test
	public void testSaveMovieSuccess() throws NewsContentAlreadyExistsException {
		when(repository.save(newsData)).thenReturn(newsData);
		final boolean flag = newscontentServiceImpl.saveNewsContent(newsData);
		assertTrue("saving newscontent failed , the call to newsContentDAOImpl is returning false , check this method", flag);
		verify(repository, times(1)).save(newsData);
		verify(repository, times(1)).findById(newsData.getId());
	}

	@Test(expected = NewsContentAlreadyExistsException.class)
	public void testSaveNewsContentFailure() throws NewsContentAlreadyExistsException {
		when(repository.findById(1)).thenReturn(newsDataOptions);
		when(repository.save(newsData)).thenReturn(newsData);
		final boolean flag = newscontentServiceImpl.saveNewsContent(newsData);
		assertFalse("Saving NewsContent failed", flag);
		verify(repository, times(1)).findById(newsData.getId());
	}

	@Test
	public void testUpdateNewsContent() throws NewsContentNotFoundException {
		when(repository.findById(1)).thenReturn(newsDataOptions);
		when(repository.save(newsData)).thenReturn(newsData);
		newsData.setDescription("This workplace perk could make it not easier to save for emergencies - CNBC");
		final NewsData newsData1 = newscontentServiceImpl.updateNewsContent(newsData);
		assertEquals("Update newscontent failed", "This workplace perk could make it not easier to save for emergencies - CNBC", 
				newsData1.getDescription());
		verify(repository, times(1)).save(newsData);
		verify(repository, times(1)).findById(newsData.getId());
	}

	@Test
	public void testDeleteNewsContent() throws NewsContentNotFoundException {
		when(repository.findById(1)).thenReturn(newsDataOptions);
		doNothing().when(repository).delete(newsData);
		final boolean flag = newscontentServiceImpl.deleteNewsContentById(newsData.getId());
		assertTrue("Deletion of News Content failed", flag);
		verify(repository, times(1)).delete(newsData);
		verify(repository, times(1)).findById(newsData.getId());
	}

	@Test
	public void testGetNewsContentById() throws NewsContentNotFoundException {
		when(repository.findById(1)).thenReturn(newsDataOptions);
		final NewsData newsdata1 = newscontentServiceImpl.getNewsContentById(1);
		assertEquals("fetching news content by id failed", newsdata1, newsData);
		verify(repository, times(1)).findById(newsData.getId());
	}

	@Test
	public void testGetMyNewsContent() {
		final List<NewsData> newsContentLst = new ArrayList<>(1);
		newsContentLst.add(newsData);
		when(repository.findByUserId("amal123")).thenReturn(newsContentLst);
		List<NewsData> myNewsContentLst = newscontentServiceImpl.getMyNews("amal123");
		assertEquals(newsContentLst, myNewsContentLst);
		verify(repository, times(1)).findByUserId("amal123");

	}
}
