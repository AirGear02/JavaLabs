package requests;

import db.DB;
import db.enums.OrderDirectionEnum;
import pages.AllBooksPage;
import pages.ErrorPage;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GetBooksRequest {
    int year;
    String operator;
    OrderDirectionEnum orderDirectionEnum;
    private List<String> errors;
    private Map<String, String> inputtedParameters;

    private HttpServletRequest request;
    private PrintWriter printWriter;

    public GetBooksRequest(HttpServletRequest request, PrintWriter printWriter) {
        this.request = request;
        this.printWriter = printWriter;
        errors = new ArrayList<>();
        inputtedParameters = new HashMap<>();
    }

    public void validateYear() {
        String yearStr = request.getParameter("year");
        inputtedParameters.put("Рік", yearStr);

        try {
            int year = Integer.parseInt(yearStr);
            if (year <= 0) {
                errors.add("Введіть рік більший нуля");
                return;
            }
            this.year = year;
        } catch (NumberFormatException e) {
            errors.add("Будь ласка, введіть коректний рік");
        } catch (NullPointerException e) {
            errors.add("Ви не ввели рік");
        }
    }

    public void validateOperator() {
        String operator = request.getParameter("operator");
        inputtedParameters.put("Оператор", operator);
        if (!Objects.equals(operator, ">") && !Objects.equals(operator, "<")) {
            errors.add("Будь ласка, виберіть коретний оператор більше або менше");
        }
        this.operator = operator;
    }

    public void validateOrderDirection() {
        String order = request.getParameter("order");
        inputtedParameters.put("Порядок сортування", order);
        if (order == null || order.length() == 0) {
            return;
        }
        try {
            OrderDirectionEnum.valueOf(order.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            errors.add("Ви ввели невірний порядок сортування");
        }
    }

    public void validateAllData() {
        validateYear();
        validateOperator();
        validateOrderDirection();
    }

    public void perform() throws SQLException, ClassNotFoundException {
        validateAllData();
        if (!errors.isEmpty()) {
            ErrorPage errorPage = new ErrorPage(this.errors, this.inputtedParameters, printWriter);
            errorPage.render();
            return;
        }
        DB db = new DB();
        ResultSet resultSet = db.getBooks(this.year, this.operator, this.orderDirectionEnum);
        AllBooksPage allBooksPage = new AllBooksPage(resultSet, this.inputtedParameters, this.printWriter);
        allBooksPage.render();
    }
}
