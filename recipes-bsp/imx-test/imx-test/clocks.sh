#!/bin/bash

# This script is taken directly from the section 5.10 of the Freescale Application Note
# AN4509 and it simple prints the CPU clocks in a nice format

saved_path=$PWD
if ! mount|grep -sq '/sys/kernel/debug'; then
  mount -t debugfs none /sys/kernel/debug
fi

printf "%-24s %-20s %3s %9s\n" "clock" "parent" "use" "flags" "rate"

for foo in $(find /sys/kernel/debug/clock -type d); do
  if [ "$foo" = '/sys/kernel/debug/clock' ]; then
    continue
  fi
  cd $foo
  ec="$(cat usecount)"
  rate="$(cat rate)"
  flag="$(cat flags)"
  clk="$(basename $foo)"
  cd ..
  parent="$(basename $PWD)"
  if [ "$parent" = 'clock' ]; then
    parent=" ---"
  fi
  printf "%-24s %-24s %2d %2d %10d\n" "$clk" "$parent" "$ec" "$flag" "$rate"
  cd $saved_path
done
