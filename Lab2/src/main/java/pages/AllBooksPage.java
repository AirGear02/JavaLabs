package pages;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AllBooksPage extends Page {
    private final ResultSet resultSet;

    public AllBooksPage(ResultSet resultSet, Map<String, String> inputtedParameters, PrintWriter printWriter) {
        this.resultSet = resultSet;
        this.printWriter = printWriter;
        this.inputtedParameters = inputtedParameters;
        this.title = "Усі книги";

    }

    @Override
    public void render() {
        startRenderPage();
        renderInputtedParameters();
        renderBooksTable();
        finishRenderPage();
    }

    private void renderBooksTable() {
        printWriter.write("<div class=\"w-50 mt-5 mx-auto\">");
        printWriter.write("<table class=\"table table-striped\">");
        printWriter.write("<thead><tr><th>Id</th><th>Рік</th><th>Ім'я книги</th><th>Автор</th></tr></thead>");
        try {
            while (resultSet.next()) {
                printWriter.write("<tr>");
                for (int i = 1; i <= 4; i++) {
                    printWriter.write("<td>");
                    printWriter.write(resultSet.getString(i));
                    printWriter.write("</td>");
                }
                printWriter.write("</tr>");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        printWriter.write("</table>");
        printWriter.write("</div>");
    }
}
