## 腾讯云独立H5人脸核身对接

[官方文档链接](https://cloud.tencent.com/document/product/1007/35884)

> 对接这种方式，提交用户信息的form表单和认证结果页面你都需要自己编写，还有个要注意的就是通常的ticket的type是SIGN类型，但是获取录制页面用的
ticket是NONCE,这一点也特别注意当初对接的时候害我找了好久


### 首先将用户信息传给腾讯

```html
http://127.0.0.1:8080/send
```

这里会返回一个视频录制的url地址，后端拿到以后传给前端去跳转，用户录制完视频，点击
确定，该页面会自动去调腾讯的视频分析接口，并将结果以一个get请求的方式跳转到我们自己
定义的controller中

```html
http://127.0.0.1:8080/page
```

### 通过页面跳转获取到的orderNo可以去查询详细的响应参数

```html
http://127.0.0.1:8080/result
```