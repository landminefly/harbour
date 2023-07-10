package com.api.viewDAO.impl;

import com.api.viewBean.LogisticsInfoViewBean;
import com.api.viewDAO.inter.LogisticsInfoViewDAO;
import com.mvc.DAO.BaseDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogisticsInfoViewDAOImpl extends BaseDAO<LogisticsInfoViewBean> implements LogisticsInfoViewDAO {

    /**
     * 根据筛选条件查询Logistics_Information表中符合条件的记录条数
     */
    @Override
    public List<LogisticsInfoViewBean> query(Connection conn, String[] args, long pageNo) throws SQLException {
        String sql = """
                     SELECT INFO.lading_number,
                            INFO.customer_name,
                            INFO.customer_code,
                            INFO.company,
                            INFO.container_number,
                            INFO.cargo,
                            LOAD.departure,
                            LOAD.destination,
                            LOAD.departure_time,
                            LOAD.arrival_time
                     FROM LOGISTICS_INFORMATION AS INFO
                              LEFT JOIN LOAD ON INFO.lading_number = LOAD.lading_number
                     WHERE INFO.lading_number LIKE ?
                       AND INFO.customer_name LIKE ?
                       AND INFO.customer_code LIKE ?
                       AND INFO.company LIKE ?
                       AND INFO.container_number LIKE ?
                       AND INFO.cargo LIKE ?
                       AND LOAD.departure LIKE ?
                       AND LOAD.destination LIKE ?
                       AND LOAD.departure_time BETWEEN ? AND ?
                       AND LOAD.arrival_time BETWEEN ? AND ?
                       LIMIT ?, 20;
                     """;
        for(int i = 8; i<12; i++){
            if("".equals(args[i])){
                if(i % 2 == 0){
                    args[i] = "1945-01-01 00:00:00";
                }else{
                    args[i] = "2050-12-31 23:59:59";
                }
            }
        }
        return super.selectForMulti(conn, sql,
                                    "%" + args[0] + "%",
                                    "%" + args[1] + "%",
                                    "%" + args[2] + "%",
                                    "%" + args[3] + "%",
                                    "%" + args[4] + "%",
                                    "%" + args[5] + "%",
                                    "%" + args[6] + "%",
                                    "%" + args[7] + "%",
                                    args[8],
                                    args[9],
                                    args[10],
                                    args[11],
                                    (pageNo-1)*20
                                    );
    }

    /**
     * 根据筛选条件查询Logistics_Information表中符合条件的记录条数
     */
    @Override
    public long queryCount(Connection conn, String[] args, long PageNo) throws SQLException {
        String sql = """
                     SELECT COUNT(*)
                     FROM LOGISTICS_INFORMATION AS INFO
                              LEFT JOIN LOAD ON INFO.lading_number = LOAD.lading_number
                     WHERE INFO.lading_number LIKE ?
                       AND INFO.customer_name LIKE ?
                       AND INFO.customer_code LIKE ?
                       AND INFO.company LIKE ?
                       AND INFO.container_number LIKE ?
                       AND INFO.cargo LIKE ?
                       AND LOAD.departure LIKE ?
                       AND LOAD.destination LIKE ?
                       AND LOAD.departure_time BETWEEN ? AND ?
                       AND LOAD.arrival_time BETWEEN ? AND ?;
                     """;
        for(int i = 8; i<12; i++){
            if("".equals(args[i])){
                if(i % 2 == 0){
                    args[i] = "1945-01-01 00:00:00";
                }else{
                    args[i] = "2050-12-31 23:59:59";
                }
            }
        }
        return super.getValue(conn, sql,
                                    "%" + args[0] + "%",
                                    "%" + args[1] + "%",
                                    "%" + args[2] + "%",
                                    "%" + args[3] + "%",
                                    "%" + args[4] + "%",
                                    "%" + args[5] + "%",
                                    "%" + args[6] + "%",
                                    "%" + args[7] + "%",
                                    args[8],
                                    args[9],
                                    args[10],
                                    args[11]
                                   );
    }
}
