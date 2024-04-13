package top.galaxyrockets.cslabmanagementplatform.generator;

import java.io.File;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

/**
 * @author EnosElinsa
 * @since 2024/04/13
 */
public class CodeGenerator {
    
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig.Builder("jdbc:mysql://galaxyrockets.top:3306/lab?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2b8&allowPublicKeyRetrieval=true",
        "lab", "YWCfbw3NtC5NPFMz")
        .schema("lab")
        .keyWordsHandler(new MySqlKeyWordsHandler())
        .build();

    private static final GlobalConfig GLOBAL_CONFIG = new GlobalConfig.Builder()
       .outputDir(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java")
       .author("EnosElinsa")
       .commentDate("yyyy/MM/dd")
       .build();

    private static final PackageConfig PACKAGE_CONFIG = new PackageConfig.Builder()
       .parent("top.galaxyrockets.cslabmanagementplatform")
       .entity("entity")
       .mapper("mapper")
       .xml("mapper.xml")
       .service("service")
       .serviceImpl("service.impl")
       .controller("controller")
       .build();

    public static void main(String[] args) {
        new AutoGenerator(DATA_SOURCE_CONFIG)
            .global(GLOBAL_CONFIG)
            .packageInfo(PACKAGE_CONFIG)
            .execute();
    }
 
}
