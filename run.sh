# check and kill xistance of current flask process
ps -ef | grep java | awk '{print $2}' | xargs kill -9

# start python flask
nohup java -jar springboot1-0.0.1-SNAPSHOT.jar > /home/peng/springboot.log 2>&1 &