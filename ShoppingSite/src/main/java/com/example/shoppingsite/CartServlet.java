package com.example.shoppingsite;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CartServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * This method is responsible for managing the user's shopping cart.
     * It receives requests to add, remove, increase, or decrease the quantity
     * of items in the cart, and updates the cart accordingly. The cart is
     * stored as a session attribute, so it can be reused for multiple requests
     * from the same client.
     * @param request the request object that carries the client information
     * @param response the response object that will be sent to the client
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String title = "Shopping Cart";

        String action = request.getParameter("action");

        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (action != null && action.equals("add")) {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            boolean productExists = false;
            List<Product> items = cart.getProducts();
            for (Product item : items) {
                if (item.getName().equals(name)) {
                    item.setQuantity(item.getQuantity() + 1);
                    productExists = true;
                    break;
                }
            }
            if (!productExists) {
                cart.addProduct(new Product(name, price, 1));
//                items.add(new Product(name, price, 1));
            }
        }

        if (action != null && action.equals("remove")) {
            String name = request.getParameter("name");
            cart.removeProduct(name);
        }

        if (action != null && action.equals("increase")) {
            String name = request.getParameter("name");
            List<Product> items = cart.getProducts();
            for (Product item : items) {
                if (item.getName().equals(name)) {
                    item.setQuantity(item.getQuantity() + 1);
                    break;
                }
            }
        }

        if (action != null && action.equals("decrease")) {
            String name = request.getParameter("name");
            List<Product> items = cart.getProducts();
            for (Product item : items) {
                if (item.getName().equals(name)) {
                    item.setQuantity(item.getQuantity() - 1);
                    if (item.getQuantity() == 0) {
                        items.remove(item);
                    }
                    break;
                }
            }
        }

        if (action != null && action.equals("checkout")) {
            session.removeAttribute("cart");
            response.sendRedirect("checkout");
        }

        out.println("<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body>\n" +
                "<h1 align = \"center\">" + title + "</h1>\n");

        List<Product> items = cart.getProducts();

        out.println("<table border='1' align='center'>" +
                "<tr>" +
                "<th>Description</th>" +
                "<th>Price</th>" +
                "<th>Quantity</th>" +
                "<th>Remove</th>" +
                "</tr>");

        double total = 0;

        for (Product item : items) {
            out.println("<tr>" +
                    "<td>" + item.getName() + "</td>" +
                    "<td>" + item.getPrice() + "</td>" +
                    "<td>" +
                    "<form action='cart' method='post'>" +
                    "<input type='hidden' name='action' value='decrease'>" +
                    "<input type='hidden' name='name' value='" + item.getName() + "'>" +
                    "<input type='hidden' name='price' value='" + item.getPrice() + "'>" +
                    "<input type='submit' value='-'>" +
                    "</form>" +
                    item.getQuantity() +
                    "<form action='cart' method='post'>" +
                    "<input type='hidden' name='action' value='increase'>" +
                    "<input type='hidden' name='name' value='" + item.getName() + "'>" +
                    "<input type='hidden' name='price' value='" + item.getPrice() + "'>" +
                    "<input type='submit' value='+'>" +
                    "</form>" +
                    "</td>" +
                    "<td>" +
                    "<form action='cart' method='post'>" +
                    "<input type='hidden' name='action' value='remove'>" +
                    "<input type='hidden' name='name' value='" + item.getName() + "'>" +
                    "<input type='submit' value='X'>" +
                    "</form>" +
                    "</td>" +
                    "</tr>");

            total += item.getPrice() * item.getQuantity();
        }

        out.println("</table>\n" +
                "<p align='center'>Total Price: " + total + "</p>" +
                "<center><span>" +
                "<form action='product'>" +
                "<input type='submit' value='Continue Shopping'>" +
                "</form>" +
                "<form action='cart' method='post'>" +
                "<input type='hidden' name='action' value='checkout'>" +
                "<input type='submit' value='Checkout'>" +
                "</form>" +
                "</span></center>" +
                "</body>" +
                "</html>");

    }
}
