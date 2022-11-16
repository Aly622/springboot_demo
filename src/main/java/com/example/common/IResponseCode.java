package com.example.common;

/**
 * @InterfaceName IBusinessCode
 * @Desc 通用错误信息类
 * @Author Oliver.Liu
 * @Date 2019/6/15 21:03
 * @Version 1.0
 * 1xx：请求收到，继续处理
 * 2xx：操作成功收到，分析、接受
 * 3xx：完成此请求必须进一步处理
 * 4xx：请求包含一个错误语法或不能完成
 * 5xx：服务器执行一个完全有效请求失败
 **/
public interface IResponseCode {
    long getCode();

    String getMessage();
}
