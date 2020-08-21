# 开发环境以及工具配置

* [github-auto-commit](#github-auto-commit)
* [git代理设置](#git_proxy)
* [on_my_zsh](#on_my_zsh)
* [修改时区, 系统时间](#system_local_time)

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
## git_proxy
`git global 配置文件位置: C:\Users\用户名\.gitconfig`

```bash
git config --global http.proxy 'http://127.0.0.1:1080'
git config --global https.proxy 'http://127.0.0.1:1080'
git config --global http.proxy 'socks5://127.0.0.1:1080'
git config --global https.proxy 'socks5://127.0.0.1:1080'
```

## on_my_zsh
安装一些必要的东西
```bash
yum install -y git zsh vim wget curl nginx net-tools epel-release
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

# 修改主题
vim ~/.zshrc
agnoster

# 更新ob_my_zsh
upgrade_oh_my_zsh
```

## system_local_time
```bash
rm -rf /etc/localtime
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ntpdate ntp1.aliyun.com
```