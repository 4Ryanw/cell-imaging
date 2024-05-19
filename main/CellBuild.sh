# 远程服务器信息
REMOTE_USER="root"
REMOTE_HOST="47.108.236.160"
# 定义项目路径
PROJECT_PATH="/home/project/cell-imaging-system/qianduan"
# 本地文件路径
DIST_PATH="/Users/narnew/Downloads/dist.zip"
#上传：
scp -r ${DIST_PATH} ${REMOTE_USER}@${REMOTE_HOST}:${PROJECT_PATH}
#删除 解压 启动
ssh -i ~/.ssh/id_rsa ${REMOTE_USER}@${REMOTE_HOST} "cd ${PROJECT_PATH}; lsof -t -i:3000 | xargs kill -9; rm -rf dist/; unzip dist.zip;node server.js;"



