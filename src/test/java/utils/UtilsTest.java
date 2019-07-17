package utils;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void getHbaseConf() {
        Configuration conf = null;
        try {
            conf = Utils.getHbaseConf("v3.1", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(conf);
    }
}