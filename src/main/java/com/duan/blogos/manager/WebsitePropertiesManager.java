package com.duan.blogos.manager;

import lombok.Data;

/**
 * Created on 2018/1/3.
 * 站点配置
 *
 * @author DuanJiaNing
 */
@Data
public class WebsitePropertiesManager {

    /**
     * 站点域名
     */
    private String addr;

    /**
     * lucene生成的索引保存路径
     */
    private String luceneIndexDir;

    /**
     * 默认的url请求参数的间隔字符
     * 如url中传递多个博文类别id时：1,2,3,8 这里间隔字符为","
     */
    private String urlConditionSplitCharacter;

}