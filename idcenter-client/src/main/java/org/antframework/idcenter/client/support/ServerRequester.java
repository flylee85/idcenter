/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-09 17:25 创建
 */
package org.antframework.idcenter.client.support;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antframework.common.util.facade.AbstractResult;
import org.antframework.common.util.id.Period;
import org.antframework.common.util.tostring.ToString;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务端请求器
 */
public class ServerRequester {
    // 发送http请求的客户端
    private static final HttpClient HTTP_CLIENT = HttpClients.createDefault();
    // 获取批量id的url后缀
    private static final String ACQUIRE_IDS_URL_SUFFIX = "/ider/acquireIds";
    // 获取批量id的url
    private final String acquireIdsUrl;

    /**
     * 构造服务端请求器
     *
     * @param serverUrl 服务端地址
     */
    public ServerRequester(String serverUrl) {
        this.acquireIdsUrl = serverUrl + ACQUIRE_IDS_URL_SUFFIX;
    }

    /**
     * 获取批量id
     *
     * @param iderId id提供者的id（id编码）
     * @param amount id数量
     * @return 批量id
     */
    public List<IdChunk> acquireIds(String iderId, int amount) {
        try {
            String resultStr = HTTP_CLIENT.execute(buildRequest(iderId, amount), new BasicResponseHandler());
            AcquireIdsResult result = JSON.parseObject(resultStr, AcquireIdsResult.class);
            if (result == null) {
                throw new RuntimeException("请求idcenter失败");
            }
            if (!result.isSuccess()) {
                throw new RuntimeException("从idcenter获取批量id失败：" + result.getMessage());
            }
            return result.getIdChunks();
        } catch (IOException e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    // 构建请求
    private HttpUriRequest buildRequest(String iderId, int amount) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("iderId", iderId));
        params.add(new BasicNameValuePair("amount", Integer.toString(amount)));

        HttpPost httpPost = new HttpPost(acquireIdsUrl);
        httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("utf-8")));
        return httpPost;
    }

    /**
     * 获取批量id-result
     */
    @Getter
    @Setter
    public static class AcquireIdsResult extends AbstractResult {
        // id块
        private List<IdChunk> idChunks;
    }

    /**
     * id块
     */
    @AllArgsConstructor
    @Getter
    public static final class IdChunk implements Serializable {
        // 周期
        private final Period period;
        // 因数
        private final int factor;
        // 开始id（包含）
        private final long startId;
        // id个数
        private final int amount;

        @Override
        public String toString() {
            return ToString.toString(this);
        }
    }
}
