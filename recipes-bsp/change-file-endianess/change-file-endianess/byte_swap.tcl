puts $argv
set i_file [lindex $argv 0]
set o_file [lindex $argv 1]
set num_b  [lindex $argv 2]
puts ""

set fileid_i [open $i_file "r"]
set fileid_o [open $o_file "w+"]
fconfigure $fileid_i -translation {binary binary}
fconfigure $fileid_o -translation {binary binary}

set old_bin [read $fileid_i]
set new_bin {}
set old_length [string length $old_bin]
set old_rem [expr $old_length % $num_b]
if {$old_rem != 0} {
	for {set i 0} {$i< [expr $num_b - $old_rem]} {incr i 1} {
	        append old_bin y
	}
}
for {set i 0} {$i<[expr $old_length-8]} {incr i $num_b} {
        for {set j $num_b} {$j>0} {incr j -1} {
                append new_bin [string index $old_bin [expr $i+($j-1)]]
        }
}

for {set j 0} {$j<8} {incr j 1} {
              append new_bin [string index $old_bin [expr $i+$j]]
}

for {set i 0} {$i<[string length $old_bin]} {incr i $num_b} {
        set binValue [string range $old_bin [expr $i+0] [expr $i+($num_b-1)]]
        binary scan $binValue H[expr $num_b*2] hexValue

        set binValue [string range $new_bin [expr $i+0] [expr $i+($num_b-1)]]
        binary scan $binValue H[expr $num_b*2] hexValue
}

puts -nonewline $fileid_o $new_bin
close $fileid_o
