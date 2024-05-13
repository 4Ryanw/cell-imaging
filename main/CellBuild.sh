#上传：
scp -r /Users/narnew/Downloads/dist.zip root@47.109.33.12:/home/project/qianduan
#删除 解压 启动
ssh -i ~/.ssh/id_rsa root@47.109.33.12 "cd /home/project/qianduan/; rm -rf dist/; unzip dist.zip;node server.js;"



