package com.wx.response.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : HuangFu
 * @Description : WxCloudDiskUrl
 * @date : 2022-01-13 10:41
 **/
@Data
@TableName
public class WxCloudDisk {
    /**
     * 资源标题
     */
    @TableField("resource_title")
    private String title;

    /**
     * 资源类型
     */
    private int resourceType;

    /**
     * 关键词
     */
    @TableField("resource_key_words")
    private String keyWords;

    /**
     * 云盘链接类型
     */
    private int linkType;

    /**
     * 云盘链接
     */
    @TableField("wx_cloud_disk_link")
    private String cloudDiskLink;

    /**
     * 云盘链接验证码
     */
    @TableField("wx_cloud_disk_code")
    private String cloudDiskCode;
}
