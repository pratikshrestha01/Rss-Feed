package com.boilerplate.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class BugMail {

	private static JavaMailSender mailSender;

	@Autowired
	public BugMail(JavaMailSender mailSender) {
		BugMail.mailSender = mailSender;
	}

	public static synchronized void Bugmail(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			String byuser = "";
			if (AuthenticationUtil.getCurrentUser() != null) {
				byuser = "<h3>By User : " + AuthenticationUtil.getCurrentUser().getUsername() + "</h3>";
			}
			final String by = byuser;
			new Thread() {
				public void run() {
					try {
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setFrom("testmailtestml@gmail.com");
						helper.setTo("paytimebug@gmail.com");
						helper.setCc("roshankhm@hotmail.com");
						helper.setSubject("PayTime Bug Reporting " + new Date().toString());
						helper.setText("text/html; charset=utf-8", by + sStackTrace);
						mailSender.send(message);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static synchronized void MerchantReport(HashMap<String, String> hashRequest,
			HashMap<String, String> hashresponse) {

		try {
			String sStackTrace = "";
			String UserName = AuthenticationUtil.getCurrentUser().getUsername();
			try {

				sStackTrace = "<h3>" + AuthenticationUtil.getCurrentUser().getId() + " : "
						+ AuthenticationUtil.getCurrentUser().getUsername() + "</h3>";

				sStackTrace = sStackTrace + "<h4>Service Request:</h4>";
				Iterator request = hashRequest.entrySet().iterator();
				while (request.hasNext()) {
					Map.Entry pair = (Map.Entry) request.next();
					sStackTrace = sStackTrace + "<p>" + pair.getKey() + " = " + pair.getValue() + "</p>";
					/*request.remove();*/ // avoids a
										// ConcurrentModificationException
				}
				sStackTrace = sStackTrace + "<h4>Service Response:</h4>";
				Iterator it = hashresponse.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					sStackTrace = sStackTrace + "<p>" + pair.getKey() + " = " + pair.getValue() + "</p>";
					/*it.remove();*/ // avoids a ConcurrentModificationException
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			final String pass = sStackTrace;

			new Thread() {
				public void run() {
					try {

						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setFrom("testmailtestml@gmail.com");
						helper.setTo("paytimebug@gmail.com");
						helper.setCc("roshankhm@hotmail.com");
						helper.setSubject("Merchant Entry " + new Date().toString() + "by: " + UserName);
						helper.setText("text/html; charset=utf-8", pass);
						mailSender.send(message);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
