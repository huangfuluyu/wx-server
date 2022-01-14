package com.wx.response.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.response.mapper.WxCloudDiskLinkMapper;
import com.wx.response.model.WxCloudDisk;
import com.wx.response.model.WxConsumerText;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : HuangFu
 * @Description : responseController
 * @date : 2022-01-10 15:17
 **/
@RestController
public class WxResponseController {

    @Autowired
    private WxCloudDiskLinkMapper linkMapper;

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
    public WxConsumerText consumeText(@RequestBody WxConsumerText params) {

        //根据用户发送的关键词获取整个资源信息
        QueryWrapper<WxCloudDisk> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("keyWords", params.getContent());
        val cloudDisk = linkMapper.selectOne(queryWrapper);


        if (cloudDisk == null) {
            return new WxConsumerText(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    "\uD83E\uDD76抱歉,暂无该资源。" + "\n" +
                            "或许你喜欢这些呢？" + "\n"

                    , null
            );
        } else {

            return new WxConsumerText(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    null, null
            );
        }
    }

    public List<WxCloudDisk> getRandomData(Integer resourceType) {
        List<WxCloudDisk> disks;
        if (resourceType != null) {
            disks = linkMapper.selectList(
                    new QueryWrapper<WxCloudDisk>().eq("resourceType", resourceType));
            disks.stream().limit(RandomUtil.randomInt(5));
        } else {
            disks = linkMapper.selectList(new QueryWrapper<WxCloudDisk>().select());
            disks = disks.stream().map().filter();
        }
        return disks;

    }
}
