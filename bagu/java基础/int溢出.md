# Integer.MAX_VALUE + 1 等于多少，为什么？

## 我的理解

等于最小的负数（`Integer.MIN_VALUE`）。因为正溢出了，会反方向走；反过来同理，`Integer.MIN_VALUE - 1` 等于最大的正数。

## 关键点

溢出了就会反方向绕回去。

## 容易混淆的地方

（待补充）
