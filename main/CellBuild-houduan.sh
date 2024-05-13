

#后端启动
#上传
# scp /Users/narnew/Project/cell-imaging-system/target/cell-imaging-system.jar root@47.109.33.12:/home/project
#重启
#ssh -i ~/.ssh/id_rsa root@47.109.33.12 "pkill -f 'java'; nohup java -jar /home/project/cell-imaging-system.jar  >/home/project/out.log 2>&1 &;tail -fn 200 /home/project/out.log"

