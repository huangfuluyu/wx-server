package com.wx.response.controller;

import cn.hutool.crypto.SecureUtil;
import com.wx.response.entity.WxCloudDisk;
import com.wx.response.entity.WxReplyText;
import com.wx.response.mapper.WxCloudDiskMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WxCloudDiskMapper linkMapper;

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

    @PostMapping(value = "/wechat", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public WxReplyText consumeText(@RequestBody WxReplyText params) {

        //根据用户发送的关键词获取整个资源信息
        val cloudDisk = linkMapper.selectByKeyWords(params.getContent());

        if (cloudDisk == null) {
            return new WxReplyText(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    "\uD83E\uDD76抱歉,暂无该资源..." + "\n" +
                            "\uD83D\uDC47或许你喜欢" + "\n" + "\n" +
                            getRandomData()
                    , null
            );
        } else {
            return new WxReplyText(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    getRandomData(cloudDisk),
                    null
            );
        }
    }

    public String getRandomData(){
        val listByType = linkMapper.listSelectTop5();
        StringBuffer sb = new StringBuffer();
        sb.append("\uD83E\uDD29[感谢关注! 感谢分享公众号]\n");
        return getString(sb, listByType);

    }

    public String getRandomData(WxCloudDisk cloudDisk) {
        val listByType = linkMapper.listSelectByDataType(cloudDisk.getCloudType());
        StringBuffer sb = new StringBuffer();
        sb.append("\uD83E\uDD29[感谢关注! 感谢分享公众号]\n");
        if (listByType == null) {
            return "[感谢关注! 感谢分享公众号]\n" +
                    cloudDisk.getCloudTitle() + "\n" +
                    "链接：" + cloudDisk.getCloudLink() + "\n" +
                    "提取码：" + cloudDisk.getCloudCode() + "\n" +
                    "有链接失效请私信！！会及时修正！";
        } else {
            return getString(sb, listByType);
        }
    }


    private String getString(StringBuffer sb, List<WxCloudDisk> listTop) {
        listTop.forEach(o -> sb.append(o.getCloudTitle()).append("【").append(o.getCloudName()).append("】").append("\n")
                .append("链接：").append(o.getCloudLink()).append("\n")
                .append("提取码：").append(o.getCloudCode()).append("\n").append("\n"));
        sb.append("有链接失效请私信！！会及时修正！");
        return sb.toString();
    }
}
