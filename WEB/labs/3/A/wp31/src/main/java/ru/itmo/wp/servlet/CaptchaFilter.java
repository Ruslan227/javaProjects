package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
//import java.util.Base64;
import java.util.Random;
import org.apache.commons.codec.binary.Base64;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final int MIN = 100;
        final int MAX = 999;
        boolean isCorrectAnswer = false;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        Boolean captcha = (Boolean) session.getAttribute("captcha");
        while (captcha == null) {
            Random r = new Random();
            int expectedValue = r.nextInt(MAX - MIN + 1) + MIN;
            session.setAttribute("expectedValue", expectedValue);
            byte[] pngBytes = ImageUtils.toPng(String.valueOf(expectedValue));
//            byte[] decodedBytesPng = Base64.getDecoder().decode(pngBytes);

            response.setContentType("text/html");
//            OutputStream outputStream = response.getOutputStream();
//            outputStream.write(pngBytes);
//            Files.copy(file.toPath(), outputStream);
            byte[] image = Base64.encodeBase64(pngBytes);
//            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Title</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<img src=\"data:image/png;base64, " + String.valueOf(image) + "\">\n" +
                    "<form action=\"captchaAnswer\" method=\"post\">\n" +
                    "    <input name=\"text\">\n" +
                    "</form>\n" +
                    "</body>\n" +
                    "</html>");
//            outputStream.flush();
            if (Integer.parseInt(request.getParameter("text")) != expectedValue) {
                captcha = null;
            }
        }
        super.doFilter(request, response, chain);
    }
}
