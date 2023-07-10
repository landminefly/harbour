package com.admin.view;

import com.api.viewBean.LogisticsInfoViewBean;
import com.api.LogisticsInfoViewServer;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlet/view/logistics")
public class LogisticsView extends HttpServlet {
    /**
     * 处理物流数据页的请求
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
        LogisticsViewServletBean logisticsViewServletBean = new Gson().fromJson(sb.toString(), LogisticsViewServletBean.class);

        //解析数据
        Object[] originArgs = logisticsViewServletBean.getOriginArgs();
        long pageNum = logisticsViewServletBean.getPageNum();
        //存储各筛选条件
        String[] args = new String[12];
        //普通的数据直接加入即可
        int argsIndex = 0;
        for( ;argsIndex<=7; argsIndex++){
            if(originArgs[argsIndex] == null){
                args[argsIndex] = "";
            }else {
                args[argsIndex] = (String) originArgs[argsIndex];
            }
        }
        //前端传来的时间筛选条件是一个数组，将时间戳转换为DM8支持的格式后再加入
        for(int i = 8; i<=9; i++){
            ArrayList<Double> tempList = (ArrayList<Double>) originArgs[i];
            if(tempList == null){
                args[argsIndex++] = "";
                args[argsIndex++] = "";
                continue;
            }
            for(int j = 0; j<=1; j++){
                BigDecimal bigDecimal = BigDecimal.valueOf(tempList.get(j));
                String formattedNumber = bigDecimal.toPlainString();
                long timestamp = Long.parseLong(formattedNumber);
                Instant instant = Instant.ofEpochMilli(timestamp);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                String format = dateTimeFormatter.format(localDateTime);
                args[argsIndex] = format;
                argsIndex++;
            }
        }

        LogisticsInfoViewServer LogisticsInfoViewServer = new LogisticsInfoViewServer();
        List<LogisticsInfoViewBean> results = LogisticsInfoViewServer.query((Connection) req.getAttribute("conn"), args, pageNum);
        long count = LogisticsInfoViewServer.queryCount((Connection) req.getAttribute("conn"), args, pageNum);

        String[][] resultsArr = new String[results.size()+1][];
        for(int i = 0; i<resultsArr.length-1; i++){
            LogisticsInfoViewBean result = results.get(i);
            String[] resultData = new String[10];
            resultData[0] = result.getLading_number();
            resultData[1] = result.getCustomer_name();
            resultData[2] = result.getCustomer_code();
            resultData[3] = result.getCompany();
            resultData[4] = result.getContainer_number();
            resultData[5] = result.getCargo();
            resultData[6] = result.getDeparture();
            resultData[7] = result.getDestination();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            resultData[8] = dateTimeFormatter.format(result.getDeparture_time().toLocalDateTime());
            resultData[9] = dateTimeFormatter.format(result.getArrival_time().toLocalDateTime());
            resultsArr[i] = resultData;
        }

        resultsArr[resultsArr.length-1] = new String[]{Long.toString(count)};
        Gson gson = new Gson();
        String arrayJson = gson.toJson(resultsArr);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // 将数组JSON字符串作为响应发送
        res.getWriter().write(arrayJson);
    }
}
