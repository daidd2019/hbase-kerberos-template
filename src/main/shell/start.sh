#!/usr/bin/env bash

base_dir=$(cd `dirname $0`/..; pwd)

if [ -f $base_dir/server.pid ] ; then
  pid=`cat $base_dir/server.pid`
  ps aux  | awk '{print $2}' | grep $pid > /dev/null
  if [ $? -eq 0 ] ; then
  echo "$pid exists ..."
  exit 0
  fi
fi

LOG4J=$base_dir/conf/log4j.properties
if [ -f $LOG4J ]; then
    export LOG4J_PARAMS="-Dlog4j.configuration=file:$LOG4J"
fi

jars=$base_dir/conf/core-site.xml:$base_dir/conf/hdfs-site.xml:$base_dir/@project.artifactId@-@project.version@.jar
for jar in `ls $base_dir/lib`
do
jars=$jars:$base_dir/lib/$jar
done

java_opts="-server -Xms200m -Xmx2048m"

echo "start server .."
nohup java $java_opts -cp $jars $LOG4J_PARAMS citic.c.hbase.HbaseUtils $base_dir/conf/config.properties >> $base_dir/server.log 2>&1  &

echo $! > $base_dir/server.pid
