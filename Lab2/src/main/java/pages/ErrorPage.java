package pages;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ErrorPage extends Page{
    private List<String> errors;

    public ErrorPage(List<String> errors, Map<String, String> inputtedParameters, PrintWriter printWriter) {
        this.errors = errors;
        this.printWriter = printWriter;
        this.inputtedParameters = inputtedParameters;
        this.title = "Помилка";
    }

    @Override
    public void render() {
        startRenderPage();
        renderInputtedParameters();
        renderErrors();
        finishRenderPage();
    }

    private void renderErrors()
    {
        printWriter.write("<div class=\"w-50 mt-5 mx-auto\">");
        for (String error: errors) {
            printWriter.write("<div class=\"alert alert-danger mt-3\">");
            printWriter.write(error);
            printWriter.write("</div>");
        }
        printWriter.write("</div>");
    }
}
