package com.stackroute.newsapp.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.repositories.NewsContentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class NewsContentRepositoryTest {

	@Autowired
	private NewsContentRepository repository;

	public void setRepo(final NewsContentRepository repository) {
		this.repository = repository;
	}

	@Rollback(false)
	@Test
	public void testsaveNews() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertThat(newscontentObject.getId()).isEqualTo(1);
	}
	
	//negative scenario for handling the saving the new movie
	@Test
	public void testSaveNewsFailure() throws Exception{
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertFalse(newscontentObject.getId()==5);
	}

	@Rollback(false)
	@Test
	public void testUpdateNewsContent() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertEquals(newscontentObject.getId(), 1);
		newscontentObject.setDescription("Testing on updating the content of the news.");
		repository.save(newscontentObject);
		final NewsData tempnewscontentObject = repository.getOne(1);
		assertEquals("Testing on updating the content of the news.", tempnewscontentObject.getDescription());
	}

	//negative scenario while updating the news content
	@Test
	public void testUpdateNewsContentFailure() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData tempnewscontentObject = repository.getOne(1);
		String desc="Some retirement plan providers are working on new ways to make it easier for you to save.";
		assertFalse(tempnewscontentObject.getDescription().equals(desc));
	}
	@Test
	public void testDeleteNewsContent() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertEquals(newscontentObject.getTitle(),
				"This workplace perk could make it easier to save for emergencies - CNBC");
		repository.delete(newscontentObject);
		assertEquals(Optional.empty(), repository.findById(1));
	}
	//negative scenario for deleting the news content
	@Test
	public void testDeleteNewsContentFailure() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertEquals(newscontentObject.getTitle(),
				"This workplace perk could make it easier to save for emergencies - CNBC");
		repository.delete(newscontentObject);
		assertEquals(Optional.empty(), repository.findById(5));
	}
	@Test
	public void testGetNewsContent() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		assertEquals(newscontentObject.getTitle(),
				"This workplace perk could make it easier to save for emergencies - CNBC");
	}
	
	//negative scenario while getting the news content
	@Test
	public void testGetNewsContentFailure() throws Exception {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		final NewsData newscontentObject = repository.getOne(1);
		String newsContent="This workplace perk could make it easier";
		assertFalse(newscontentObject.getTitle().equals(newsContent));
	}

	@Rollback(false)
	@Test
	public void testGetByMovies() {
		repository.save(new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		repository.save(new NewsData(2, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		repository.save(new NewsData(3, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		repository.save(new NewsData(4, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin"));
		List<NewsData> newsContentObjLst = repository.findByUserId("amal123");
		assertEquals(newsContentObjLst.get(0).getTitle(),
				"This workplace perk could make it easier to save for emergencies - CNBC");
	}

}
