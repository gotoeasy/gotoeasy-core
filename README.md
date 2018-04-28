[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c2203daee8aa4d5daf10e7860f84bbd2)](https://www.codacy.com/app/gotoeasy/gotoeasy-core?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gotoeasy/gotoeasy-core&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/top.gotoeasy/gotoeasy-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/top.gotoeasy/gotoeasy-core)


# `gotoeasy-core`
GotoEasy系列的核心包，没有第三方包的依赖，目的为提供常用功能方便使用。

Maven使用
```xml
<dependency>
    <groupId>top.gotoeasy</groupId>
    <artifactId>gotoeasy-core</artifactId>
    <version>x.y.z</version>
</dependency>
```
Gradle使用
```gradle
compile group: 'top.gotoeasy', name: 'gotoeasy-core', version: 'x.y.z'
```

## 主要内容
```
gotoeasy-core/
├── bus （总线）
├── compiler （动态编译）
├── config （Js配置文件）
├── converter （数据类型转换）
├── log （日志系统动态选择）
├── reflect （反射类）
└── util （工具类）
```

## GotoEasy系列
- `gotoeasy-core` http://github.com/gotoeasy/gotoeasy-core/
- `gotoeasy-aop` http://github.com/gotoeasy/gotoeasy-aop/
- `gotoeasy-rmi` http://github.com/gotoeasy/gotoeasy-rmi/
- TODO
- TODO
- TODO

## LICENSE

    Copyright (c) 2018 ZhangMing (www.gotoeasy.top)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
