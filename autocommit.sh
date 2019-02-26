#! /bin/sh
cd /root/github
date >> auto-commit.txt
git add *
git commit -m 'auto'
git push
