package com.manish.empl.employeeservice.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class HttpURLConnectionExample {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://localhost:9000/emp/getAllEmp";

	private static final String POST_URL = "http://localhost:9000/emp/onBoardAll";


	public static void main(String[] args) throws IOException {
		
		System.out.println("GET STARTS");
		sendGET();
		System.out.println("GET DONE\n\n");
		System.out.println("POST STARTS");
		sendPOST();
	    System.out.println("POST DONE");
	}

	private static void sendGET() throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

	}

	private static void sendPOST() throws IOException {
		
		List<Employee> employeeList = new ArrayList<>();
		
		Employee employee = new Employee();
		
		//employee.setId(1005L);
		employee.setDesignation("Senior Manager");
		employee.setLocation("New York");
		employee.setName("Wade");
		employee.setSalary(6000L);
		
		employeeList.add(employee);

		/*
		so, here is the challenge: sending list as part of the request body and unfortunately , you can not use spring
		stuff here, so follow below steps
		1. Create your objects
		2. Add those objects to list
		3. Convert list to json , I have used Gson library here.
		4. post the json as byte array.
			Problem solved: Enjoy :)
		 */
		
		System.out.println("List Content is "+employeeList);
		
		String json = new Gson().toJson(employeeList);
		
		System.out.println("Json Data "+ json);
		
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		
		
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_CREATED) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
	}

}