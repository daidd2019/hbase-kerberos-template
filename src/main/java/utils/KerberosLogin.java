package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class KerberosLogin {
    private static Logger LOG = LoggerFactory.getLogger(KerberosLogin.class);

    private static UserGroupInformation ugi = null;
    private String krbFile = null;
    private String principal  = null;
    private String keytabPath;

    private void cleanParams() {
        this.krbFile = null;
        this.principal = null;
        this.keytabPath = null;
        if (ugi != null) {
            try {
                ugi.logoutUserFromKeytab();
            } catch (IOException e) {
                LOG.warn("logout user from keytab error");
            }
            ugi = null;
        }

    }

    public boolean KrbLoin(String krbFile, String principal, String keytabPath) {

        if (krbFile == null || principal == null || keytabPath == null) {
            LOG.error("krbFile principal and keytabPath is null");
            return false;
        }
        Configuration conf = new Configuration();

        System.setProperty("java.security.krb5.conf", krbFile);
        LOG.debug("krb5.conf is " + krbFile);
        conf.set("hadoop.security.authentication", "kerberos");

        UserGroupInformation.setConfiguration(conf);
        try {
            ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytabPath);
            LOG.info("ugi.getUserName() " + ugi.getUserName());
            UserGroupInformation.setLoginUser(ugi);
            LOG.info("Kerberos Certification success !!!");
            this.krbFile = krbFile;
            this.principal = principal;
            this.keytabPath = keytabPath;
        }
        catch (IOException e) {
            LOG.error("Kerberos certification failed", e);
            this.cleanParams();
            return false;
        }
        return true;
    }

    public void reLogin() {
        if (ugi == null) {
            KrbLoin(this.krbFile, this.principal, this.keytabPath);
        } else {
            try {
                ugi.checkTGTAndReloginFromKeytab();
            } catch (IOException e) {
                LOG.error("Kerberos certification failed", e);
            }
        }
    }
}
