# 定义项目路径
PROJECT_PATH="/home/project/cell-imaging-system/"
# 本地文件路径
LOCAL_FILE_PATH="/Users/narnew/Project/cell-imaging-system/target/cell-imaging-system.jar"
# 远程服务器信息
REMOTE_USER="root"
REMOTE_HOST="47.108.236.160"

#后端启动
#上传
scp  ${LOCAL_FILE_PATH} ${REMOTE_USER}@${REMOTE_HOST}:${PROJECT_PATH}

#重启
ssh -i ~/.ssh/id_rsa ${REMOTE_USER}@${REMOTE_HOST} "cd ${PROJECT_PATH} ;sudo sh restart.sh;tail -fn 200 out.log "



