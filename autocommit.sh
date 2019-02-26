#! /bin/sh
date >> auto-commit.txt
git add *
git commit -m 'auto'
git push
