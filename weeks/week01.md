# 第 1 周（D1–D7，10/26–11/1）：环境、语法闭环、OOP 入门 → CourtHub v0.1

本周目标：搭好环境，会断点调试和 Git，把基础语法写熟，做出控制台版 CourtHub v0.1（能预约、取消、检测冲突）。
本周规则：除了环境配置，所有代码自己手写，Sonnet 只讲解、出题、Review。
本周 Lab：Lab0 断点调试 + 栈/堆内存图。

每天开场对 Sonnet 说：`今天是 Day N，读 CLAUDE.md、progress.md 和 weeks/week01.md，按今天的任务带我做。`

---

## D1 周一 10/26（2.5h）环境、摸底、第一次提交
| 时间 | 任务 |
|---|---|
| 0:00–0:40 | 安装 JDK 21（Temurin，机器上已有 JDK 17，保留不删），把 JAVA_HOME 和 PATH 指向 21；安装 IntelliJ IDEA Community；记录 `java -version`、`javac -version`、`git --version` 的真实输出到 notes/day01.md，确认是 21 |
| 0:40–1:05 | 注册 GitHub，`git config user.name/user.email`；本仓库 `git init`，建 `labs/ notes/ bagu/ projects/courthub/`，第一次 commit + push |
| 1:05–1:45 | 闭卷摸底：让 Sonnet 出 15 题（类型、运算、分支、循环、数组、方法），记录分数作为基线 |
| 1:45–2:20 | Lab0-1 断点调试：Sonnet 给一段含 3 个 bug 的循环程序（这是唯一允许它生成的代码），只用断点找 bug。必须用到 Step Over / Step Into / 查看变量 / Evaluate Expression |
| 2:20–2:30 | progress.md 记一行，commit |

验收：GitHub 有仓库；摸底分数已记录；能说出每个 bug 出现在第几次循环、哪个变量值不对。
自测：JDK、JRE、JVM 的关系？`javac` 和 `java` 各做了什么？

## D2 周二（2.5h）变量、运算、分支、循环、方法
代码放 `labs/week01/day02/`，7 题：
1. 闰年判断
2. 成绩转等级（switch，含 JDK 14+ 箭头写法）
3. 打印 1～100 的质数
4. 水仙花数
5. 整数反转（123→321，处理负数、末尾 0，思考溢出怎么办）
6. 猜数字游戏（Random + Scanner + while）
7. 把第 1～5 题抽成 static 方法，main 里调用

重点：int 溢出、整数除法、`i++`/`++i`、`break`/`continue`、方法参数和返回值。
自测：`Integer.MAX_VALUE + 1` 等于多少，为什么？方法里改 int 参数会影响调用方吗？

## D3 周三（2.5h）数组与"算法感"
代码放 `labs/week01/day03/`，6 题：
1. 数组最大、最小、平均值
2. 双指针原地反转数组
3. 冒泡排序 + "一趟没交换就提前结束"
4. 二分查找（while 版 + 递归版）
5. 统计字符串中每个字母出现次数（`int[26]`）
6. 两数之和暴力解（下周会用 HashMap 优化，先感受 O(n²)）

重点：数组默认值、越界异常、二维数组、`Arrays.toString` / `Arrays.sort`。
自测：二分查找为什么要求有序？冒泡的时间复杂度？

## D4 周四（2.5h）OOP 一：类与对象，项目开工
在 `projects/courthub/` 建 IDEA 普通 Java 项目（下周再转 Maven）：
- `Court`：id、name、type（暂用 String）、openHour、closeHour
- `User`：studentId、name
- 用到：private + getter/setter、构造器重载、重写 toString、static 计数器生成 id

Lab0-2 内存图：在纸上画 `Court c = new Court(...)` 时栈和堆里有什么；再画 `Court d = c; d.setName("x");` 后的状态。拍照存 `labs/evidence/lab00-memory.md`，让 Sonnet 只指出错误。

验收：main 里创建 5 个场地、3 个用户并打印。
自测：static 变量和实例变量的区别？构造器能被继承吗？

## D5 周五（2.5h）OOP 二：继承、多态、接口
1. `TimeSlot`：date（先用 String，如 "2026-10-30"）、startHour、endHour
2. 计价接口 `PricingStrategy`，3 个实现：`StudentPricing`（免费）、`OutsiderPricing`（30 元/小时）、`PeakPricing`（18 点后加价 50%）
3. `PriceCalculator` 接收接口类型参数，体会多态
4. 抽象类 `Person`，让 `User` 继承，对比接口和抽象类的用途

自测：接口和抽象类的区别？多态成立的三个条件？

## D6 周六（5h）常用类、枚举、异常 → 交付 v0.1
| 时间 | 任务 |
|---|---|
| 0:00–1:00 | String 常用方法、StringBuilder；包装类与自动装箱（`Integer` 127/128 的 `==` 坑）；`==` vs `equals`。先预测输出再运行 |
| 1:00–1:30 | 枚举：`CourtType`（BADMINTON / BASKETBALL / TABLE_TENNIS）、`BookingStatus`（BOOKED / CANCELLED） |
| 1:30–2:00 | 异常：try/catch/finally，自定义 `BookingException extends RuntimeException` |
| 2:00–2:20 | ArrayList 增删查遍历（会用即可） |
| 2:20–4:30 | 核心：手写 `BookingService` + 控制台菜单（列场地 / 预约 / 取消 / 我的预约）。冲突规则先在纸上画区间图推导 `a.start < b.end && b.start < a.end`，再写代码。规则：场地时段不重叠；同一用户同一时段只能一个；非法时段（start ≥ end、超开放时间、场地不存在）抛异常 |
| 4:30–5:00 | commit，`git tag v0.1` |

验收：能演示 6 个场景：正常预约、场地冲突、用户冲突、取消后可再约、非法时段被拒、查看我的预约。

## D7 周日（5h）Git 进阶、手写测试、周测、反馈
| 时间 | 任务 |
|---|---|
| 0:00–1:00 | Git：新建分支 `feature/price`，把计价接进预约流程（预约成功显示费用）后合并；配 `.gitignore`；故意制造一次冲突并解决 |
| 1:00–2:00 | 手写 `BookingServiceCheck`（if + 打印 PASS/FAIL）覆盖 8 个用例，包括边界：[8,10) 与 [10,12) 不冲突 |
| 2:00–3:00 | Sonnet review 全部代码，自己改 |
| 3:00–4:00 | 闭卷周测 20 题（10 概念 + 5 读代码写输出 + 5 改错） |
| 4:00–4:30 | `notes/week01-summary.md`：用自己的话解释本周 10 个概念 |
| 4:30–5:00 | 让 Sonnet 按模板生成 `feedback/week01.md`，发给导师 |

## 第 1 周通过门槛（满足 4 项及以上进入第 2 周）
- [ ] 能独立用断点定位 bug
- [ ] v0.1 能演示 6 个场景
- [ ] 能在纸上推导区间冲突公式，并说明为什么 [8,10) 和 [10,12) 不冲突
- [ ] 能讲清栈和堆里分别放什么（入门级）
- [ ] 能独立写出接口 + 多个实现 + 多态调用
- [ ] 周测 ≥ 14/20
