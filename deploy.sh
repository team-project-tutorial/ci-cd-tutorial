#!/bin/bash
# rm -rf /home/ec2-user/board-app # s3 artifact 다운로드로 flow를 변경함에 따라 불필요
# mkdir /home/ec2-user/board-app # s3 artifact 다운로드로 flow를 변경함에 따라 불필요
# mv /home/ec2-user/deploy/board-app /home/ec2-user # s3 artifact 다운로드로 flow를 변경함에 따라 불필요
cd /home/ec2-user/board-app
sudo fuser -k -n tcp 8080 || true
nohup java -jar /home/ec2-user/board-app/app.jar > application-log 2>&1 &
# rm -rf /home/ec2-user/deploy # s3 artifact 다운로드로 flow를 변경함에 따라 불필요
