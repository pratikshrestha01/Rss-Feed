package com.boilerplate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.boilerplate.api.IAdminApi;
import com.boilerplate.api.ICustomerApi;
import com.boilerplate.api.IRssItemApi;
import com.boilerplate.api.IUserApi;
import com.boilerplate.dynamicJasper.ReportService;
import com.boilerplate.model.NbaRssItemDto;
import com.boilerplate.model.RpmRssItemDto;
import com.boilerplate.repositories.UserRepository;
import com.boilerplate.util.AuthenticationUtil;
import com.boilerplate.util.BugMail;
import com.dto.rssItem.RssItemDto;
import com.rss.parser.BbcCricketParser;
import com.rss.parser.EspnMlbParser;
import com.rss.parser.EspnNbaParser;
import com.rss.parser.EspnNflParser;
import com.rss.parser.EspnNhlParser;
import com.rss.parser.EspnRpmParser;
import com.rss.parser.FoxsportsSoccerParser;


@Controller
@Scope("session")

public class MainController implements MessageSourceAware {

	@Autowired
	private IAdminApi adminApi;

	@Autowired
	private ICustomerApi customerApi;

	

	
	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReportService reportService;

	@Autowired
	private BugMail bugMail;
	
	
	@Autowired
	private EspnNflParser espnNflParser;
	
	@Autowired
	private EspnNbaParser espnNbaParser;
	
	@Autowired
	private EspnMlbParser espnMlbParser;
	
	@Autowired
	private EspnRpmParser espnRpmParser;
	
	
	@Autowired
	private IRssItemApi rssItemApi;
	
	@Autowired
	private EspnNhlParser  espnNhlParser;
	
	@Autowired
	private BbcCricketParser bbcCricketParser;
	
	@Autowired
	private FoxsportsSoccerParser foxsportsSoccerParser;

	private MessageSource messageSource;
	private final RequestCache requestCache = new HttpSessionRequestCache();

	private Logger logger = LoggerFactory.getLogger(MainController.class);

	/*
	 * public MainController(ICustomerApi customerApi) { this.customerApi =
	 * customerApi; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getHome(ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage,
			@RequestParam(value = "infomessage", required = false) String infomessage,
			@RequestParam(value = "msg", required = false) String msg, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "pageNo", required = false) Integer page) {
		
	/*	modelMap.put("rssItems", rssItemApi.getBulkRssItem());
		
	List<RssItemDto> mlblist=espnMlbParser.EspnMlbParse();
		rssItemApi.saveBulkRssItem(mlblist);
			
		List<RssItemDto> nbalist=espnNbaParser.EspnNbaParse();
		rssItemApi.saveBulkRssItem(nbalist);
		
		
		List<RssItemDto> nfllist=espnNflParser.EspnNflParse();
		rssItemApi.saveBulkRssItem(nfllist);
	
		List<RssItemDto> nhllist=espnNhlParser.EspnNhlParse();
		rssItemApi.saveBulkRssItem(nhllist);
		
		List<RssItemDto> rpmlist=espnRpmParser.EspnRpmParse();
		rssItemApi.saveBulkRssItem(rpmlist);
	
		List<RssItemDto> bbcCricketList=bbcCricketParser.BbcCricketParse();
		rssItemApi.saveBulkRssItem(bbcCricketList);
*/	
		foxsportsSoccerParser.foxSportsSoccerParse();
		

			return "/Starter/main";


	}

	@RequestMapping(method = RequestMethod.GET, value = "/main")
	public String getMain(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage, HttpServletResponse response) {

		if (AuthenticationUtil.getCurrentUser() != null) {

			return "redirect:/";

		}

		return "/Starter/login";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listItems")
	public String listItems(HttpServletRequest request, ModelMap modelMap,
			@RequestParam(value = "errormessage", required = false) String errormessage,
			HttpServletResponse response) {
		
		return "/Starter/demo";
		
	}

		
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
