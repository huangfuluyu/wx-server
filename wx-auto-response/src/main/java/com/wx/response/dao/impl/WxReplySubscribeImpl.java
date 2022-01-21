package com.wx.response.dao.impl;

import com.wx.response.dao.WxOperationDao;
import com.wx.response.entity.WxReply;

import static com.wx.response.constants.WxReplyConstants.WX_REPLY_SUBSCRIBE;
import static com.wx.response.constants.WxReplyConstants.WX_REPLY_TEXT;

/**
 * @author : HuangFu
 * @Description : WxReplySubscribeImpl
 * @date : 2022-01-21 09:51
 **/
public class WxReplySubscribeImpl implements WxOperationDao {

    @Override
    public WxReply replyMsg(WxReply params) {
        if (params.getEvent().equals(WX_REPLY_SUBSCRIBE)) {
            return new WxReply(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    WX_REPLY_TEXT,
                    "这里是啄儿木鸟电影\n" +
                            "每天更新最新电影资源和资讯\n" +
                            "\n" +
                            "\n" +
                            "没有目的性的观影, 发现电影不一样的美。",
                    null, null
            );
        }
        return null;
    }
}
