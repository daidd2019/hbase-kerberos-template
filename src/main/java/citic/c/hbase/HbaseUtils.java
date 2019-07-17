package citic.c.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.KerberosLogin;
import utils.Utils;

import java.io.IOException;
import java.util.Properties;

public class HbaseUtils {
    private static Logger log = LoggerFactory.getLogger(HbaseUtils.class);
    private static String HBASE_NS = "hbaseDB";

    public static void createNS(String hdpVersion, String nameSpace, String propsFile) throws IOException {
        log.info("hbase version " + hdpVersion);
        Configuration conf = Utils.getHbaseConf(hdpVersion, propsFile);
        Connection conn = null;
        Admin admin = null;
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();

        NamespaceDescriptor nsd = NamespaceDescriptor.create(nameSpace).build();
        for(NamespaceDescriptor ns : admin.listNamespaceDescriptors()) {
            log.info(ns.toString());
            if (ns.getName().equals(nameSpace)) {
                log.info("delete " + ns + " namesapce");
                admin.deleteNamespace(ns.getName());
            }
        }
        log.info("create namespace " + nameSpace);
        admin.createNamespace(nsd);
        for(NamespaceDescriptor ns : admin.listNamespaceDescriptors()) {
            log.info(ns.toString());
            if (ns.getName().equals(nameSpace)) {
                log.info("delete " + ns + " namesapce");
                admin.deleteNamespace(ns.getName());
            }
        }


    }
    public static void main(String[] args) throws IOException {
        String props = null;
        if (args.length == 1) {
            log.info("load props " + args[0]);
            props = args[0];

        } else {
            log.info("load default props");
        }

        Properties properties = Utils.getProps(props);
        String keytab = properties.getProperty("hbase.krb.keytab");
        String principal = properties.getProperty("hbase.krb.principal");
        String krb = properties.getProperty("java.security.krb5.conf");

        KerberosLogin kerberosLogin = new KerberosLogin();
        kerberosLogin.KrbLoin(krb, principal, keytab);
        createNS("v3.1", HBASE_NS, props);
        createNS("v2.6", HBASE_NS, props);

    }
}
