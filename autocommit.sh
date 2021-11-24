#! /bin/sh
cd /root/github
git pull
date >> auto-commit.txt
git add *
git commit -m 'auto'
git push
