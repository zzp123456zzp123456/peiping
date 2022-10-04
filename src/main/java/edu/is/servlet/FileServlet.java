package edu.is.servlet;

import edu.is.service.Service;
import edu.is.util.ThymeleafUtil;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@MultipartConfig//表示用于文件传输
@WebServlet("/file")
public class FileServlet extends HttpServlet {


    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        Part part = req.getPart("testFile");
        if (part == null) {
            ThymeleafUtil.process("file2.html", new Context(), resp.getWriter());
        }
        if (!part.getSubmittedFileName().endsWith(".xlsx")) {
            //等待改装
            resp.getWriter().write("格式错误,请重新上传");
            Thread.sleep(1000);
            ThymeleafUtil.process("file2.html", new Context(), resp.getWriter());
            return;
        }

        Service service = new Service();

        if (service.pingDanTest(part.getInputStream())) {
            try (FileInputStream inputStream = new FileInputStream("output.xlsx")) {
                resp.setContentType("application/x-msdownload");
                resp.setHeader("Content-Disposition", "attachment;filename=" + part.getSubmittedFileName());
                IOUtils.copy(inputStream, resp.getOutputStream());
            }
        }
    }

}
