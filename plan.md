# CourtHub 100 天计划 v2（骨架 + 硬核底层缝合版）

> 起止：2026-10-26（D1，周一）～ 2027-02-02（D100）
> 时间预算：周一到周五 2.5h/天，周末 5h/天，每周 22.5h，合计约 320h。
> 本文件是总纲；每周的逐日任务在 `weeks/weekNN.md`，由导师（Opus）按周反馈逐周生成。

## 0. 相对 v1 改了什么

保留：CourtHub 校园场馆预约 + AI 预约助手、单项目螺旋演进、周反馈机制、AI 放在 W12–W13。

缝入 Astra 的部分：
- 硬核底层实验线（Lab0～Lab11）：JMM/volatile、手写有界阻塞队列、JVM OOM + jstack/jmap、MySQL 双会话 MVCC 与间隙锁、网络抓包、OS。
- "先预测，再运行，留证据"：每个实验写预测、现象、证据、结论，存到 `labs/evidence/`。
- DB-first 正确性：并发正确性的最终防线是数据库约束和条件更新，Redis/锁只是加速或协调。
- 周通过门槛：每周有一张验收清单，满足大部分才进入下一周；不满足的拆成下周前两天的补课。
- 证据纪律：简历和 README 里的数字只能来自自己跑出的原始结果。

我额外做的调整：
1. 硬核知识"螺旋两次"：入门版放在 W3–W5（先见到现象），深挖版放在 W8–W11（讲清原理并排错）。
   - JMM：W3 见到竞态和可见性 → W8 讲 happens-before、CAS、AQS。
   - MySQL：W4 双会话看隔离级别和 MVCC 快照读 → W9 间隙锁/死锁 → W11 redo/undo/binlog。
   - 网络：W5 抓包看握手和 HTTP → W10 TIME_WAIT、连接池耗尽。
   - jstack：W3 初见死锁线程 → W8 定位线程池问题 → W11 高 CPU/OOM 全套排查。
2. 每个硬核实验都绑定 CourtHub 的真实问题，避免"为了背八股而做实验"。
3. 网络/OS 八股不集中硬背：W5 起每天 2 张八股卡片"滴灌"，W10/W11 再集中做实验。
4. 为了腾时间：前端全部交给 AI 生成；Spring Cloud/ES/Seata 只在 W14 讲概念；"限流 Starter"第二项目改为选做。
5. Agent 写操作的边界（借鉴 Astra 的安全观）：模型只能"提议预约"，生成待确认草稿；真正下单必须由用户点击确认，走普通下单接口和后端鉴权。

## 1. 100 天总大纲

| 周 | 日期 | 主线技术 | 硬核底层实验（Lab） | CourtHub 版本 | 刷题 / 八股 |
|---|---|---|---|---|---|
| W1 | 10/26–11/1 | 环境、语法闭环、OOP 入门、Git | Lab0 断点调试、栈/堆内存图 | v0.1 控制台内存版：预约/取消/冲突检测 | 无 / 每日 2 个自测 |
| W2 | 11/2–11/8 | OOP 进阶、equals/hashCode、不可变对象、集合使用、异常、Maven、JUnit 5 | Lab1 值传递实验；可变 key 让 HashSet"丢数据" | v0.2 分层重构 + 15 个以上单测 | 无 / 每日 2 个自测 |
| W3 | 11/9–11/15 | 泛型 PECS、HashMap 源码、Lambda/Stream、IO + Jackson、线程入门 | Lab2 100 线程抢同一场地复现超占 → synchronized；volatile 可见性 & i++ 丢失更新；jstack 初见死锁 | v0.3 JSON 持久化 + 线程安全 | 热身 LC 1、242 |
| W4 | 11/16–11/22 | MySQL 建表/SQL/索引入门/事务、JDBC | Lab3 双会话复现脏读/不可重复读/幻读；RR 下快照读 vs 当前读（MVCC 初见）；100 万行 EXPLAIN 对比 | v1.0 MySQL 版（唯一约束兜底） | 每周 2 题 |
| W5 | 11/23–11/29 | HTTP、Spring Boot、REST、MyBatis | Lab4 curl -v + Wireshark 抓三次握手/四次挥手；Socket 手写极简 HTTP Server；看 Tomcat 线程 | v2.0 Web API | 每周 4 题 + 网络卡片 |
| W6 | 11/30–12/6 | Spring IoC/AOP/事务、反射 + 动态代理、JWT、统一异常、参数校验；前端 AI 生成 | Lab5 手写 JDK 动态代理；复现 @Transactional 自调用失效、checked 异常不回滚 | v2.1 登录 + 页面 | 每周 4 题 + 网络卡片 |
| W7 | 12/7–12/13 | Redis 数据结构、缓存一致性、验证码登录、Bitmap 存时段余量 | Lab6 缓存穿透/击穿复现；Redis 单线程 + IO 多路复用（select/poll/epoll） | v3 缓存 | 每周 4 题 + OS 卡片 |
| W8 | 12/14–12/20 | JUC 深挖：JMM/happens-before、CAS/ABA/Atomic、AQS、ReentrantLock/Condition、线程池、ThreadLocal | Lab7 手写有界阻塞队列（wait/notify 版 + Condition 版，测试带超时）；线程池拒绝/背压；ThreadLocal 线程池串号；jstack 定位死锁 | v4.0 "早 8 点放号"模拟器，单机并发正确 | 每周 4 题 + 手撕 |
| W9 | 12/21–12/27 | DB-first 并发正确性、MySQL 锁、Redisson、Lua、限流、JMeter | Lab8 区间检查 SELECT…FOR UPDATE 引出间隙锁/next-key lock；复现死锁并读 SHOW ENGINE INNODB STATUS；对比唯一约束/乐观锁/悲观锁/Redisson 四种方案 | v4.1 高并发抢场 + 压测原始数据 | 每周 4 题 + 手撕 |
| W10 | 12/28–1/3 | RabbitMQ（outbox、幂等消费、延迟消息超时释放、DLQ）、候补、Docker Compose | Lab9 TCP 进阶：TIME_WAIT、连接池耗尽复现；消费者 ACK 前崩溃重投验证幂等 | v5.0 异步化 + 一键启动 | 每周 5 题 |
| W11 | 1/4–1/10 | JVM、OS、MySQL 日志与慢 SQL | Lab10 -Xmx64m 制造堆 OOM → heap dump → MAT 找泄漏；栈溢出/元空间 OOM；死循环高 CPU → top/jstack 定位线程；GC 日志；Arthas；redo/undo/binlog 两阶段提交 | v5.1 故障报告 + 优化 | 每周 5 题，八股重点周 |
| W12 | 1/11–1/17 | 大模型 API、LangChain4j、对话记忆、Function Calling、SSE 流式 | 工具调用时序抓日志 | v6.0 AI 预约助手 | 每周 5 题 |
| W13 | 1/18–1/24 | RAG（场馆规则知识库）、待确认草稿、越权防护、降级、调用日志、20 条评测 | Lab11 prompt injection / 越权 / 模型超时测试 | v6.1 Agent 完整版 | 每周 5 题 |
| W14 + D99–100 | 1/25–2/2 | 压测、部署、README、简历、模拟面试、八股串讲（含微服务/分布式概念） | 故障演练答辩 | v7 上线 + 简历定稿 | 二刷错题 / 串讲 |

刷题总目标：D100 前完成 Hot100 中高频约 50 题，D100 后继续补齐。
期末周：在周反馈里写明，导师会把计划顺延，不硬塞。

## 2. CourtHub 业务不变量（贯穿 100 天，所有阶段都必须守住）

1. 同一场地同一日期的两个有效预约，时段不能重叠：`a.start < b.end && b.start < a.end` 即冲突。
2. 同一用户同一时段最多持有 1 个有效预约。
3. 时段必须在开放时间内，且 start < end，以小时为粒度。
4. 预约状态只能合法迁移：BOOKED → CANCELLED / CHECKED_IN / NO_SHOW；终态不可再改。
5. 并发下以上规则仍成立，最终防线在数据库（唯一约束/条件更新/锁），不是 JVM 内存。
6. 重复请求、重复消息不能产生两条预约（幂等）。
7. AI 不能绕过以上任何一条；AI 只能生成待确认草稿。

## 3. 八股卡片日历（每天 2 张，写在 `bagu/` 下，一题一个文件）

| 周 | 主题 |
|---|---|
| W1–W2 | Java 基础：JDK/JRE/JVM、== 与 equals、String 不可变、值传递、重载/重写、接口/抽象类、异常体系、final/static |
| W3 | 集合与泛型：ArrayList 扩容、HashMap put/resize/树化、fail-fast、PECS、类型擦除；线程基础与状态 |
| W4 | MySQL：三范式、索引 B+ 树、聚簇/二级/回表/覆盖、最左前缀、ACID、隔离级别、MVCC 初步 |
| W5–W6 | 网络：分层模型、三次握手/四次挥手、TCP 可靠传输、流量/拥塞控制、HTTP 1.1/2/3、HTTPS 握手、DNS、Cookie/Session/Token、输入 URL 全过程；Spring：IoC、AOP、Bean 生命周期、事务失效 |
| W7 | OS：进程/线程/协程、用户态/内核态、进程通信、IO 模型、select/poll/epoll、零拷贝；Redis 基础 |
| W8 | JUC：volatile、synchronized 锁升级、CAS/ABA、AQS、ReentrantLock、线程池、ThreadLocal、CHM |
| W9 | MySQL 锁（记录锁/间隙锁/临键锁/意向锁/死锁）、分布式锁演进、限流算法 |
| W10 | MQ 可靠性/幂等/顺序/积压；TCP 进阶（TIME_WAIT、粘包）；OS 虚拟内存、页面置换、死锁四条件 |
| W11 | JVM：内存区域、类加载/双亲委派、GC 算法/G1、OOM 类型、排查流程；MySQL redo/undo/binlog、两阶段提交 |
| W12–W13 | AI：Token/上下文、Function Calling 流程、RAG 全链路、chunk 策略、prompt injection、降级 |
| W14 | 微服务/分布式概念（CAP/BASE、注册中心、网关、熔断、分布式事务）+ 全部串讲 |

## 4. 周通过门槛规则

- 每周文件末尾有一张验收清单（通常 6 项）。
- 满足全部：正常进入下一周。
- 满足 4–5 项：进入下一周，未通过项拆成下周前两天各 30 分钟补课。
- 少于 4 项：在周反馈里说明，导师重排下一周（减量，不重做整周）。

## 5. 简历最终形态（W14 产出）

- 项目一：CourtHub 校园场馆预约平台 + AI 预约助手（原创，主项目）。
- 可选项目二：从 CourtHub 抽出的限流/幂等 Spring Boot Starter（W14 时间允许再做）。
- 每条亮点都要回答三问：为什么这样设计、还有什么方案、怎么测出来的。