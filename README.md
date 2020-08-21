# 开发环境以及工具配置

## github-auto-commit
`在linux服务器上面配置git并且每日提交, 让github绿油油`

```bash
# 安装git
yum install -y git

# 配置git用户名&邮箱
git config --global user.name "用户名"
git config --global user.email "邮箱"

# 生成秘钥
ssh-keygen
# 把/root/.ssh下面id_rsa.pub里的内容复制到github里

# 进入 /root/github文件夹clone本项目, 并且手动push一次
git clone git@github.com:kenshirokk/auto-commit.git .
chmod +x autocommit.sh
./autocommit.sh

# 定时任务
crontab -e
0 6 * * * sh /root/github/autocommit.sh
分(0-59) 时(0-23) 几号(1-31) 月份(1-12) 星期几(0-6)//0代表周日
```
