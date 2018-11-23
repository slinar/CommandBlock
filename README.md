# CommandBlock
**开发环境:**<br>
**IDE：** Eclipse Photon Release (4.8.0)<br>
**游戏版本：** MineCraft 1.12.2<br>
**插件类型：** Bukkit<br>

### 描述
有些服主可能想禁用掉一部分命令，或者只让部分玩家执行一些额特殊的命令，或者觉得OP权限太大了想禁用掉部分指令，这样这些命令只能再控制台执行。
这就需要一个插件来屏蔽这些命令。<br>

本插件使用非常简单，把jar丢进服务端的plugins文件夹下然后启动服务端，然后根据需要更改配置文件。<br>

### 配置文件说明
- list下为需要禁用的指令列表，按照yaml语法一条一条添加即可<br>
playerWhiteList下白名单玩家列表，在这个列表下的玩家执行命令将不检查。configUpdateInterval的值为检测配置文件更改的频率（时间单位：秒）。

### 权限和命令
权限: 没有权限对所有玩家生效（包括OP）<br>
命令：只有一个命令：/cb reload - 重载配置（其实这个命令也不需要，本插件支持自动更新配置文件，改完config.yml保存   即可，无需手动重载配置）<br>
