# Lab0-1 断点调试实验

## 预测
运行前看到的错误输出：
```
sum = 357
passCount = 3
riseCount = 0
```

当时猜测：没有发现 bug，但运行后对比期望输出，发现了三个问题：
- `passCount = 3` 可能是因为 `isPass()` 函数用的是 `> 60` 而不是 `>= 60`
- `sum = 357` 是因为没有加上 58，循环条件应该用 `<=` 而不是 `<`
- `riseCount = 0` 是因为每次都在跟自己比，而不是跟前一个数比

## 实际现象
调试时发现的三个 bug：
- **Bug 1**：`isPass()` 方法第 13 行，把 `score > 60` 改成 `score >= 60`
- **Bug 2**：`for` 循环第 24 行，条件改为 `i <= scores.length - 1`（或 `i < scores.length`）
- **Bug 3**：第 34 行 `prev = s` 应该放在 `if (s > prev)` 结构的外面

## 证据
**运行前的错误输出：**
```
sum = 357
passCount = 3
riseCount = 0
```

**调试时观察到的关键变量值：**
- Bug 2（循环条件）：修改前在 `i = 4` 时就退出循环，没有执行 `i = 5`，导致 `scores[5] = 58` 没有被加入
- Bug 3（prev 位置）：修改前 `prev = s` 在 if 里面，只有满足 `s > prev` 才更新。第二次循环后应该 `prev = 42`，但因为 42 < 85 不满足条件，prev 不会更新，导致后续比较都失效

**Evaluate Expression 验证：**
- 第一次循环（`i = 0`）时：`s = 85`, `prev = 85`, `s > prev = false`

**修改后的正确输出：**
```
sum = 415
passCount = 4
riseCount = 2
```

## 原因
- **Bug 1**：判断合格的时候忽略了 `= 60` 这个条件，导致 60 分会被漏判
- **Bug 2**：for 循环的条件是 `i < scores.length - 1`（等价于 `i <= 4`），只循环了 5 次（i=0,1,2,3,4），但数组有 6 个元素（下标 0～5），少循环了 1 次，漏掉了最后一个元素 `scores[5] = 58`
- **Bug 3**：`prev = s` 应该放在判断 riseCount 之后（if 结构外面），而不是之前也不是之中。如果放在之前，每次循环 prev 都会等于 s，导致 `s > prev` 永远是 false，riseCount = 0；如果放在 if 里面（之中），不能及时更新 prev，只有满足 `s > prev` 时才更新，不符合题意（下一个数大于前一个数）

## 结论
通过这个实验我学到了：
1. 光看代码推算结果不准确，我第一次预测的结果全错，说明我的代码追踪能力还有问题
2. 断点调试可以一步步看运行结果，比盲猜更可靠
3. 四个调试操作都用到了：Step Over（F8）逐行执行、Smart Step Into（Shift+F7）进入方法、查看变量面板观察值的变化、Evaluate Expression（Alt+F8）计算表达式 `s > prev`
