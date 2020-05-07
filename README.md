# yunxin-utils

为云兴也为自己整理的一些实用常用的函数吧. 基本上分为以下几大类：
- `Work.Bytes`：   与字节集、自定义二进制结构体等有关
- `Work.Security`:    与加解密有关。 RSA、AES、DES、TEA等快捷方法
- `Work.Compression`:  与压缩解压缩有关。 zip、gzip等功能方法。
- `Work.Http`:    与http请求有关。 get、post等功能方法。
- `Work.Reflection`:  与反射有关。
- `Work.concurrent`:  与多线程有关。 一些并发任务的快捷使用。


## `Work.Bytes`类
    这个类基本上整理与字节集有关的所有有用的函数。
   
---
##### 1. 字节集与16进制字符串的互转
``` java
    byte[] data = Work.Bytes.bytes("10 20 30 40");
    String hex = Work.Bytes.hex(data);
    // hex 就是字符串 "10 20 30 40"
```

##### 2. 原始数据类型与字节集的互转
    

`Work.Bytes.bytesOf`**XXXX**() 是一组将XXX转化为bytes字节集的方法集合。

`Work.Bytes.bytes2`**XXXX**() 是一组将字节集转化为XXXX的方法集合。
    
    
``` java
    
    // public byte[] bytesOfXXXX();
    Work.Bytes.bytesOfBMPImage(null);
    Work.Bytes.bytesOfInt(1);  
    Work.Bytes.bytesOfLong(1l);
    Work.Bytes.bytesOfShort((short) 1);
    Work.Bytes.bytesOfUInt(1l);
    Work.Bytes.bytesOfUShort(1);
    Work.Bytes.bytesOfUByte((short) 1);
    Work.bytes.bytesOfIpV4("192.168.1.1");
    
    // public T bytes2XXXX(byte[] data)
    
    Work.Bytes.bytes2SignedInt(null);
    Work.Bytes.bytes2SignedLong(null);
    Work.Bytes.bytes2SignedShort(null);
    Work.Bytes.bytes2UnSignedByte(null);
    Work.Bytes.bytes2UnSignedShort(null);
    Work.Bytes.bytes2UnSignedInt(null);

```
##### 3. 与字节集相关的一些基本操作

- `byte[] Work.Bytes.union(byte[] ...)`; //合并一组字节集
- `byte[] Work.Bytes.flip(byte[] )`; //翻转字节集
- `byte[] Work.Bytes.left(byte[], int n)`;  //取字节集左边n位
- `byte[] Work.Bytes.right(byte[], int n)`;  //取字节集右边n位
- `boolean Work.Bytes.equals(byte[], byte[])`; //两个字节集是否相等
- `boolean Work.Bytes.equals(byte[]...)`;  //一组字节集是否相等
- `byte[] Work.Bytes.bytesWithFills(int size, byte fill)`; // 用fill填充生成指定长度的字节集
- `byte[] Work.Bytes.random(int size)`; //生成指定长度的随机字节集
- `byte[] Work.Bytes.endWiths(byte[],byte[])` ; // 前一个字节集是否以后一个字节集结尾
- `byte[] Work.Bytes.startWiths(byte[],byte[])`; // 前一个字节集是否以后一个字节集开头
- `byte[] Work.Bytes.indexOf(byte[],byte[],int)`; // 在第一个字节集中从指定位置查找第二个字节集开始的位置
- `byte[] Work.bytes.skip(byte[],int)` ; //忽略前指定长度后剩下的字节集

##### 4. 与自定义数据结构有关的基本操作

- `Pack Work.Bytes.pack()` 方法构造一个`Pack`对象来完成二进制字节结构的设计，如下是一个简单的例子：
``` java
        Pack pack = Work.Bytes.pack();  // 初始化一个Pack对象
        pack.setInt(4);     // 添加一个整型，4字节
        pack.setShort((short) 2);  // 添加一个短整型，2字节
        pack.setByte((byte) 1);    // 添加一个字节，1字节
        pack.setBin(Work.Bytes.bytes("01 02 03 04 05"));  // 添加任意长度的字节
        pack.setStr("a string");   // 添加字符串
        byte[] packedData = pack.getAll();   // 最后打包
```
-`Pack Work.Bytes.pack(byte[])` 当然也可以使用该方法对新Pack对象做初始化，然后接着往后添加。 


再比如下面的代码让字符串可以出现在二进制体中的任意位置，因为加了一个长度。
当然，这个长度是2字节还是4字节或更长，那就看设计需要了。
``` java
        String msg = "hello world";
        Pack pack = Work.Bytes.pack();
        pack.setShort((short) msg.length());
        pack.setStr(msg);
        byte[] packedData = pack.getAll();
```
- `byte[] Work.Bytes.ld2Data(byte[])`这个函数则快速的完成了上面代码的功能。
我把它统称为ld行数据，length-delimited，由长度确定分割的。而这里的ld2则是两个字节。
- `byte[] Work.Bytes.ld4Data(byte[])`则是长度为4字节大头的ld数据。

-`byte[] Work.Bytes.tlv2(short type,byte[] data) ` 这个则是tlv行数据了，长度为2字节哦。
``` java
    byte[] tlvData = Work.Bytes.tlv2(1,Work.Bytes.hex("01 02 03"));
    // 其实是如下的数据
    //type:  0x00 0x01
    // len:  0x00 0x03
    // val:  0x00 0x02 0x03
```

-`byte[] Work.Bytes.tlv4(short type,byte[] data) ` 同理还有长度为4字节的tlv





