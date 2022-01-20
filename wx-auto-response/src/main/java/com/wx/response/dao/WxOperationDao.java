package com.wx.response.dao;

import com.wx.response.entity.WxReplyText;

/**
 * @author : HuangFu
 * @Description : WxTextDao
 * @date : 2022-01-20 17:21
 **/
public interface WxOperationDao {
    /**
     * 回复文本消息
     *
     * @return WxReplyText
     */
    WxReplyText replyTextMsg(WxReplyText replyText);
}
