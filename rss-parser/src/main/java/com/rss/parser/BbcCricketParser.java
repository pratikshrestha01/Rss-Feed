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
import com.rss.espn.nhl.ObjectFactory;
import com.rss.espn.nhl.Rss;

@Component
public class BbcCricketParser {

	String  fromFile = "https://www.foxsports.com.au/content-feeds/cricket/";
	String toFile = "xmls/bbcCricket.xml";

	public List<RssItemDto> BbcCricketParse() {
		try {

			URL website = new URL(fromFile);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(toFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			return bbcCricketUnmarsheller();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<RssItemDto>();
		}
	}
	
	public List<RssItemDto> bbcCricketUnmarsheller(){
		 try {

 			File file = new File("xmls/bbcCricket.xml");
 			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

 			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
 			Rss rss =   (Rss) jaxbUnmarshaller.unmarshal(file);
 			return parsher(rss);

 		  } catch (JAXBException e) {
 			e.printStackTrace();
 			return new ArrayList<RssItemDto>();
 		  }
	}
	
	public List<RssItemDto> parsher(Rss rss) {
		List<RssItemDto> rssList = new ArrayList<RssItemDto>();
		
		
		if(rss.getChannel()!=null) {
			for(Rss.Channel.Item rci: rss.getChannel().getItem()) {
				RssItemDto ri = new RssItemDto();
				ri.setTitle(rci.getTitle());
				ri.setDescription(rci.getDescription());
				//ri.setImageUrl(rss.getChannel().getImage().getUrl());
				ri.setCatagory(SportCat.Cricket);
				ri.setSource("BBC");
				ri.setUrl(rci.getLink());
				//ri.setSourceDate(rci.getLa());
				rssList.add(ri);
			}
		}
		
		return rssList;
	}

}