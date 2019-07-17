#!/usr/bin/env bash

base_dir=$(cd `dirname $0`/..; pwd)

if [ -f $base_dir/server.pid ] ; then
  pid=`cat $base_dir/server.pid`
  ps aux  | awk '{print $2}' | grep $pid > /dev/null
  if [ $? -eq 0 ] ; then
  echo "kill $pid "
  kill -9 $pid
  fi
fi

