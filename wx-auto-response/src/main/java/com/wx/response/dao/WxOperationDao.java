package com.wx.response.dao;

import com.wx.response.entity.WxReply;
import org.springframework.stereotype.Component;

/**
 * @author : HuangFu
 * @Description : WxTextDao
 * @date : 2022-01-20 17:21
 **/
public interface WxOperationDao {
    /**
     * 回复用户消息
     *
     * @param replyText replyText
     * @return WxReplyText
     */
    WxReply replyMsg(WxReply replyText);

}
