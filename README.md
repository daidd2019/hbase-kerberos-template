## hbase client 同时连接hdp2.6 与 hdp3.1
    tar -zxf hbase-kerberos-template-1.0-SNAPSHOT-release.tar.gz
## 验证问题
    当插入hbase数据时region所在的节点重启，客户端程序是否继续插入
    
    结果： 客户端程序报错后，继续插入，此时region已经转移到其他主机
    data inserted
    2019-07-30 16:59:54,094 WARN [main-SendThread(gp-node-4:2181):83537] Session 0x36be4eaf2ca0218 for server gp-node-4/10.247.33.135:2181, unexpected error, closing socket connection and attempting reconnect
    java.io.IOException: 远程主机强迫关闭了一个现有的连接。
    	at sun.nio.ch.SocketDispatcher.read0(Native Method)
    	at sun.nio.ch.SocketDispatcher.read(SocketDispatcher.java:43)
    	at sun.nio.ch.IOUtil.readIntoNativeBuffer(IOUtil.java:223)
    	at sun.nio.ch.IOUtil.read(IOUtil.java:192)
    	at sun.nio.ch.SocketChannelImpl.read(SocketChannelImpl.java:380)
    	at org.apache.zookeeper.ClientCnxnSocketNIO.doIO(ClientCnxnSocketNIO.java:68)
    	at org.apache.zookeeper.ClientCnxnSocketNIO.doTransport(ClientCnxnSocketNIO.java:366)
    	at org.apache.zookeeper.ClientCnxn$SendThread.run(ClientCnxn.java:1125)
    2019-07-30 16:59:54,279 WARN [hconnection-0x3967e60c-metaLookup-shared--pool2-t2:83722] Possibly transient ZooKeeper, quorum=gp-node-3:2181,gp-node-2:2181,gp-node-4:2181, exception=org.apache.zookeeper.KeeperException$ConnectionLossException: KeeperErrorCode = ConnectionLoss for /hbase/meta-region-server
    2019-07-30 16:59:54,778 INFO [main-SendThread(gp-node-2:2181):84221] Opening socket connection to server gp-node-2/10.247.33.133:2181. Will not attempt to authenticate using SASL (unknown error)
    2019-07-30 16:59:54,800 INFO [main-SendThread(gp-node-2:2181):84243] Socket connection established to gp-node-2/10.247.33.133:2181, initiating session
    2019-07-30 16:59:54,815 INFO [main-SendThread(gp-node-2:2181):84258] Session establishment complete on server gp-node-2/10.247.33.133:2181, sessionid = 0x36be4eaf2ca0218, negotiated timeout = 60000
    2019-07-30 17:00:16,462 INFO [main:105905] , tableName=student
    2019-07-30 17:00:16,462 INFO [main:105905] Left over 2 task(s) are processed on server(s): [gp-node-4,16020,1564389994235, gp-node-1,16020,1564389992813]
    2019-07-30 17:00:16,462 INFO [main:105905] Regions against which left over task(s) are processed: [student,,1564477115317.a4e540ea919574a7ac294a0e43f786ac.]
    data inserted

    
    
    
## 遗留问题
    配置文件v2.6 和 v3.1 打包到jar里面，以后更新配置文件，需要替换jar中的内容后重启服务。

