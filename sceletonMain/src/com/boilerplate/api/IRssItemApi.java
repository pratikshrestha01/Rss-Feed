package com.boilerplate.api;

import java.util.List;



import com.boilerplate.entity.RssItem;
import com.boilerplate.model.NbaRssItemDto;
import com.boilerplate.model.RpmRssItemDto;
import com.dto.rssItem.RssItemDto;

import com.boilerplate.entity.Admin;


import com.boilerplate.entity.RssItem;
import com.boilerplate.model.AdminDTO;
import com.boilerplate.model.PagablePage;
import com.boilerplate.model.RpmRssItemDto;

import com.boilerplate.util.ClientException;


public interface IRssItemApi {

	void saveBulkRssItem(List<RssItemDto> rssItemList);
	
	List<RssItemDto> getBulkRssItem();
  
		
	
	}
