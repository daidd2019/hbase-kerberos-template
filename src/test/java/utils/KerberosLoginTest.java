package utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther: sponge
 * @Date: 2019/7/5 09:28
 * @Description:
 */
public class KerberosLoginTest {

//    @Test
    public void krbLoinTest() {

        KerberosLogin kerberosLogin = new KerberosLogin();
        kerberosLogin.KrbLoin("src/main/resources/krb5.conf", "hbase", "src/main/resources/develop.keytab");

    }
}