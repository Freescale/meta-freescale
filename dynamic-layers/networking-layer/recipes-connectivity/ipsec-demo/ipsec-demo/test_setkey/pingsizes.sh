#!/bin/bash
#
# Usage: ./pingsizes.sh 1440 20 (or greater)
#

PINGDEST=${PINGDEST:-200.200.200.10}
k=$1
lim="$((k+$2))"
((k-=1))
while [ "$k" != "$lim" ] ; do
	echo -n "ping -s $((k+=1)) : "
	ping -i 1000 -c 1 -s $k $PINGDEST | grep packets &
	sleep 1
	PID=`ps -eaf | grep 'ping -i' | grep -v grep | sed 's/[ ][ ]*/ /g' | cut -d " " -f 2`
	if [ -n "$PID" ] ; then
		echo "****************** killing $PID"
		kill $PID > /dev/null
	fi
done
