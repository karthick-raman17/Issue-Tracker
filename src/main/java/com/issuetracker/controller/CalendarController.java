package com.issuetracker.controller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.UUID;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.issuetracker.model.ContactPojo;
import com.issuetracker.model.GooglePojo;
import com.issuetracker.model.TicketPojo;

@SuppressWarnings("unused")
@Controller
public class CalendarController {
	private final Logger logger = Logger.getLogger(CalendarController.class.getName());

	@RequestMapping(value = "/oauth2callback", method = { RequestMethod.GET })
	protected ModelAndView googleOAuth(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String code = request.getParameter("code");
			// https://issuetracker-2018.appspot.com/oauth2callback
			// http://localhost:8080/oauth2callback

			String urlParameters = "code=" + code
					+ "&client_id=359756818041-56bf33qea3ttu8o01ldd0asl725f053a.apps.googleusercontent.com"
					+ "&client_secret=CX-zKxdzfSU4ogrLzajcjWee" + "&redirect_uri=https://issuetracker-2018.appspot.com/oauth2callback"
					+ "&grant_type=authorization_code";

			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(urlConn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();

			String line, outputString = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}

			// Access Token
			JsonObject json = (JsonObject) new JsonParser().parse(outputString);
			String access_token = json.get("access_token").getAsString();
			System.out.println("JSON " + json);

			url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + access_token);
			urlConn = url.openConnection();
			outputString = "";
			reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while ((line = reader.readLine()) != null) {
				outputString += line;
			}

			GooglePojo data = new Gson().fromJson(outputString, GooglePojo.class);
			Query userQuery = new Query("Contacts").addFilter("loginId", FilterOperator.EQUAL, data.getEmail());

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			PreparedQuery pq = datastore.prepare(userQuery);

			List<Entity> isNewUser = null;
			isNewUser = pq.asList(FetchOptions.Builder.withLimit(1));
			HttpSession session = request.getSession();

			if (isNewUser.isEmpty()) {

				Key todoKey = KeyFactory.createKey("Contacts", data.getId());
				Entity customerEntity = new Entity(todoKey);

				customerEntity.setProperty("uuid", generateString());
				customerEntity.setProperty("loginId", data.getEmail());
				customerEntity.setProperty("firstName", data.getGiven_name());
				customerEntity.setProperty("lastName", data.getFamily_name());
				customerEntity.setProperty("picture", data.getPicture());
				customerEntity.setProperty("loginType", "google");

				session.setAttribute("user", data.getGiven_name());
				session.setAttribute("email", data.getEmail());
				session.setAttribute("picture", data.getPicture());
				session.setMaxInactiveInterval(30 * 60);

				Cookie userName = new Cookie("data", data.getId());
				userName.setMaxAge(30 * 60);
				response.addCookie(userName);

				datastore.put(customerEntity);
			} else {
				Entity customerUpdate = pq.asSingleEntity();

				customerUpdate.setProperty("loginId", data.getEmail());
				customerUpdate.setProperty("firstName", data.getGiven_name());
				customerUpdate.setProperty("lastName", data.getFamily_name());
				customerUpdate.setProperty("picture", data.getPicture());
				customerUpdate.setProperty("loginType", "google");

				session.setAttribute("user", data.getGiven_name());
				session.setAttribute("email", data.getEmail());
				session.setAttribute("picture", data.getPicture());
				session.setMaxInactiveInterval(30 * 60);

				Cookie userName = new Cookie("data", data.getId());
				userName.setMaxAge(30 * 60);
				response.addCookie(userName);

				datastore.put(customerUpdate);

				System.out.println("Customer profile updated");
			}
			writer.close();
			reader.close();

		} catch (MalformedURLException e) {
			System.out.println(e);
		} catch (ProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		return new ModelAndView("redirect:home");

	}

	@RequestMapping(value = "/createticket", method = { RequestMethod.POST }, consumes = "text/plain")
	protected void createTicket(@RequestBody String postData, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		TicketPojo ticketData = new Gson().fromJson(postData, TicketPojo.class);

		try {
			Entity ticket = new Entity("Tickets");

			ticket.setProperty("ticketID", generateString());
			ticket.setProperty("title", ticketData.getTitle());
			ticket.setProperty("start", ticketData.getStart());
			ticket.setProperty("description", ticketData.getDescription());
			ticket.setProperty("allDay", ticketData.isAllDay());
			ticket.setProperty("assignedTo", ticketData.getAssignedTo());
			ticket.setProperty("severity", ticketData.getSeverity());
			ticket.setProperty("dueDate", ticketData.getDueDate());
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(ticket);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		CalendarController controller = new CalendarController();
		try {
			controller.sendEmail(postData, request, response);
		} catch (ResourceNotFoundException e) {
			
			e.printStackTrace();
		} catch (ParseErrorException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.print(postData.toString());

	}

	@RequestMapping(value = "/alltickets", method = { RequestMethod.GET })
	protected void allTickets(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		Query ticketQuery = new Query("Tickets");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(ticketQuery);

		List<Entity> results = null;
		PrintWriter out = response.getWriter();
		try {
			results = pq.asList(FetchOptions.Builder.withDefaults());

			if (!results.isEmpty()) {

				List<String> alltickets = new ArrayList<String>();

				ListIterator<Entity> itr = results.listIterator();

				TicketPojo retrieveTicket = new TicketPojo();

				Gson g = new Gson();

				String jsonString = null;

				for (int i = 0; i < results.size(); i++) {

					retrieveTicket.setTicketID((String) results.get(i).getProperty("ticketID"));
					retrieveTicket.setTitle((String) results.get(i).getProperty("title"));
					retrieveTicket.setStart((String) results.get(i).getProperty("start"));
					retrieveTicket.setDescription((String) results.get(i).getProperty("description"));
					retrieveTicket.setAssignedTo((String) results.get(i).getProperty("assignedTo"));
					retrieveTicket.setAllDay((boolean) results.get(i).getProperty("allDay"));
					retrieveTicket.setDueDate((String) results.get(i).getProperty("dueDate"));
					retrieveTicket.setSeverity((String) results.get(i).getProperty("severity"));
					jsonString = g.toJson(retrieveTicket);
					alltickets.add(jsonString);

				}
				out.print(alltickets);
			} else {
				out.print("{\"error\":\"No data\"}");
				;
			}
		} catch (Exception e) {
			System.out.println("Exception occured while fetching the tickets " + e.getStackTrace());
		}

	}

	@RequestMapping(value = "/allusers", method = { RequestMethod.GET })
	protected void allUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		Query userQuery = new Query("Contacts");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(userQuery);

		List<Entity> results = null;
		PrintWriter out = response.getWriter();

		try {
			results = pq.asList(FetchOptions.Builder.withDefaults());
			if (!results.isEmpty()) {

				List<String> allusers = new ArrayList<String>();

				ListIterator<Entity> itr = results.listIterator();

				ContactPojo retrieveAllUsers = new ContactPojo();

				Gson g = new Gson();

				String jsonString = null;

				for (int i = 0; i < results.size(); i++) {

					retrieveAllUsers.setUUID((String) results.get(i).getProperty("uuid"));
					retrieveAllUsers.setLoginId((String) results.get(i).getProperty("loginId"));
					retrieveAllUsers.setFirstName((String) results.get(i).getProperty("firstName"));
					retrieveAllUsers.setLastName((String) results.get(i).getProperty("lastName"));
					retrieveAllUsers.setLoginType((String) results.get(i).getProperty("loginType"));
					retrieveAllUsers.setPicture((String) results.get(i).getProperty("picture"));

					jsonString = g.toJson(retrieveAllUsers);
					allusers.add(jsonString);

				}
				out.print(allusers);
			} else {
				out.print("{\"error\":\"No users found\"}");
				;
			}
		} catch (Exception e) {
			System.out.println("Exception occured while retrieving userinfo " + e.getStackTrace());
		}

	}

	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public ModelAndView logoutUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					System.out.println("JSESSIONID=" + cookie.getValue());
					break;
				}
			}
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(method = { RequestMethod.POST }, consumes = "text/plain" )
	public void sendEmail(@RequestBody String postData, HttpServletRequest request, HttpServletResponse response) throws ResourceNotFoundException, ParseErrorException, Exception {
		HttpSession userSession = request.getSession();
	
		TicketPojo ticketData = new Gson().fromJson(postData, TicketPojo.class);
		System.out.println(ticketData);		

		final String username = "karthick.raman@anywhere.co";
		final String password = "K@rthick31081994";

		String subject = "New issue ticket raised";
		String messageBody = "No data";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Query userQuery = new Query("Contacts").addFilter("uuid", FilterOperator.EQUAL, ticketData.getAssignedTo());
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			PreparedQuery pq = datastore.prepare(userQuery);
			
			List<Entity> recipientUser = null;
			recipientUser = pq.asList(FetchOptions.Builder.withLimit(1));
			System.out.println(recipientUser.get(0).getProperty("loginId"));
			
			messageBody = "<h1>Ticket was created by " + (String) userSession.getAttribute("email") + "</h1>" + "<p> "
					+ "<strong>Title : " + ticketData.getTitle() + " </strong>" + "</p>" + "<div>"
					+ "<strong style=\"color:red;\">Severity : " + ticketData.getSeverity()
					+ " </strong>" + "</div>";
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String) userSession.getAttribute("email")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse((String) recipientUser.get(0).getProperty("loginId")));
			message.setSubject(subject);
			message.setContent(messageBody, "text/html; charset=utf-8");

			Transport.send(message);
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	

	@RequestMapping(value = "/signup_page")
	public String signup() {
		return "signup";
	}

	@RequestMapping(value = "/home")
	public String homepage() {
		return "home";
	}


	public static String generateString() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
}
