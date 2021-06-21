package com.exterro.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.exterro.model.AddFeedback;
import com.exterro.model.Feedback;
import com.exterro.model.Product;
import com.exterro.model.Sales;
import com.exterro.model.Users;
import com.google.gson.Gson;

import net.minidev.json.JSONValue;

@Path("/call")
public class LoginController {
	private static int pid = 100;
	private static Logger LOG = Logger.getLogger(LoginController.class.getName());

	/*
	 * @Path("/login")
	 * 
	 * @POST public Response login(@FormParam("email")String
	 * username,@FormParam("pwd") String pwd) { //System.out.println(username+
	 * " "+pwd); if(username.equalsIgnoreCase("admin@gmail.com") &&
	 * pwd.equals("Admin1234")) return Response.status(200).entity(
	 * "User credentials are correct!!..").build(); else return
	 * Response.status(401).entity(
	 * "Failed to login!!..Enter the correct details.").build(); }
	 * 
	 * @Path("/registration")
	 * 
	 * @POST public Response register(@FormParam("firstname")String
	 * fname,@FormParam("lastname") String lname, @FormParam("email")String
	 * email,@FormParam("phone") int phone,@FormParam("pass")String
	 * pass,@FormParam("repass") String repass) { return
	 * Response.status(200).entity("User "+fname+" registered Successfully!!.."
	 * ).build();
	 * 
	 * }
	 */
	@SuppressWarnings("unchecked")
	@Path("/validate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validate(@FormParam("A") String s) {
		// System.out.println("1");
		// String str =null;
		// LOG.info("Inside Login method");
		String s1 = "";
		String s2 = "";

		JSONObject jobj = new JSONObject();
		try {
			System.out.println("inside controller");

			JSONParser js = new JSONParser();
			Object oo = js.parse(s);

			JSONArray ja = new JSONArray();
			ja.add(oo);
			// System.out.println("value!!!!"+ja);
			for (int i = 0; i < ja.size(); i++) {
				// System.out.println("inside loop");
				JSONObject jj = (JSONObject) ja.get(i);
				System.out.println(jj.get("name"));
				System.out.println(jj.get("password"));
				s1 = (String) jj.get("name");
				s2 = (String) jj.get("password");
			}

		} catch (Exception e) {
			// LOG.error(e);
			e.printStackTrace();
		}
		if (s1.equalsIgnoreCase("sabari") && s2.equals("123456")) {
			System.out.println("in IF");
			jobj.put("status", "success");
		} else if (s1.equalsIgnoreCase("admin") && s2.equals("ADMIN")) {
			System.out.println("Inside Admin IF");
			jobj.put("status", "success1");
		} else {
			jobj.put("status", "error");
		}
		System.out.println(jobj);

		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/register")
	@POST
	// @Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
	// MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response Reg(@FormParam("A") String s) throws Exception {
		// LOG.info("Log details");
		JSONObject jobj = new JSONObject();
		String fname = "";
		String pass = "";
		Session sess = RegConfig.getSessionFactory().openSession();

		System.out.println("inside controller");

		JSONParser js = new JSONParser();
		Object oo = js.parse(s);

		JSONArray ja = new JSONArray();
		ja.add(oo);
		// System.out.println("value!!!!" + ja);
		for (int i = 0; i < ja.size(); i++) {
			System.out.println("inside loop");
			JSONObject jj = (JSONObject) ja.get(i);
			// System.out.println(jj.get("fname"));
			// System.out.println(jj.get("email"));
			String s1 = (String) jj.get("fname");
			String s2 = (String) jj.get("lname");
			String s3 = (String) jj.get("email");
			int mob = Integer.parseInt((String) jj.get("phone"));
			String s4 = (String) jj.get("pass");
			String s5 = (String) jj.get("repass");
			fname = s1;
			pass = s4;
			System.out.println(fname + " " + pass);
			Users cust = new Users(s1, s2, s3, mob, s4, s5);
			sess.beginTransaction();
			sess.save(cust);
			sess.getTransaction().commit();
		}
		jobj.put("Status", "Success");
		// sess.close();

		System.out.println(jobj);
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response userDetails() {
		JSONObject jobj = new JSONObject();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "select * from Users";
			Statement s1 = c.createStatement();
			ResultSet r = s1.executeQuery(str);
			while (r.next()) {
				System.out.println(r.getString("first_name") + " " + r.getString("last_name") + " "
						+ r.getString("email") + " " + r.getInt("phone"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_HTML)
	public Response addProduct(@FormParam("A") String s) throws Exception {

		JSONObject jobj = new JSONObject();
		Session sess = AddProductConfig.getSessionFactory().openSession();

		System.out.println("inside controller");

		JSONParser js = new JSONParser();
		Object oo = js.parse(s);

		JSONArray ja = new JSONArray();
		ja.add(oo);
		// System.out.println("value!!!!" + ja);
		for (int i = 0; i < ja.size(); i++) {
			System.out.println("inside loop");
			JSONObject jj = (JSONObject) ja.get(i);
			// System.out.println(jj.get("fname"));
			// System.out.println(jj.get("email"));
			String s1 = (String) jj.get("pname");
			int price = Integer.parseInt((String) jj.get("price"));
			int quantity = Integer.parseInt((String) jj.get("quantity"));
			String image = (String) jj.get("image");
			Scanner sc = new Scanner(new BufferedReader(new FileReader(image)));
			Blob blob = null;
			while (sc.hasNext()) {
				byte[] myArray = sc.nextLine().getBytes();
				blob = new SerialBlob(myArray);
			}
			System.out.println(s1 + " " + price + " " + quantity + " " + blob);
			Product p = new Product(s1, price, quantity, blob);
			sess.beginTransaction();
			sess.save(p);
			sess.getTransaction().commit();
		}
		jobj.put("status", "success");

		System.out.println(jobj);
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/product")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response productDetail() {
		JSONObject jobj = new JSONObject();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "select * from Product";
			Statement s1 = c.createStatement();
			ResultSet r = s1.executeQuery(str);
			int i = 1;
			while (r.next()) {
				Blob blob = r.getBlob("image");
				byte byteArray[] = blob.getBytes(1, (int) blob.length());
				FileOutputStream outPutStream = new FileOutputStream(
						"C:\\Users\\sveluswamy\\Desktop\\bloboutput" + i + ".jpg");
				outPutStream.write(byteArray);
				System.out.println(byteArray);
				System.out.println(r.getInt("pid") + " " + "C:\\Users\\sveluswamy\\Desktop\\bloboutput" + i + ".jpg"
						+ " " + r.getString("pname") + " " + r.getInt("price") + " " + r.getInt("quantity"));
				i++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/removeProduct")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response removeProduct(@FormParam("A") String s) throws Exception {
		JSONObject jobj = new JSONObject();
		JSONParser js = new JSONParser();
		Object oo = js.parse(s);
		JSONArray ja = new JSONArray();
		ja.add(oo);
		JSONObject jj = (JSONObject) ja.get(0);
		int id = Integer.parseInt((String) jj.get("id"));
		System.out.println(id);
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "delete from Product where pid=" + id;
			Statement s1 = c.createStatement();
			s1.executeUpdate(str);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked" })
	@Path("/addProducts")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response addProducts(@FormParam("A") String s) throws Exception {
		JSONObject jobj = new JSONObject();
		System.out.println("inside controller-addProducts");

		JSONParser js = new JSONParser();
		Object oo = js.parse(s);

		JSONArray ja = new JSONArray();
		ja.add(oo);
		// System.out.println("value!!!!" + ja);

		// Initialize the file object using text file location as above
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
				"exterro-123456");
		for (int i = 0; i < ja.size(); i++) {
			System.out.println("inside loop");
			JSONObject jj = (JSONObject) ja.get(i);
			// System.out.println(jj.get("fname"));
			// System.out.println(jj.get("email"));
			String s1 = (String) jj.get("pname");
			int price = Integer.parseInt((String) jj.get("price"));
			int quantity = Integer.parseInt((String) jj.get("quantity"));
			String image = (String) jj.get("image");
			File file = new File(image);
			System.out.println(s1 + " " + price + " " + quantity + " " + image);
			PreparedStatement pStmt = null;
			ByteArrayOutputStream bAout = new ByteArrayOutputStream();
			pStmt = connection
					.prepareStatement("insert into Product_details(image, name, price, quantity) values(?, ?, ?, ?)");
			// pStmt.setInt(1, pid++);
			pStmt.setBlob(1, new FileInputStream(file));
			pStmt.setString(2, s1);
			pStmt.setInt(3, price);
			pStmt.setInt(4, quantity);
			pStmt.execute();
			bAout.close();
			pStmt.close();
			System.out.println("Data successfully inserted to table ");
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/products")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response productDetails() {
		JSONObject jobj = new JSONObject();
		List<AddFeedback> l = new ArrayList<AddFeedback>();
		String json = "";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "select * from AddFeedback";
			Statement s1 = c.createStatement();
			ResultSet r = s1.executeQuery(str);
			// int i = 1;
			// System.out.println(r);
			/*
			 * List<User> users=new ArrayList<User>(); while(rs.next()) { User
			 * user = new User(); user.setUserId(rs.getString("UserId"));
			 * user.setFName(rs.getString("FirstName")); users.add(user); }
			 */

			while (r.next()) {
				AddFeedback p = new AddFeedback(str, str, str, str);
				// System.out.println(r.getString("name"));
				p.setName(r.getString("name"));
				p.setDescription(r.getString("description"));
				p.setVenue(r.getString("venue"));
				p.setTime(r.getString("time"));
				l.add(p);
				json += new Gson().toJson(p);
				System.out.println(json);
			}
			for (AddFeedback a : l) {
				System.out.println(a.getName() + " " + a.getDescription());
			}
			// System.out.println(l);

			/*
			 * while (r.next()) { Blob blob = r.getBlob("image"); byte
			 * byteArray[] = blob.getBytes(1, (int) blob.length());
			 * FileOutputStream outPutStream = new FileOutputStream(
			 * "C:\\Users\\sveluswamy\\Desktop\\blob_output" + i + ".jpg");
			 * outPutStream.write(byteArray); System.out.println(byteArray);
			 * System.out.println(r.getInt("pid") + " " +
			 * "C:\\Users\\sveluswamy\\Desktop\\blob_output" + i + ".jpg" + " "
			 * + r.getString("name") + " " + r.getInt("price") + " " +
			 * r.getInt("quantity")); i++; }
			 */
		} catch (Exception e) {
			System.out.println(e.getMessage() + "123");
		}
		jobj.put("status", "success");
		return Response.ok(json).build();
	}

	@Path("/sales")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_HTML)
	public Response sales(@FormParam("A") String s) throws Exception {

		JSONObject jobj = new JSONObject();
		Session sess = SalesConfig.getSessionFactory().openSession();

		System.out.println("inside controller");

		JSONParser js = new JSONParser();
		Object oo = js.parse(s);

		JSONArray ja = new JSONArray();
		ja.add(oo);
		// System.out.println("value!!!!" + ja);
		for (int i = 0; i < ja.size(); i++) {
			System.out.println("inside loop");
			JSONObject jj = (JSONObject) ja.get(i);
			// System.out.println(jj.get("fname"));
			// System.out.println(jj.get("price1"));
			Long price = (Long) jj.get("price1");
			System.out.println(price);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(price + " " + formatter.format(date));
			// System.out.println(price);
			Sales p = new Sales(price, formatter.format(date));
			sess.beginTransaction();
			sess.save(p);
			sess.getTransaction().commit();
		}
		jobj.put("status", "success");

		System.out.println(jobj);
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/criteria")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_HTML)

	public Response criteria() {
		Session sess = SalesConfig.getSessionFactory().openSession();
		System.out.println("inside controller");

		@SuppressWarnings("deprecation")
		Criteria cc = sess.createCriteria(Sales.class);
		// cc.add(Restrictions.eq("first_name", "sabarish"));
		List<Users> ll = cc.list();
		for (Users s : ll) {
			System.out.println(s.getFirst_name() + " " + s.getLast_name() + " " + s.getEmail() + " " + s.getPhone());
			;
		}
		return Response.ok().build();

	}

	@SuppressWarnings("unchecked")
	@Path("/salesReport")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_HTML)

	public Response salesReport() {
		Session sess = SalesConfig.getSessionFactory().openSession();
		System.out.println("inside controller");

		@SuppressWarnings("deprecation")
		Criteria cc = sess.createCriteria(Sales.class);
		// cc.add(Restrictions.eq("first_name", "sabarish"));
		List<Sales> ll = cc.list();
		for (Sales s : ll) {
			System.out.println(s.getSid() + " " + s.getDate() + " " + s.getPrice());
		}
		return Response.ok("success").build();

	}

	@SuppressWarnings("unchecked")
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("A") String s) {
		System.out.println("Inside Controller" + " " + s);
		String s1 = "";
		String s2 = "";
		// LOG.info("ADMIN logged in successfully!!");
		JSONObject jobj = new JSONObject();
		try {
			System.out.println("inside controller");

			JSONParser js = new JSONParser();
			Object oo = js.parse(s);

			JSONArray ja = new JSONArray();
			ja.add(oo);
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jj = (JSONObject) ja.get(i);
				System.out.println(jj.get("name"));
				System.out.println(jj.get("password"));
				s1 = (String) jj.get("name");
				s2 = (String) jj.get("password");
			}

		} catch (Exception e) {
			// LOG.error(e);
			e.printStackTrace();
		}
		if (s1.equalsIgnoreCase("admin") && s2.equals("ADMIN")) {
			System.out.println("Inside Admin IF");
			jobj.put("status", "success");
		} else {
			jobj.put("status", "error");
		}
		System.out.println(jobj);

		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/addFeedback")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFeedback(@FormParam("A") String s) throws Exception {

		System.out.println("inside addfeedback" + " " + s);
		// System.out.println(s);
		JSONObject jobj = new JSONObject();
		Session sess = AddFeedbackConfig.getSessionFactory().openSession();

		// System.out.println("inside controller");
		// LOG.info("Data Added into the database");
		JSONParser js = new JSONParser();
		Object oo = js.parse(s);
		try {
			JSONArray ja = new JSONArray();
			ja.add(oo);
			// System.out.println("value!!!!" + ja);
			for (int i = 0; i < ja.size(); i++) {
				// System.out.println("inside loop");
				JSONObject jj = (JSONObject) ja.get(i);
				// System.out.println(jj.get("fname"));
				// System.out.println(jj.get("email"));
				String s1 = (String) jj.get("name");
				String s2 = ((String) jj.get("description"));
				String s3 = ((String) jj.get("venue"));
				String s4 = (String) jj.get("time");
				/*
				 * Scanner sc = new Scanner(new BufferedReader(new
				 * FileReader(image))); Blob blob = null; while (sc.hasNext()) {
				 * byte[] myArray = sc.nextLine().getBytes(); blob = new
				 * SerialBlob(myArray); }
				 */
				System.out.println(s1 + " " + s2 + " " + s3 + " " + s4);
				AddFeedback p = new AddFeedback(s1, s2, s3, s4);
				sess.beginTransaction();
				sess.save(p);
				sess.getTransaction().commit();
			}
		} catch (Exception e) {
			// LOG.error(e);
		}
		jobj.put("status", "success");

		// System.out.println(jobj);
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/deleteFeedback")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)

	public Response deleteFeedback(@FormParam("A") String s) throws Exception {
		JSONObject jobj = new JSONObject();
		JSONParser js = new JSONParser();
		Object oo = js.parse(s);
		JSONArray ja = new JSONArray();
		ja.add(oo);
		JSONObject jj = (JSONObject) ja.get(0);
		String feedbackName = ((String) jj.get("name"));
		System.out.println(feedbackName);
		// LOG.info("Data deleted from the database successfully!!..");
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "delete from AddFeedback where UPPER(name)=" + "'" + feedbackName + "'";
			Statement s1 = c.createStatement();
			s1.executeUpdate(str);
			c.close();
		} catch (Exception e) {
			// LOG.error(e);
			System.out.println(e.getMessage());
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/getFeedback")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public String getFeedback() {
		// JSONObject jobj = new JSONObject();
		List<AddFeedback> l = new ArrayList<AddFeedback>();
		JSONObject js = new JSONObject();
		String json = "";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "select * from AddFeedback";
			Statement s1 = c.createStatement();
			ResultSet r = s1.executeQuery(str);
			// int i = 1;
			// System.out.println(r);
			/*
			 * List<User> users=new ArrayList<User>(); while(rs.next()) { User
			 * user = new User(); user.setUserId(rs.getString("UserId"));
			 * user.setFName(rs.getString("FirstName")); users.add(user); }
			 */

			while (r.next()) {

				/*
				 * HashMap<String, String> h=new HashMap<String, String>();
				 * 
				 * //System.out.println(r.getString("name")); h.put("fid",
				 * (String.valueOf(r.getInt("fid")))); h.put("name",
				 * r.getString("name"));
				 */
				AddFeedback p = new AddFeedback(str, str, str, str);
				p.setFid(r.getInt("fid"));
				p.setName(r.getString("name"));
				p.setDescription(r.getString("description"));
				p.setVenue(r.getString("venue"));
				p.setTime(r.getString("time"));
				l.add(p);
				// js=new JSONObject(h);
				// jobj.put(r.getInt("fid"),p);
				// System.out.println(json);
			}
			json = new Gson().toJson(l);
			/*
			 * Object obj1=JSONValue.parse(json); js=new JSONObject((Map) obj1);
			 */
			// JSONArray j=new JSONArray(l);
			System.out.println(js);

			/*
			 * while (r.next()) { Blob blob = r.getBlob("image"); byte
			 * byteArray[] = blob.getBytes(1, (int) blob.length());
			 * FileOutputStream outPutStream = new FileOutputStream(
			 * "C:\\Users\\sveluswamy\\Desktop\\blob_output" + i + ".jpg");
			 * outPutStream.write(byteArray); System.out.println(byteArray);
			 * System.out.println(r.getInt("pid") + " " +
			 * "C:\\Users\\sveluswamy\\Desktop\\blob_output" + i + ".jpg" + " "
			 * + r.getString("name") + " " + r.getInt("price") + " " +
			 * r.getInt("quantity")); i++; }
			 */
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// jobj.put("status", "success");
		return json;
	}

	@SuppressWarnings("unchecked")
	@Path("/updateFeedback")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response update(@FormParam("A") String s) throws Exception {
		JSONObject jobj = new JSONObject();
		try {
			JSONParser js = new JSONParser();
			Object oo = js.parse(s);
			JSONArray ja = new JSONArray();
			ja.add(oo);
			JSONObject jj = (JSONObject) ja.get(0);
			int s5 = Integer.parseInt((String) jj.get("id"));
			String s1 = (String) jj.get("name");
			String s2 = ((String) jj.get("des"));
			String s3 = ((String) jj.get("venue"));
			String s4 = (String) jj.get("time");
			System.out.println(s1+" "+s2+" "+s3+" "+s4);
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection c = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=school", "sa",
					"exterro-123456");
			String str = "UPDATE AddFeedback SET name=" + "'"+ s1 +"'"+ ", description=" +"'"+ s2 +"'" + ", venue=" + "'"+ s3 +"'" + ",time= " +"'"+ s4 +"'"
					+ " WHERE fid=" + s5;
			Statement stmt = c.createStatement();
			stmt.executeUpdate(str);
			System.out.println("Table updated Successfully!!..");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}
	@SuppressWarnings({ "unchecked", "resource" })
	@Path("/Feedback")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response feedback(@FormParam("A") String s) throws Exception {

		JSONObject jobj = new JSONObject();
		Session sess = FeedbackConfig.getSessionFactory().openSession();
		LOG.info("Data Added into the database");
		JSONParser js = new JSONParser();
		Object oo = js.parse(s);
		try {
			JSONArray ja = new JSONArray();
			ja.add(oo);
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jj = (JSONObject) ja.get(i);
				String s1 = (String) jj.get("sname");
				int s2 = Integer.parseInt(((String) jj.get("rating")));
				String s3 = ((String) jj.get("fdk"));
				System.out.println(s1 + " " + s2 + " " + s3);
				Feedback p = new Feedback(s1, s2, s3);
				sess.beginTransaction();
				sess.save(p);
				sess.getTransaction().commit();
			}
		} catch (Exception e) {
			// LOG.error(e);
		}
		jobj.put("status", "success");

		// System.out.println(jobj);
		return Response.ok(jobj.toString()).build();
	}
}
