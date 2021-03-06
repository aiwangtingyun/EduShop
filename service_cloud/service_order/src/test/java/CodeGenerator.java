import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * @author 王廷云
 * @since 2020/07/25
 */
public class CodeGenerator {

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");    // 获取工程模块路径
        gc.setOutputDir(projectPath + "/src/main/java");        // 设置代码生成路径
        gc.setAuthor("王廷云");
        gc.setOpen(false);                  //生成后是否打开资源管理器
        gc.setFileOverride(false);          //重新生成时文件是否覆盖
        gc.setServiceName("%sService");	    //去掉Service接口的首字母I
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略
        gc.setDateType(DateType.ONLY_DATE); //定义生成的实体类中日期类型
        gc.setSwagger2(true);               //开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/edushop?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("199583");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.wang");           // 生成的包名
        pc.setModuleName("eduorder");       // 生成的模块名
        pc.setController("controller");     // 生成的包名：com.wang.eduservice.controller
        pc.setEntity("entity");             // 生成的包名：com.wang.eduservice.entity
        pc.setService("service");           // 生成的包名：com.wang.eduservice.service
        pc.setMapper("mapper");             // 生成的包名：com.wang.eduservice.mapper
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("t_order", "t_pay_log");                // 表名
        strategy.setNaming(NamingStrategy.underline_to_camel);      // 数据库表映射到实体的命名策略
        strategy.setTablePrefix("t" + "_");                         // 生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true);                        // lombok模型@Accessors(chain = true) setter链式操作
        strategy.setRestControllerStyle(true);                      // restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true);             // url中驼峰转连字符

        mpg.setStrategy(strategy);


        // 6、执行
        mpg.execute();
    }
}
