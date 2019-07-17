#!/usr/bin/env bash

base_dir=$(cd `dirname $0`/..; pwd)

LOG4J=$base_dir/conf/log4j.properties
if [ -f $LOG4J ]; then
    export LOG4J_PARAMS="-Dlog4j.configuration=file:$LOG4J"
fi

jars=$base_dir/hbase-kerberos-template-1.0-SNAPSHOT.jar
for jar in `ls $base_dir/lib`
do
jars=$jars:$base_dir/lib/$jar
done


cd $base_dir
java_opts="-server -Xms200m -Xmx2048m"

java $java_opts -cp $jars $LOG4J_PARAMS  citic.c.hbase.HbaseUtils  $base_dir/conf/config_prd.properties
