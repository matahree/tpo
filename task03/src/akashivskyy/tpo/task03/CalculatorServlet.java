//
// CalculatorServlet.java
//
// Copyright © 2017 Adrian Kashivskyy. All rights reserved.
//

package akashivskyy.tpo.task03;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CalculatorServlet extends HttpServlet {

	// Routing

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		printHeader(resp);
		printForms(resp);
		printResult(req, resp);
		printFooter(resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		printHeader(resp);
		printForms(resp);
		printResult(req, resp);
		printFooter(resp);
	}

	// Printing

	private void printHeader(HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"css/styles.css\" />");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title>Calculator</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Calculator</h1>");
	}

	private void printForms(HttpServletResponse resp) throws IOException {
		printForm(resp, "get");
		printForm(resp, "post");
	}

	private void printForm(HttpServletResponse resp, String method) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<form acton=\"/\" method=\"" + method.toLowerCase() + "\">");
		out.println("<fieldset>");
		out.println("<legend>" + method.toUpperCase() + "</legend>");
		out.println("<input type=\"text\" name=\"x\" />");
		out.println(" + ");
		out.println("<input type=\"text\" name=\"y\" />");
		out.println(" = ");
		out.println("<input type=\"submit\" value=\"Submit using " + method.toUpperCase() + "\" />");
		out.println("</fieldset>");
		out.println("</form>");
	}

	private void printResult(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String xString = req.getParameter("x");
		String yString = req.getParameter("y");

		if (xString == null && yString == null) {
			return;
		}

		if (xString == null) {
			printError(resp, "Please provide a valid first number.");
			return;
		}

		if (yString == null) {
			printError(resp, "Please provide a valid second number.");
			return;
		}

		Integer xInt = null;
		Integer yInt = null;

		try {
			xInt = Integer.parseInt(xString);
		} catch (NumberFormatException e) {
			printError(resp, "Please provide a valid first number.");
			return;
		}

		try {
			yInt = Integer.parseInt(yString);
		} catch (NumberFormatException e) {
			printError(resp, "Please provide a valid second number.");
			return;
		}

		printSuccess(resp, xInt + yInt);

	}

	private void printSuccess(HttpServletResponse resp, int value) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<fieldset>");
		out.println("<legend class=\"success\">RESULT</legend>");
		out.println(value);
		out.println("</fieldset>");
	}

	private void printError(HttpServletResponse resp, String value) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<fieldset>");
		out.println("<legend class=\"error\">ERROR</legend>");
		out.println(value);
		out.println("</fieldset>");
	}

	private void printFooter(HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("</body>");
		out.println("</html>");
	}

}
