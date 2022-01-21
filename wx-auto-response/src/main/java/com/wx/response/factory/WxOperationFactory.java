package com.wx.response.factory;

import com.wx.response.constants.WxReplyConstants;
import com.wx.response.dao.WxOperationDao;
import com.wx.response.dao.impl.WxReplySubscribeImpl;
import com.wx.response.dao.impl.WxReplyTextImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : HuangFu
 * @Description : WxOperationFactory
 * @date : 2022-01-21 11:04
 **/
public class WxOperationFactory {
    static Map<String, WxOperationDao> operationMap = new HashMap<>();

    static {
        operationMap.put(WxReplyConstants.WX_REPLY_TEXT, new WxReplyTextImpl());
        operationMap.put(WxReplyConstants.WX_REPLY_SUBSCRIBE, new WxReplySubscribeImpl());
    }

    public static Optional<WxOperationDao> getOperation(String msgType){
        return Optional.ofNullable(operationMap.get(msgType));
    }
}
