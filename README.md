# AndroidBitmapOperations
Android bitmap clip, scale, blur, rotate and so on. java and jni realize. 图片裁剪、缩放、模糊、旋转的java和jni实现。
# 1、Bitmap碎片复用的情况下任意裁剪

裁剪讲解博客地址：https://www.jianshu.com/p/329746c1789f

这里说的`碎片复用`就是在图片的`裁剪过程中`会`创建`和`丢弃`大量的`Bitmap对象`，如果不对这些Bitmap进行复用会造成多余的`内存浪费`，造成`内存抖动`。


### 1.1 Bitmap裁剪保留下部分：
|说明         |              前后效果对比              |
|    ----        |                 -----                |
|             裁剪保留下部分，取一半高度      |    ![TIM图片20180228220755.png](http://upload-images.jianshu.io/upload_images/1813550-d95bf47ac2d79ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   **裁剪后：**    ![Screenshot_2018-02-28-21-59-16-052_BitmapKit.png](http://upload-images.jianshu.io/upload_images/1813550-26e1f0c48c4624b7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) |

裁剪代码：
```java
    /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @param recycleSrc 是否回收原图
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapBottom(Bitmap srcBitmap, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留下部分的第一个像素的Y坐标*/
        int needY = srcBitmap.getHeight() - needHeight;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }
```

### 1.2 Bitmap裁剪保留左部分：
|说明         |              前后效果对比              |
|    ----        |                 -----                |
|             裁剪保留左部分，取一半宽度      |    ![TIM图片20180228220755.png](http://upload-images.jianshu.io/upload_images/1813550-d95bf47ac2d79ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   **裁剪后：**   ![Screenshot_2018-02-28-21-59-34-089_BitmapKit.png](http://upload-images.jianshu.io/upload_images/1813550-18ca231f0bea41ab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) |

裁剪代码：
```java
    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapLeft(Bitmap srcBitmap, int needWidth, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapLeft before w : "+srcBitmap.getWidth());

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapLeft after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }
```


### 1.3 Bitmap裁剪保留右部分：
|说明         |              前后效果对比              |
|    ----        |                 -----                |
|             裁剪保留右部分，取一半宽度      |    ![TIM图片20180228220755.png](http://upload-images.jianshu.io/upload_images/1813550-d95bf47ac2d79ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   **裁剪后：**  ![Screenshot_2018-02-28-22-00-03-095_BitmapKit.png](http://upload-images.jianshu.io/upload_images/1813550-db59e75c670a7268.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) |

裁剪代码：
```java
    /**
     * 裁剪一定高度保留左边
     * @param srcBitmap
     * @param needWidth
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapRight(Bitmap srcBitmap, int needWidth, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());

        int needX = srcBitmap.getWidth() - needWidth;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, needX, 0, needWidth, srcBitmap.getHeight());

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }

```


### 1.4  Bitmap裁剪保留上部分：
|说明         |              前后效果对比              |
|    ----        |                 -----                |
|             裁剪保留上部分，取一半高度      |    ![TIM图片20180228220755.png](http://upload-images.jianshu.io/upload_images/1813550-d95bf47ac2d79ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   **裁剪后：**  ![Screenshot_2018-02-28-21-59-49-769_BitmapKit.png](http://upload-images.jianshu.io/upload_images/1813550-b578fd3f5884a3a9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 |

裁剪代码：
```java
   /**
     * 裁剪一定高度保留下面
     * @param srcBitmap
     * @param needHeight
     * @param recycleSrc 是否回收原图
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapTop(Bitmap srcBitmap, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapBottom before h : "+srcBitmap.getHeight());

        /**裁剪保留上部分的第一个像素的Y坐标*/
        int needY = 0;

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap,0,needY,srcBitmap.getWidth(),needHeight);

        Log.d("danxx", "cropBitmapBottom after h : "+cropBitmap.getHeight());

        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }


```

### 1.5 Bitmap指定参数任意裁剪：
|说明         |              前后效果对比              |
|    ----        |                 -----                |
|             指定参数任意裁剪      |    ![TIM图片20180228220755.png](http://upload-images.jianshu.io/upload_images/1813550-d95bf47ac2d79ed0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   **裁剪后：**  ![Screenshot_2018-02-28-22-00-13-606_BitmapKit.png](http://upload-images.jianshu.io/upload_images/1813550-2295ffb4c3b8a4b2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240) |

裁剪代码：
```java
    /**
     * 自定义裁剪，根据第一个像素点(左上角)X和Y轴坐标和需要的宽高来裁剪
     * @param srcBitmap
     * @param firstPixelX
     * @param firstPixelY
     * @param needWidth
     * @param needHeight
     * @param recycleSrc
     * @return
     */
    @DebugLog
    public static Bitmap cropBitmapCustom(Bitmap srcBitmap, int firstPixelX, int firstPixelY, int needWidth, int needHeight, boolean recycleSrc) {

        Log.d("danxx", "cropBitmapRight before w : "+srcBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight before h : "+srcBitmap.getHeight());

        if(firstPixelX + needWidth > srcBitmap.getWidth()){
            needWidth = srcBitmap.getWidth() - firstPixelX;
        }

        if(firstPixelY + needHeight > srcBitmap.getHeight()){
            needHeight = srcBitmap.getHeight() - firstPixelY;
        }

        /**裁剪关键步骤*/
        Bitmap cropBitmap = Bitmap.createBitmap(srcBitmap, firstPixelX, firstPixelY, needWidth, needHeight);

        Log.d("danxx", "cropBitmapRight after w : "+cropBitmap.getWidth());
        Log.d("danxx", "cropBitmapRight after h : "+cropBitmap.getHeight());


        /**回收之前的Bitmap*/
        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(cropBitmap) && !srcBitmap.isRecycled()) {
            GlideBitmapPool.putBitmap(srcBitmap);
        }

        return cropBitmap;
    }

```