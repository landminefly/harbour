package com.api;

import com.Utils.JdbcUtils;
import com.parser.FileParser;
import com.parser.enums.TableEnum;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectToHadoop {
    //表枚举属性
    TableEnum tableEnum = null;

    public void connect(Connection conn,String url) throws SQLException {
        try {
            // Hadoop配置
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", url); // 设置Hadoop主节点和端口
            // 创建Hadoop文件系统对象
            FileSystem fs = FileSystem.get(conf);
            // Hadoop文件路径
            Path remotePath = new Path("/"); // 根目录，可以根据需求修改
            //本地文件保存路径
            String localPath = "/harbour/hadoopFiles"; // 本地保存路径，可以根据需求修改
            //递归下载文件
            downloadFiles(fs, remotePath, new Path(localPath));

            FileParser fileParser = new FileParser();
            File file = new File(localPath);
            for (File listFile : file.listFiles()) {
                //判断文件数据属于哪个表，并更新表枚举属性
                String name = listFile.getName();
                if (name.contains("装货表")) {
                    tableEnum = TableEnum.LOAD;
                } else if (name.contains("卸货表")) {
                    tableEnum = TableEnum.UNLOAD;
                } else if (name.contains("物流信息")) {
                    tableEnum = TableEnum.LOGISTICS_INFO;
                } else if (name.contains("物流公司")) {
                    tableEnum = TableEnum.LOGISTICS_COMPANY;
                } else if (name.contains("集装箱动态")) {
                    tableEnum = TableEnum.CONTAINER_STATUS;
                }

                Class<?> DAOClass = tableEnum.getDAOImplClass();
                Constructor<?> DAOConstructor = DAOClass.getConstructor();
                Object DAO = DAOConstructor.newInstance();
                Method insertBatch = DAOClass.getDeclaredMethod("truncate", Connection.class);
                insertBatch.invoke(DAO,conn);
            }

            for (File listFile : file.listFiles()) {
                fileParser.parse(listFile,conn);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadFiles(FileSystem fs, Path remotePath, Path localPath) throws IOException {
        // 获取指定路径下的文件状态
        FileStatus[] fileStatuses = fs.listStatus(remotePath);
        for (FileStatus status : fileStatuses) {
            if(!status.getPath().getName().contains("crc")) {
                // 下载文件到本地目录
                Path localPathFile = new Path(localPath.toString() + "/" + status.getPath().getName());
                fs.copyToLocalFile(false, status.getPath(), localPathFile, true);
            }
        }
    }
}
