#!/bin/sh


#sudo route -n add -net 10.67.0.0/16  192.168.120.254;
#sudo route -n add -net 192.1.0.0/16 192.1.5.254;
#sudo route -n add -net 192.2.0.0/16 192.1.5.254;
#sudo route -n add -net 192.168.0.0/16 192.1.5.254;
#sudo route -n add -net 10.0.0.0/8 192.1.5.254;
#sudo route -n add -net 172.20.0.0/16 192.1.5.254;




sudo route -n add -net 10.67.0.0/16  192.168.120.254;
sudo route -n add -net 192.1.0.0/16 192.1.123.254;
sudo route -n add -net 192.2.0.0/16 192.1.123.254;
sudo route -n add -net 192.168.0.0/16 192.1.123.254;
sudo route -n add -net 10.0.0.0/8 192.1.123.254;
sudo route -n add -net 172.20.0.0/16 192.1.123.254;

#route add 192.1.0.0 mask 255.255.0.0 192.1.123.254
#route add 192.2.0.0 mask 255.255.0.0 192.1.123.254
#route add 192.168.0.0 mask 255.255.0.0 192.1.123.254
#route add 10.0.0.0 mask 255.0.0.0 192.1.123.254
#route add 172.20.0.0 mask 255.255.0.0 192.1.123.254

