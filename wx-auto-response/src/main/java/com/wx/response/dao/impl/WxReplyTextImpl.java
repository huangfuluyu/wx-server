package com.wx.response.dao.impl;

import com.wx.response.dao.WxOperationDao;
import com.wx.response.entity.WxCloudDisk;
import com.wx.response.entity.WxReply;
import com.wx.response.mapper.WxCloudDiskMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : HuangFu
 * @Description : WxTextImpl
 * @date : 2022-01-20 17:30
 **/
@Service
public class WxReplyTextImpl implements WxOperationDao {

    @Autowired
    private WxCloudDiskMapper linkMapper;

    @Override
    public WxReply replyMsg(WxReply params) {
        //根据用户发送的关键词获取整个资源信息
        val cloudDisk = linkMapper.selectByKeyWords(params.getContent());

        if (cloudDisk == null) {
            return new WxReply(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    "\uD83E\uDD76抱歉,暂无该资源..." + "\n" +
                            "\uD83D\uDC47或许你喜欢" + "\n" + "\n" +
                            getRandomData()
                    , null, null);
        } else {
            return new WxReply(
                    params.getFromUserName(),
                    params.getToUserName(),
                    String.valueOf(System.currentTimeMillis()),
                    params.getMsgType(),
                    getRandomData(cloudDisk),
                    null, null);
        }
    }

    /**
     * 获取随机数据
     *
     * @return String
     */
    public String getRandomData() {
        val listByType = linkMapper.listSelectTop5();
        StringBuffer sb = new StringBuffer();
        sb.append("\uD83E\uDD29[感谢关注! 感谢分享公众号]\n");
        return getString(sb, listByType);

    }

    /**
     * 获取随机数据
     *
     * @param cloudDisk 关键词所对应资源
     * @return String
     */
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


    /**
     * 将列表数据格式化
     *
     * @param sb      StringBuffer
     * @param listTop 列表数据
     * @return String
     */
    private String getString(StringBuffer sb, List<WxCloudDisk> listTop) {
        listTop.forEach(o -> sb.append(o.getCloudTitle()).append("【").append(o.getCloudName()).append("】").append("\n")
                .append("链接：").append(o.getCloudLink()).append("\n")
                .append("提取码：").append(o.getCloudCode()).append("\n").append("\n"));
        sb.append("有链接失效请私信！！会及时修正！");
        return sb.toString();
    }
}
