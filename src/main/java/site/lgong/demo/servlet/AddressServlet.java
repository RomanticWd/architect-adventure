package site.lgong.demo.servlet;

import site.lgong.demo.entity.Address;
import site.lgong.demo.utils.DataBaseHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author <a href="https://github.com/RomanticWd">RomanticWd</a>
 * @description 使用servlet实现控制器层
 * @createTime 2020/7/20 22:47
 */
@WebServlet("/address")
public class AddressServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "select * from address";
        List<Address> addressList = DataBaseHelper.queryEntityList(Address.class, sql);
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(addressList);
        out.flush();
        out.close();
    }
}
