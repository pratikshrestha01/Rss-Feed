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

import org.springframework.stereotype.Component;

import com.dto.rssItem.RssItemDto;
import com.dto.rssItem.SportCat;
import com.rss.foxSports.Soccer.ObjectFactory;
import com.rss.foxSports.Soccer.Rss;
import com.rss.foxSports.Soccer.Rss.Channel.Item;


@Component
public class FoxsportsSoccerParser {

	String  fromFile = "https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU&tag=soccer";
	String toFile = "xmls/foxSportsSoccer.xml";

	public List<RssItemDto> foxSportsSoccerParse() {
		try {
			URL website = new URL(fromFile);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(toFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			return foxSportSoccerUnmarshaller();
		}catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<RssItemDto>();
		}
	}
	
	public List<RssItemDto> foxSportSoccerUnmarshaller(){
		 try {
 			File file = new File(toFile);
 			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

 			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
 			Rss rss =   (Rss) jaxbUnmarshaller.unmarshal(file);
 			return parsher(rss);
 			
 		     }catch (JAXBException e) {
 		       	e.printStackTrace();
 			 return new ArrayList<RssItemDto>();
 		  }
	}
	
	public List<RssItemDto> parsher(Rss rss) {
		List<RssItemDto> rssList = new ArrayList<RssItemDto>();
		
		
		if(rss.getChannel()!=null) {
			for(Object rcii: rss.getChannel().getContent()) {
				Rss.Channel.Item rci=(Item) rcii;
				RssItemDto ri = new RssItemDto();
				ri.setTitle(rci.getTitle());
				ri.setDescription(rci.getDescription());				
				ri.setCatagory(SportCat.BaseBall);
				ri.setSource("ESPN");
				ri.setUrl(rci.getLink());
				ri.setSourceDate(rci.getPubDate());
				rssList.add(ri);
			}
		}
		return rssList;
	}
}



