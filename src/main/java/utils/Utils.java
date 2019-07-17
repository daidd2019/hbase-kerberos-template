package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
    private static Logger LOG = LoggerFactory.getLogger(Utils.class);

    private static Configuration hdpv2_6Conf = null;
    private static Configuration hdpv3_1Conf = null;

    public static Properties getProps(String config) throws IOException {
        Properties properties = new Properties();

        if (config == null || config.equals("")) {
            try (InputStream in = Utils.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(in);
            }
        } else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(config));
            properties.load(bufferedReader);
        }
        return properties;
    }

    public static Integer getInt(Properties properties, String key, Integer defaultValues) {
        Integer result = defaultValues;
        try {
            result = Integer.parseInt(properties.getProperty(key));
        }catch (NumberFormatException e) {
            LOG.warn("Error when cast string type to int");
        }catch (Exception e) {
            LOG.warn("get " + key + " Error");
        }
        return result;
    }

    public static Integer getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static Configuration getHbaseConf(String hdpVersion, String props) throws IOException {
         if (hdpVersion.equals("v3.1")) {
            if (hdpv3_1Conf == null) {
                Properties properties = getProps(props);
                hdpv3_1Conf = HBaseConfiguration.create();
                hdpv3_1Conf.addResource(properties.getProperty("hdp.v3.1.hbase.site"));
                hdpv3_1Conf.addResource(properties.getProperty("hdp.v3.1.core.site"));
                hdpv3_1Conf.addResource(properties.getProperty("hdp.v3.1.hdfs.site"));
            }

            return hdpv3_1Conf;
        }
        if (hdpVersion.equals("v2.6")) {
            if (hdpv2_6Conf == null) {
                Properties properties = getProps(props);
                hdpv2_6Conf = HBaseConfiguration.create();
                hdpv2_6Conf.addResource(properties.getProperty("hdp.v2.6.hbase.site"));
                hdpv2_6Conf.addResource(properties.getProperty("hdp.v2.6.core.site"));
                hdpv2_6Conf.addResource(properties.getProperty("hdp.v2.6.hdfs.site"));
            }
            return hdpv2_6Conf;
        }
        return null;
    }
}
