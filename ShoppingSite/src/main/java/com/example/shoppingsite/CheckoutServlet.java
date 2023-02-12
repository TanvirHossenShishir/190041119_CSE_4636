package com.example.shoppingsite;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * This method is responsible for handling the checkout process. But it doesn't
     * really do anything. Currently, it just simply returns a HTML response with
     * success message.
     * @param request the request object that carries the client information
     * @param response the response object that will be sent to the client
     **/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        out.println("<html><body>" +
                "<center><h1>Thank You for Your Purchase!</h1>" +
                "<form method='post' action='cart'>" +
                "<input type='hidden' name='action' value='view'>" +
                "<input type='submit' value='Back to Cart'>" +
                "</form></center>" +
                "</body></html>");
    }
}
