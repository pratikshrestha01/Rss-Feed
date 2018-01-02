package com.rss.parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dto.rssItem.RssItemDto;
import com.dto.rssItem.SportCat;
import com.rss.espn.nba.ObjectFactory;
import com.rss.espn.nba.Rss;

@Component
public class EspnNbaParser {

	String fromFile = "http://www.espn.com/espn/rss/nba/news";
	String toFile = "xmls/espnnba.xml";

	public List<RssItemDto> EspnNbaParse() {
		try {

			URL website = new URL(fromFile);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(toFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			return  espnNbaUnmarshaller();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<RssItemDto>();
		}
	}

	public List<RssItemDto> espnNbaUnmarshaller() {
		try {

			File file = new File("xmls/espnnba.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Rss rss = (Rss) jaxbUnmarshaller.unmarshal(file);
			return parsher(rss);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return new ArrayList<RssItemDto>();
		}
	}

	public List<RssItemDto> parsher(Rss rss) {

		List<RssItemDto> nbarssList = new ArrayList<RssItemDto>();
		if (rss.getChannel() != null) {
			for (Rss.Channel.Item rci : rss.getChannel().getItem()) {
				RssItemDto nri = new RssItemDto();
				nri.setTitle(rci.getTitle());
				nri.setDescription(rci.getDescription());
				nri.setImageUrl(rss.getChannel().getImage().getUrl());
				nri.setCatagory(SportCat.Basketball);
				nri.setSource("ESPN");
				nri.setUrl(rci.getLink());
				nri.setSourceDate(rci.getPubDate());
				nbarssList.add(nri);
			}
		}
		return nbarssList;

	}
}

