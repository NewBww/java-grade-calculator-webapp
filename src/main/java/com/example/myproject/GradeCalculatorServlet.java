package com.example.myproject;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static java.lang.Double.parseDouble;

@WebServlet(name = "GradeCalculatorServlet", value = "/gradeCalculator")
public class GradeCalculatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double sumGrade = 0;
        double sumCredit = 0;
        for(int i = 1; i < 11; i++) {
            if (!request.getParameter("credit" + i).equals("")) {
                sumGrade += (parseDouble(request.getParameter("credit" + i)) * parseDouble(request.getParameter("grade" + i)));
                sumCredit += parseDouble(request.getParameter("credit" + i));
            }
        }

        if (sumCredit == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credit parameter");
            return;
        }

        double gpa = sumGrade / sumCredit;
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        gpa = Double.parseDouble(df.format(gpa));

        request.setAttribute("gpa", gpa);
        request.getRequestDispatcher("your_gpa.jsp").forward(request, response);
    }
}
