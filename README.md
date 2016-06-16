# BottomViewPagerIndicator

**A library for custom bottom indicator with viewpager.**

**为Viewpager方便的设置显示指示器的库，在Banner，Welcome页面比较常用**

### 效果图

![文字效果](https://github.com/gaoyan10/BottomViewPagerIndicator/raw/master/images/indicator_text.png)

![图片效果](https://github.com/gaoyan10/BottomViewPagerIndicator/raw/master/images/indicator_image.png)

## Usage

### Step1



### Step2

在layout中使用如下


```

<merge xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    <com.yan.view.CustomBottomPageIndicator
        android:id="@+id/indicator"
        android:gravity="bottom|center_horizontal"
        app:indicator_spacing="5dp"  # 当Type是图片的时候的间隔
        app:indicator_type="text"   #type是文字或者image图片
        app:indicator_textColor="@android:color/white" # type是文字时的文字颜色
        app:indicator_textSize="15sp" # type是文字时的文字尺寸
        app:indicator_unSelectDrawable="@drawable/ic_welcome_transparent" #未选中时的图
        app:indicator_selectDrawable="@drawable/ic_welcome_white"
        android:layout_marginBottom="20dp"  #选中时的图
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</merge>


```

### Step3

在代码中设置CustomBottomPageIndicator和ViewPager的关联

```

indicator = (CustomBottomPageIndicator) findViewById(R.id.indicator);
indicator.setViewPager(mViewPager);


```

License
=======

    Copyright (c) 2016 Yan Gao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

