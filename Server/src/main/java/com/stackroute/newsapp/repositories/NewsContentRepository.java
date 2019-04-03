package com.stackroute.newsapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.newsapp.domain.NewsData;

public interface NewsContentRepository extends JpaRepository<NewsData, Integer> {
	List<NewsData> findByUserId(String userId);
}
