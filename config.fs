[system/vendor/lib/libmtcloader.so]
mode: 0755
user: AID_ROOT
group: AID_ROOT
caps: DAC_OVERRIDE

[system/bin/install-recovery.sh]
mode: 0000
user: AID_SYSTEM
group: AID_SYSTEM

[system/bin/netd]
mode: 0755
user: AID_ROOT
group: AID_SYSTEM
caps: NET_ADMIN NET_BIND_SERVICE NET_RAW

[system/bin/iptables]
mode: 0755
user: AID_ROOT
group: AID_ROOT

[system/bin/ip6tables]
mode: 0755
user: AID_ROOT
group: AID_ROOT

[system/bin/ts_calibrator]
mode: 0755
user: AID_ROOT
group: AID_ROOT
caps: SYS_ADMIN DAC_OVERRIDE

[system/bin/patchoat]
mode: 0755
user: AID_ROOT
group: AID_ROOT
caps: SYS_NICE SYS_RESOURCE
