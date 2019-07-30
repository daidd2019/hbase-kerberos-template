package citic.c.hbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HbaseInsert {
    private static Logger log = LoggerFactory.getLogger(HbaseInsert.class);

    public static void main(String[] args) throws IOException {

        Integer rows  = 100000000;
        if (args.length == 1) {
            log.info("insert rows " + args[0]);
            rows = Integer.parseInt(args[0]);

        } else {
            log.info("insert default rows " + rows);
        }

        HbaseUtility.init();
        String[] col1 = new String[] { "name", "age" ,"chinese", "math" };
        String[] val1 = new String[] { "xx", "18" ,"60", "70" };


        HbaseUtility.droptable("student");
        HbaseUtility.createtable("student", "imformation", "score");

        for (int i = 0; i < rows; i ++) {
            HbaseUtility.insertdata("student", String.valueOf(i), "imformation", col1, val1);
        }
        HbaseUtility.scantable("student");

        HbaseUtility.droptable("student");
    }

}