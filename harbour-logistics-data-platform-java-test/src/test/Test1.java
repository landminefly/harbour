package test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Test1 {

    public static void main(String[] args) throws SQLException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Scheduler scheduler;

        try {
            // 创建调度器
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 创建任务
            JobDetail job = JobBuilder.newJob(YourJob.class)
                                      .withIdentity("yourJob", "yourGroup")
                                      .build();

            // 创建触发器，设置定时执行的时间间隔
            Trigger trigger = TriggerBuilder.newTrigger()
                                            .withIdentity("yourTrigger", "yourGroup")
                                            .startNow()
                                            .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)) // 每60秒执行一次
                                            .build();

            // 将任务和触发器关联到调度器
            scheduler.scheduleJob(job, trigger);

            // 启动调度器
            scheduler.start();
        } catch (SchedulerException e) {
            // 异常处理
        }

        // Connection conn = JdbcUtils.getConnection();
        // LogisticsInfoViewDAOImpl logisticsInfoViewDAOImpl = new LogisticsInfoViewDAOImpl();
        // List<LogisticsInfoViewBean> query = logisticsInfoViewDAOImpl.query(conn, (String) null);
        // System.out.println(query.size());

        // try {
        //     Connection conn = JdbcUtils.getConnection();
        //     new FileParser().parse(new File("C:\\Users\\leili\\Desktop\\File\\装货表2022.txt"),conn);
        // } catch (IOException | InvalidFormatException e) {
        //     throw new RuntimeException(e);
        // }

        // Connection conn = JdbcUtils.getConnection();
        // QueryRunner queryRunner = new QueryRunner();
        // List<CustomerBean> list = queryRunner.query(conn, "SELECT * FROM CUSTOMER;", new BeanListHandler<>(CustomerBean.class));
        // for(CustomerBean item : list){
        //     System.out.println(item);
        // }

        // Class<CustomerBean> customerBeanClass = CustomerBean.class;
        // Constructor<CustomerBean> constructor = customerBeanClass.getConstructor();
        // CustomerBean customerBean = constructor.newInstance();
        // Field[] name = customerBeanClass.getDeclaredFields();
        // for(Field f : name){
        //     f.setAccessible(true);
        //     if(f.getType() == int.class) {
        //         f.set(customerBean, 1);
        //     }else if(f.getType() == String.class){
        //         f.set(customerBean,"1");
        //     }
        // }
        // System.out.println(customerBean);

        // new FileParser().parse(new File("C:\\Users\\leili\\Desktop\\File\\卸货表2022.txt"));

        // String str = "'昌盛船务','宏泰7778','2022-10-09 19:44:49','2022-10-10 15:44:49','2022-10-05 23:44:49','2022-10-09 18:44:49','苏州港','TKNG2924820','YWCM8054','20','南通港','苏州港'";
        // Pattern compile = Pattern.compile("'(.*?)'");
        // Matcher matcher = compile.matcher(str);
        //
        //
        // Class<UnloadBean> unloadBeanBeanClass = UnloadBean.class;
        // Constructor<UnloadBean> constructor = unloadBeanBeanClass.getConstructor();
        // UnloadBean unloadBean = constructor.newInstance();
        // Field[] name = unloadBeanBeanClass.getDeclaredFields();
        //
        // int count = 0;
        // matcher.find();
        // while(true){
        //     if("id".equals(name[count].getName())) {
        //         count++;
        //         continue;
        //     }
        //     Class<?> type = name[count].getType();
        //     if(type == Timestamp.class){
        //         DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //         TemporalAccessor parse = dateTimeFormatter.parse(matcher.group(1));
        //         LocalDateTime from = LocalDateTime.from(parse);
        //         Timestamp timestamp = Timestamp.valueOf(from);
        //         name[count].setAccessible(true);
        //         name[count].set(unloadBean,timestamp);
        //     }else{
        //         name[count].setAccessible(true);
        //         name[count].set(unloadBean,matcher.group(1));
        //     }
        //     count++;
        //     if(!matcher.find()){
        //         break;
        //     }
        // }
        //
        //
        // Connection connection = JdbcUtils.getConnection();
        // System.out.println(unloadBean);
        // new QueryRunner().update(connection,"INSERT INTO UNLOAD VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)",unloadBean.getId(),unloadBean.getShip_company(),unloadBean.getShip_name(),unloadBean.getStart_time(),unloadBean.getEnd_time(),unloadBean.getDeparture_time(),unloadBean.getArrival_time(),unloadBean.getPort(),unloadBean.getLading_number(),unloadBean.getContainer_number(),unloadBean.getContainer_size(),unloadBean.getDeparture(),unloadBean.getDestination());
        // connection.close();
        //
        // DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // LocalDateTime localDateTime = LocalDateTime.parse("2023-07-07 23:50:00", formatter1);
        //
        // DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDate localDate = LocalDate.parse("2023-07-07", formatter2);

    }
}
