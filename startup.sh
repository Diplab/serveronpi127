nohup mvn install exec:java -DskipTests -Dexec.mainClass=com.diplab.webservice.Device127 > my.log 2>&1&
echo $! > save_pid.txt

