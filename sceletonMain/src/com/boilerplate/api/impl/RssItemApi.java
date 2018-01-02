package com.boilerplate.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.api.IRssItemApi;

import com.boilerplate.entity.Address;

import com.boilerplate.entity.RssItem;
import com.boilerplate.model.NbaRssItemDto;
import com.boilerplate.model.RpmRssItemDto;

import com.boilerplate.repositories.AddressRepository;

import com.boilerplate.repositories.RssItemRepository;
import com.boilerplate.util.ConvertUtil;
import com.dto.rssItem.RssItemDto;
import com.dto.rssItem.SportCat;

@Service
public class RssItemApi implements IRssItemApi {

	@Autowired
	private RssItemRepository rssItemRepository;

	@Override
	public void saveBulkRssItem(List<RssItemDto> rssItemList) {
		try {
			for (RssItemDto rto : rssItemList) {
				try {
					if (rto != null) {
						if (rto.getTitle() != null && rto.getDescription() != null) {
							List<RssItem> rss = new ArrayList<RssItem>();
							String desc=StringUtils.substring("abc", 0, 35);
							rss = rssItemRepository.findByNameAndDesc(rto.getTitle(), desc);
							if (rss.size() == 0) {
								RssItem rt = new RssItem();
								if (rto.getTitle() != null) {
									rt.setTitle(rto.getTitle());
								}
								if (rto.getDescription() != null) {
									rt.setDescription(rto.getDescription());
								}
								if (rto.getCatagory() != null) {
									rt.setCatagory(rto.getCatagory());
								}
								if (rto.getImageUrl() != null) {
									rt.setImageUrl(rto.getImageUrl());
								}
								if (rto.getUrl() != null) {
									rt.setUrl(rto.getUrl());
								}
								if (rto.getSource() != null) {
									rt.setSource(rto.getSource());
								}
								if (rto.getSourceDate() != null) {
									rt.setSourceDate(rto.getSourceDate());
								}
								rssItemRepository.save(rt);
							}

						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<RssItemDto> getBulkRssItem() {
		List<RssItem> rssItemList = ConvertUtil.convertIterableToList(rssItemRepository.findAll());
		return ConvertUtil.convertRssItemToDTO(rssItemList);
	}

}
