#上传：
scp -r /Users/narnew/Downloads/dist.zip root@47.109.33.12:/home/project/cell-imaging-system/qianduan
#删除 解压 启动



ssh -i ~/.ssh/id_rsa root@47.109.33.12 "cd /home/project/cell-imaging-system/qianduan; lsof -t -i:3000 | xargs kill -9; rm -rf dist/; unzip dist.zip;node server.js;"



