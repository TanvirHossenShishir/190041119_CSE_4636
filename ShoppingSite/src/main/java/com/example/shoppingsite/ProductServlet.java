package com.example.shoppingsite;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * This method is responsible for displaying a list of available products
     * to the user. It has a hardcoded static list of products and generates
     * HTML to display the product information to the user.
     * @param request the request object that carries the client information
     * @param response the response object that will be sent to the client
     **/
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean cookieFound = false;
        Cookie [] cookieArray = request.getCookies();
        PrintWriter out = response.getWriter();

        out.println("<html>\n" +
                "<head><title>Product List</title></head>\n" +
                "<body>\n");

        for (Cookie c:cookieArray) {
            if (c.getName().equals("usernameKey")) {
                cookieFound = true;
                out.print("<center><span><h3> Username: " + c.getValue() + " </h3>");

                break;
            }
        }
        if (!cookieFound) {
            out.println("<h1> Invalid session. Please log in again. " +
                    "</h1>");
            RequestDispatcher rd = request.getRequestDispatcher(
                    "login.html");
            rd.include(request, response);
        }

        out.println("<center><form method='post' action='logout'>" +
                "<input type='hidden' name='action' value='view'>" +
                "<input type='submit' value='Log out'>" +
                "</form></center>");

        out.println("<center><form method='post' action='cart'>" +
                "<input type='hidden' name='action' value='view'>" +
                "<input type='submit' value='Go to Cart'>" +
                "</form></center>");

        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        out.println("<h1 align = \"center\">Product List </h1>\n" +
                "<table border = \"1\" align = \"center\">\n" +
                "<tr>\n" +
                "<th>Description</th><th>Price</th><th>Add to Cart</th>" +
                "</tr>");

        List<Product> products = new ArrayList<Product>();

        products.add(new Product("Pen", 10, 0));
        products.add(new Product("Pencil", 15, 0));
        products.add(new Product("Eraser", 10, 0));
        products.add(new Product("Scale", 20, 0));
        products.add(new Product("Sharpener", 10, 0));

        for (Product product : products) {
            out.println("<tr>" +
                    "<td>" + product.getName() + "</td>" +
                    "<td>" + product.getPrice() + "</td>" +
                    "<td>" +
                    "<form action='cart' method='post'>" +
                    "<input type='hidden' name='name' value='" + product.getName() + "'>" +
                    "<input type='hidden' name='price' value='" + product.getPrice() + "'>" +
                    "<input type='hidden' name='action' value='add'>" +
                    "<input type='submit' value='Add to Cart'>" +
                    "</form>" +
                    "</td>" +
                    "</tr>");
        }

        out.println("</table>" +
                "</body>" +
                "</html>");
    }
}

