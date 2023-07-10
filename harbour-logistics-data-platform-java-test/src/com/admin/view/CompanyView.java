package com.admin.view;

import com.api.CustomerViewServer;
import com.api.LogisticsCompanyViewServer;
import com.api.viewBean.CustomerViewBean;
import com.api.viewBean.LogisticsCompanyViewBean;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/servlet/view/company")
public class CompanyView extends HttpServlet {
    /**
     * 处理物流公司数据页的请求
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //接收数据，并转换为bean
        req.setCharacterEncoding("utf-8");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            sb.append(temp);
        }
        ViewServletBean viewServletBean = new Gson().fromJson(sb.toString(), ViewServletBean.class);

        //解析数据
        Object[] originArgs = viewServletBean.getOriginArgs();
        long pageNum = viewServletBean.getPageNum();
        //存储各筛选条件
        String[] args = new String[5];
        //普通的数据直接加入即可
        int argsIndex = 0;
        for( ;argsIndex<=4; argsIndex++){
            if(originArgs[argsIndex] == null){
                args[argsIndex] = "";
            }else {
                args[argsIndex] = (String) originArgs[argsIndex];
            }
        }

        //执行api的查询方法
        LogisticsCompanyViewServer logisticsCompanyViewServer = new LogisticsCompanyViewServer();
        List<LogisticsCompanyViewBean> results = logisticsCompanyViewServer.query((Connection) req.getAttribute("conn"), args, pageNum);
        long count = logisticsCompanyViewServer.queryCount((Connection) req.getAttribute("conn"), args, pageNum);

        //将得到的封装对象中的各个属性单独拿出来，按照前端页面的数据格式放入一个数组中
        //多个封装对象对应多个数组，最终得到一个二维数组
        String[][] resultsArr = new String[results.size()+1][];
        for(int i = 0; i<resultsArr.length-1; i++){
            LogisticsCompanyViewBean result = results.get(i);
            String[] resultData = new String[5];
            resultData[0] = result.getName();
            resultData[1] = result.getCode();
            resultData[2] = result.getPerson();
            resultData[3] = result.getPhone();
            resultData[4] = result.getAddress();
            resultsArr[i] = resultData;
        }
        //二维数组的最后要预留一个位置，用来存放查询到的记录条数
        resultsArr[resultsArr.length-1] = new String[]{Long.toString(count)};

        //格式化后响应给前端
        Gson gson = new Gson();
        String arrayJson = gson.toJson(resultsArr);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // 将数组JSON字符串作为响应发送
        res.getWriter().write(arrayJson);
    }
}
