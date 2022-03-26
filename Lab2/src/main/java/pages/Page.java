package pages;

import java.io.PrintWriter;
import java.util.Map;

public abstract class Page {
    protected PrintWriter printWriter;
    protected Map<String, String> inputtedParameters;
    protected String title;

    protected void startRenderPage() {
        printWriter.println("<!DOCTYPE html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n");
        printWriter.println("<html>\n");
        printWriter.println("<head>\n");
        printWriter.println("<title>" + this.title + "</title>\n");
        printWriter.println("<link rel=\"stylesheet\" type=\"text/css\" HREF=\"/styles/bootstrap.min.css\"");
        printWriter.println("</head>\n");
        printWriter.println("<body>\n");
    }

    protected void renderInputtedParameters() {
        printWriter.write("<div class=\"w-25 mt-3 mx-auto\">");
        printWriter.write("<table class=\"table\">");
        printWriter.write("<thead><tr><th colspan=\"2\">Введені параметри</th></tr></thead>");
        for (Map.Entry<String, String> entry : inputtedParameters.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.length() == 0) {
                continue;
            }
            printWriter.write("<tr>");
            printWriter.write("<td>" + entry.getKey() + "</td>");
            printWriter.write("<td>" + entry.getValue() + "</td>");
            printWriter.write("</tr>");
        }

        printWriter.write("</table>");
        printWriter.write("</div>");
    }

    protected void finishRenderPage() {
        printWriter.write("<div class=\"text-center mt-5\">");
        printWriter.write("<a href=\"/\" class=\"btn btn-primary\">На головну сторінку</href>");
        printWriter.write("</div>");
        printWriter.write("</body></html>");
        printWriter.close();
    }

    public abstract void render();
}
