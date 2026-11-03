# HANDOFF：交接说明（新会话请先读这个文件）

## 这是什么
一个 100 天学习计划仓库：学生从"只会 Java 基础语法"开始，学到 Java 后端 + AI Agent，主项目是 CourtHub（校园场馆预约平台 + AI 预约助手）。
计划由导师模型制定；你是课堂老师，负责按计划逐日带学生完成任务。

## 阅读顺序
1. `CLAUDE.md`：课堂规则（必须遵守，尤其是"第 1～3 周不替学生写代码"和"证据纪律"）
2. `plan.md`：100 天总大纲、业务不变量、八股日历、周通过门槛
3. `weeks/week01.md`：本周逐日任务（week02、week03 是预排版，会按周反馈调整）
4. `progress.md`：已完成到哪一天
5. `templates/weekly-feedback.md`：每周日生成周反馈用

## 当前状态（2026-10-28 交接时）
- 计划开始日：2026-10-26（D1）。学生尚未正式完成 D1，从 D1 开始即可，日期顺延。
- 已安装：JDK 17.0.20（Temurin）、Git 2.47.1。
- 计划统一使用 JDK 21（Temurin）。JDK 21 尚未安装：D1 第一步由课堂老师协助安装，安装后把 JAVA_HOME 和 PATH 指向 JDK 21，用 `java -version` 和 `javac -version` 确认输出 21；原有 JDK 17 保留不删。
- 未安装：Maven（第 2 周 D8 才需要；IDEA 自带 Maven 也可）、IntelliJ IDEA（D1 安装）。
- 本目录还不是 git 仓库（D1 任务里执行 `git init`）。
- `labs/ notes/ bagu/ projects/courthub/` 目录尚未创建（D1 任务）。

## 最新状态（2026-10-28，导师 Opus 写）
- D1 已于 2026-10-28 开课，进行到第 1 步（安装 JDK 21）。已给学生 winget / MSI 两种安装方式，等学生贴出 `java -version`、`javac -version`、`git --version`、`where.exe java`、`echo $env:JAVA_HOME` 的原始输出。
- 开课前让学生预测过："装完 21 不改设置，`java -version` 显示 17 还是 21？"拿到输出后先对比这个预测。
- 已安装 skill `grilling`（追问式对齐，来自 mattpocock/skills），可在设计决策时使用。没有安装 ponytail 和 teach，因为它们和本课规则冲突。

## 每周循环
1. 周一到周六：按 `weeks/weekNN.md` 逐日上课，每天结束更新 `progress.md` 并 commit。
2. 周日：按模板生成 `feedback/weekNN.md`。
3. 学生把周反馈交给导师，导师生成下一周的 `weeks/weekNN.md`。课堂老师不要自行编写后续周计划，最多提出调整建议。