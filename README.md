# MTCNN4Android
MTCNN For Android Java<br>
<i>主要参考MTCNN论文和Facenet中MTCNN的实现，纯Java实现。为了方便java调用，我先把npy转为pb。</i>

* 编译环境：Android Studio3.1.2
* 核心类MTCNN用法 (MTCNN.Java)
  * 类实例化 MTCNN mtcnn=new MTCNN(getAssets())
  * 只有1个API：public Vector<Box> detectFaces(Bitmap bitmap,int minFaceSize)
    * 参数bitmap：要处理的图片
    * 参数minFaceSize：最小的脸像素值，一般>=40。越大则检测速度越快，但会忽略掉较小的脸
    * 返回值:所有的脸的Box，包括left/right/top/bottom/landmark(一共5个点，嘴巴鼻子眼)
# 运行效果：
![Alt text](Screenshot_20180626-112620.png) <br>
![Alt text](Screenshot_20180626-112635.png) <br>
![Alt text](Screenshot_20180626-112651.png) <br>
