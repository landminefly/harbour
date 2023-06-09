package com.api;

import com.parser.FileParser;
import com.parser.enums.TableEnum;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectToMinIO {

    TableEnum tableEnum = null;

    /**
     * 连接到MinIO存储并将数据导入DM8
     *
     * @param conn DM8连接
     * @param url  MinIO存储地址
     * @param usr  登录到MinIO的用户名
     * @param pwd  登录到MinIO的密码
     */
    public void connect(Connection conn, String url, String usr, String pwd) {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                               .endpoint(url)
                               .credentials(usr, pwd)
                               .build();
            String bucketName = "data";
            String localPathPar = "/harbour/";
            File dirPar = new File(localPathPar);
            if (!dirPar.exists()) {
                dirPar.mkdir();
            }
            String localPath = "/harbour/minIOFiles/";
            File dir = new File("/harbour/minIOFiles/");
            if (!dir.exists()) {
                dir.mkdir();
            }
            for (Bucket listBucket : minioClient.listBuckets()) {
                System.out.println(listBucket.name());
            }

            // 检查'bucketName'是否存在。
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                // 列出'bucketName'里的对象
                Iterable<Result<Item>> myObjects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());

                for (Result<Item> result : myObjects) {
                    Item item = result.get();

                    System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());

                    if (!item.objectName().contains("mc")) {

                        InputStream in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                        File file = new File(localPath + item.objectName());
                        FileOutputStream ou = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int length = 0;
                        while ((length = in.read(buffer)) != -1) {
                            ou.write(buffer, 0, length);
                        }
                        in.close();
                        ou.close();
                    }
                }

            }

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
                insertBatch.invoke(DAO, conn);

            }
            //解析文件
            for (File listFile : file.listFiles()) {
                fileParser.parse(listFile, conn);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}