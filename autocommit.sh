#! /bin/sh
cd /root/github
/usr/local/bin/git pull
date >> auto-commit.txt
/usr/local/bin/git add *
/usr/local/bin/git commit -m 'auto'
/usr/local/bin/git push
