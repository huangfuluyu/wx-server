package com.wx.response.controller;

import cn.hutool.crypto.SecureUtil;
import com.wx.response.dao.WxOperationDao;
import com.wx.response.entity.WxReply;
import com.wx.response.factory.WxOperationFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : HuangFu
 * @Description : responseController
 * @date : 2022-01-10 15:17
 **/
@RestController
public class WxReplyController {


    /**
     * 验证微信配置
     *
     * @param request request
     * @return String
     */
    @GetMapping("/wechat")
    public String checkWeChat(HttpServletRequest request) {
        try {
            if (request == null) {
                return "request is null!!";
            }
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            String token = "luyu2021";
            //获得排序之后的列表
            List<String> list = Stream.of(token, timestamp, nonce).sorted().collect(Collectors.toList());
            String formatList = String.join(",", list).replace(",", "");
            //  哈希算法加密formatList得到hashcode
            String hashCode = SecureUtil.sha1(formatList);
            if (!String.valueOf(hashCode).equals(signature)) {
                return "hashCode != signature!!";
            }
            return echostr;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "200";
    }

    /**
     * 回复用户消息
     *
     * @param params 微信规定xml格式
     * @return 回复用户内容
     */
    @PostMapping(value = "/wechat", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public WxReply consumeText(@RequestBody WxReply params) {
        WxOperationDao operation = WxOperationFactory.getOperation(
                params.getMsgType()).orElseThrow(RuntimeException::new);
        return operation.replyMsg(params);
    }


}
