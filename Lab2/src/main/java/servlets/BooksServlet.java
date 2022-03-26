package servlets;

import requests.GetBooksRequest;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "BooksServlet", value = "/books")
public class BooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        GetBooksRequest booksRequest = new GetBooksRequest(request, printWriter);
        try {
            booksRequest.perform();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
